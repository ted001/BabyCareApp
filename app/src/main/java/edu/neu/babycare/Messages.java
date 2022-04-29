package edu.neu.babycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class Messages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //Initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set home
        bottomNavigationView.setSelectedItemId(R.id.messages);

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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.messages:
                        return;
                }
            }
        });
    }
}