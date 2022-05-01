package edu.neu.babycare.ui.family;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;
import edu.neu.babycare.model.Messages;
import edu.neu.babycare.ui.login.LoginActivity;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    private String messageReceiver;
    private String messageSender;
    private ImageButton sendMessageButton;
    private ImageButton sendPhotoButton;
    private EditText messageInputText;
    private DatabaseReference dbRef;
    private final List<Messages> messagesList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;
    private ProgressDialog loadingBar;
    private String checker = "", myUrl = "";
    private StorageTask uploadTask;
    private Uri fileUri;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageReceiver = getIntent().getExtras().get("visit_user_name").toString();
        setTitle(messageReceiver);
        messageSender = MyApplication.getInstance().getLoginUserName();
        dbRef = FirebaseDatabase.getInstance().getReference();
        initViews();
        initListeners();

    }

    private void initViews() {
        sendMessageButton = (ImageButton) findViewById(R.id.send_message_btn);
        sendPhotoButton = (ImageButton) findViewById(R.id.send_photo_btn);
        messageInputText = (EditText) findViewById(R.id.input_message);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        messageAdapter = new MessageAdapter(messagesList);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);
        loadingBar = new ProgressDialog(this);
    }

    private void initListeners() {
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        sendPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "image";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Select Image"), 438);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            loadingBar.setTitle("Sending Image");
            loadingBar.setMessage("Please wait, we are sending the image...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            fileUri = data.getData();
            if (checker.equals("image")) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                        .child("Image Files");

                String messageSenderRef = "Messages/" + messageSender + "/" + messageReceiver;
                String messageReceiverRef = "Messages/" + messageReceiver + "/" + messageSender;

                DatabaseReference userMessageKeyRef = dbRef.child("Messages")
                        .child(messageSender).child(messageReceiver).push();
                String messagePushID = userMessageKeyRef.getKey();
                StorageReference filePath = storageReference.child(messagePushID + "." + ".jpg");
                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();
                            myUrl = downloadUrl.toString();

                            Map messageTextBody = new HashMap();
                            messageTextBody.put("message", myUrl);
                            messageTextBody.put("name", fileUri.getLastPathSegment());
                            messageTextBody.put("type", checker);
                            messageTextBody.put("from", messageSender);
                            messageTextBody.put("to", messageReceiver);
                            messageTextBody.put("messageID", messagePushID);

                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
                            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody);

                            dbRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(ChatActivity.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                    messageInputText.setText("");
                                }
                            });
                        }
                    }
                });


            } else {
                loadingBar.dismiss();
                Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    protected void onResume() {
        super.onStart();
        Log.d(TAG, "onStart: " + messageSender + " , " + messageReceiver);
        childEventListener = getListener();
        dbRef.child("Messages").child(messageSender).child(messageReceiver).addChildEventListener(childEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbRef.child("Messages").child(messageSender).child(messageReceiver).removeEventListener(childEventListener);
        messagesList.clear();
    }

    @NonNull
    private ChildEventListener getListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages message = dataSnapshot.getValue(Messages.class);
                Log.d(TAG, "onChildAdded: " + message.getMessageID());
                messagesList.add(message);
                messageAdapter.notifyDataSetChanged();
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void sendMessage() {
        String messageText = messageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        } else {
            String messageSenderRef = "Messages/" + messageSender + "/" + messageReceiver;
            String messageReceiverRef = "Messages/" + messageReceiver + "/" + messageSender;

            DatabaseReference userMessageKeyRef = dbRef.child("Messages")
                    .child(messageSender).child(messageReceiver).push();

            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSender);
            messageTextBody.put("to", messageReceiver);
            messageTextBody.put("messageID", messagePushID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody);

            dbRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChatActivity.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    messageInputText.setText("");
                }
            });
        }
    }

}