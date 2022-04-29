package edu.neu.babycare.ui.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import edu.neu.babycare.MainActivity;
import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;
import edu.neu.babycare.ui.family.FamilyActivity;
import edu.neu.babycare.ui.training.adapter.TrainingCenterRvAdapter;

public class TrainingCenterActivity extends AppCompatActivity {
    private static final String TAG = TrainingCenterActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private TrainingCenterRvAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private ArrayList<String> trainingCenterItemList = new ArrayList<>();
    private TextView mPointTv;
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center);

        for (int i = 0; i < 24; i++) {
            //String str = "Level " + (i + 1) + "\t\t" + i + "~" + (i + 1) + " Month";
            String str = i + " ~ " + (i + 1) + " Month";
            trainingCenterItemList.add(str);
        }

        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_training_center);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new TrainingCenterRvAdapter(trainingCenterItemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(Context context, String text, int position) {
                Log.d(TAG, "onItemClick: " + text + " , " + position);
                Intent intent = new Intent(TrainingCenterActivity.this, TrainingByMonthActivity.class);
                intent.putExtra("title", text);
                startActivity(intent);
            }
        };
        recyclerViewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

        mPointTv = findViewById(R.id.pointTv);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.training_center);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.training_center:
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        int score = MyApplication.getInstance().getScore();
        mPointTv.setText(score + "");
        bottomNavigationView.setSelectedItemId(R.id.training_center);
    }
}
