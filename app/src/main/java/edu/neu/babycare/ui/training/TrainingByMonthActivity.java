package edu.neu.babycare.ui.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;

import edu.neu.babycare.R;
import edu.neu.babycare.model.Baby;
import edu.neu.babycare.model.Video;
import edu.neu.babycare.ui.training.adapter.TrainingByMonthRvAdapter;

public class TrainingByMonthActivity extends AppCompatActivity {
    private static final String TAG = TrainingByMonthActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TrainingByMonthRvAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private ArrayList<Video> itemList = new ArrayList<>();
    private String[] videoNameList = {
            "Baby Massage",
            "Bathing a Newborn Baby",
            "Change a Baby's Diaper"
    };
    private String[] videoUrlList = {
            "https://firebasestorage.googleapis.com/v0/b/babycare-7971c.appspot.com/o/Baby%20Massage.mp4?alt=media&token=4a417b12-2510-4e48-b1c8-7fcdd69f2105",
            "https://firebasestorage.googleapis.com/v0/b/babycare-7971c.appspot.com/o/Bathing%20a%20Newborn%20Baby.mp4?alt=media&token=27569e29-9226-4228-94e3-16838e4fcb66",
            "https://firebasestorage.googleapis.com/v0/b/babycare-7971c.appspot.com/o/Change%20a%20Baby's%20Diaper.mp4?alt=media&token=06b9bc7f-c09e-4862-9ead-e44b40907341"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_by_month);
        String title = getIntent().getStringExtra("title");
        this.setTitle(title);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            Video video = new Video(videoNameList[i], videoUrlList[i], 10);
            itemList.add(video);
        }
    }



    private void initView() {
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_training_by_month);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new TrainingByMonthRvAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(Context context, String text, int position) {
                Log.d(TAG, "onItemClick: " + text + " , " + position);
                Intent intent = new Intent(TrainingByMonthActivity.this, VideoPlayActivity.class);
                intent.putExtra("url", text);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        };
        recyclerViewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

}
