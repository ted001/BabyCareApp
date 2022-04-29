package edu.neu.babycare.ui.family;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.babycare.MainActivity;
import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;
import edu.neu.babycare.model.User;
import edu.neu.babycare.ui.training.TrainingCenterActivity;

public class FamilyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference familyRef;
    private List<User> familyMemberList = new ArrayList<>();
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.family);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.training_center:
                        startActivity(new Intent(getApplicationContext(), TrainingCenterActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.family:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        familyRef = FirebaseDatabase.getInstance().getReference().child("FamilyCodes").child("1111").child("family");
        recyclerView = findViewById(R.id.familyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(familyRef, User.class)
                        .build();
        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    familyMemberList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerAdapter<User, FamilyViewHolder> adapter =
                new FirebaseRecyclerAdapter<User, FamilyViewHolder>(options) {

                    @NonNull
                    @Override
                    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent, false);
                        return new FamilyViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FamilyViewHolder holder, int position, @NonNull User model) {
                        User user = familyMemberList.get(position);
                        if (user.getUserName().equals(MyApplication.getInstance().getLoginUserName())) {
                            RecyclerView.LayoutParams param
                                    = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            param.height = 0;
                            param.width = 0;
                            holder.itemView.setVisibility(View.GONE);
                            holder.itemView.setLayoutParams(param);
                            return;
                        }
                        holder.userName.setText(user.getUserName());
                        holder.userRelationship.setText(user.getRelationship());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent chatIntent = new Intent(FamilyActivity.this, ChatActivity.class);
                                chatIntent.putExtra("visit_user_name", user.getUserName());
                                startActivity(chatIntent);
                            }
                        });

                    }

                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.family);
    }

    public static class FamilyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView userRelationship, userName;


        public FamilyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.users_profile_image);
            userRelationship = itemView.findViewById(R.id.user_relationship);
            userName = itemView.findViewById(R.id.user_profile_name);
        }
    }
}