package edu.neu.babycare.ui.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import edu.neu.babycare.R;
import edu.neu.babycare.ui.training.TrainingCenterFragment;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FamilyFragment familyFragment = new FamilyFragment();
        fragmentTransaction.add(R.id.fragment_container, familyFragment, "familyFragment");
        fragmentTransaction.commit();
    }
}