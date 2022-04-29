package edu.neu.babycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class TrainingCenter1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center1);

        //Initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set home
        bottomNavigationView.setSelectedItemId(R.id.training_center);

        //Perform itemSelectedList
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.training_center:
                        return;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), Messages.class));
                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });
    }
}