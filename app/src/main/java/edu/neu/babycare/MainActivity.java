package edu.neu.babycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.babycare.model.Baby;
import edu.neu.babycare.model.User;
import edu.neu.babycare.ui.family.FamilyActivity;
import edu.neu.babycare.ui.login.LoginActivity;
import edu.neu.babycare.ui.training.TrainingCenterActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    NavigationBarView bottomNavigationView;
    //firebaseDatabase
    private FirebaseDatabase mFirebaseDb;
    private DatabaseReference mDbBabyRef;
    private TextView mBabyNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDb = FirebaseDatabase.getInstance();
        mDbBabyRef = mFirebaseDb.getReference("FamilyCodes").child("1111").child("baby");

        mBabyNameTv = findViewById(R.id.baby_name);
        //Initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set home
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.training_center:
                        startActivity(new Intent(getApplicationContext(), TrainingCenterActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        break;
                    case R.id.family:
                        startActivity(new Intent(getApplicationContext(), FamilyActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        TextView dailyMission = (TextView) findViewById(R.id.daily_mission_title);
        dailyMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityDailyMission();
            }
        });

        TextView dailyMissionContent = (TextView) findViewById(R.id.daily_mission_content);
        dailyMissionContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityDailyMission();
            }
        });

        ImageButton btnFeedingHistory = (ImageButton) findViewById(R.id.feeding_history);
        btnFeedingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityFeedingHistory();
            }
        });

//        ImageButton btnWeightNHeight = (ImageButton) findViewById(R.id.weight_length);
//        btnWeightNHeight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openActivityWeightNHeight();
//            }
//        });

        ImageButton btnAlbum = (ImageButton) findViewById(R.id.photo_album);
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAlbum();
            }
        });
    }

    public void openActivityDailyMission() {
        Intent intentDailyMission = new Intent(this, DailyMission.class);
        startActivity(intentDailyMission);
    }

    public void openActivityFeedingHistory() {
        Intent intentFeedingHistory = new Intent(this, FeedingHistory.class);
        startActivity(intentFeedingHistory);
    }

    public void openActivityWeightNHeight() {
        Intent intentWeightNHeight = new Intent(this, WeightNHeight.class);
        startActivity(intentWeightNHeight);
    }

    public void openActivityAlbum() {
        Intent intentAlbum = new Intent(this, photo.class);
        startActivity(intentAlbum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayBabyName();
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void displayBabyName() {
        mDbBabyRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                } else {
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String babyName = postSnapshot.getValue(Baby.class).babyName;
                        mBabyNameTv.setText(babyName);
                        Log.d(TAG, "onComplete: " + babyName);
                    }
                }
            }
        });
    }
}