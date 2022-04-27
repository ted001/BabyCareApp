package edu.neu.babycare.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.babycare.MainActivity;
import edu.neu.babycare.R;
import edu.neu.babycare.model.User;
import edu.neu.babycare.ui.signup.SignUpActivity;
import edu.neu.babycare.ui.training.TrainingCenterActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText userNameEt;
    private String loginUserName;
    //firebaseDatabase
    private FirebaseDatabase mFirebaseDb;
    private DatabaseReference mDbUsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mFirebaseDb = FirebaseDatabase.getInstance();
        mDbUsersRef = mFirebaseDb.getReference("Users");

    }

    private void initView() {
        userNameEt = findViewById(R.id.userName);
    }

    public void signUp(View view) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        loginUserName = userNameEt.getText().toString().trim();
        if (loginUserName == null || loginUserName.length() < 1) {
            userNameEt.setError("UserName is required");
            userNameEt.requestFocus();
            return;
        }
        mDbUsersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                } else {
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String userName = postSnapshot.getValue(User.class).userName;
                        if (userName.equals(loginUserName)) {
                            startActivity(new Intent(LoginActivity.this, TrainingCenterActivity.class));
                            LoginActivity.this.finish();
                            return;
                        }
                    }
                    userNameEt.setError("UserName does not exist, please try again or sign up");
                    userNameEt.requestFocus();
                }
            }
        });


    }
}
