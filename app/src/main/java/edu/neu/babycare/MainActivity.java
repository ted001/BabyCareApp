package edu.neu.babycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_1 = (Button) findViewById(R.id.button1);

        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, photo.class));
            }
        });

        //Initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set home
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform itemSelectedList
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.training_center:
                        startActivity(new Intent(getApplicationContext(), TrainingCenter1.class));
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.home:
                        return;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), Messages.class));
                        overridePendingTransition(0, 0);
                        return;
                }
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

        ImageButton btnWeightNHeight = (ImageButton) findViewById(R.id.weight_length);
        btnWeightNHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityWeightNHeight();
            }
        });

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
        Intent intentAlbum = new Intent(this, Album.class);
        startActivity(intentAlbum);
    }
}