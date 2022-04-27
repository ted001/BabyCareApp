package edu.neu.babycare.ui.training;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.neu.babycare.R;

public class TrainingCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TrainingCenterFragment trainingCenterFragment = new TrainingCenterFragment();
        fragmentTransaction.add(R.id.fragment_container, trainingCenterFragment, "trainingCenterFragment");
        fragmentTransaction.commit();
    }
}
