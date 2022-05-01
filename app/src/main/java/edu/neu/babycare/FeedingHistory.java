package edu.neu.babycare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FeedingHistory extends AppCompatActivity {
    private Button button2;

    private Map<String, Map<String, Integer>> activityMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_history);

        this.activityMap = new HashMap<>();
        new ButtonListener(this, findViewById(R.id.button), activityMap);
        new ButtonListener(this, findViewById(R.id.button4), activityMap);
        new ButtonListener(this, findViewById(R.id.button5), activityMap);
        new ButtonListener(this, findViewById(R.id.button6), activityMap);
        new ButtonListener(this, findViewById(R.id.button7), activityMap);
        new ButtonListener(this, findViewById(R.id.button8), activityMap);
        new ButtonListener(this, findViewById(R.id.button9), activityMap);
        new ButtonListener(this, findViewById(R.id.button10), activityMap);
        new ButtonListener(this, findViewById(R.id.button11), activityMap);
        new ButtonListener(this, findViewById(R.id.button12), activityMap);
        new ButtonListener(this, findViewById(R.id.button13), activityMap);
        new ButtonListener(this, findViewById(R.id.button14), activityMap);
        new ButtonListener(this, findViewById(R.id.button15), activityMap);

        FeedingHistory currentClass = this;
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), currentClass.readMap(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String readMap() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Integer>> entry : activityMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> value = entry.getValue();
            for (Map.Entry<String, Integer> entry1 : value.entrySet()) {
                String activity = entry1.getKey();
                Integer count = entry1.getValue();
                sb.append(key).append(" ").append(activity).append("").append("for ").append(count).append("times");
            }
        }
        return sb.toString();

    }
}