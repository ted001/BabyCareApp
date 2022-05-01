package edu.neu.babycare;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class ButtonListener {
    private final AlertDialog.Builder dialogBuilder;

    public ButtonListener(Context context, Button button, Map<String, Map<String, Integer>> activityMap) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(button.getText())
                .setMessage("Do you want to add " + button.getText() + "?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String activity = button.getText().toString();
                String dayKey = LocalDate.now().toString();
                if (activityMap.containsKey(dayKey)) {
                    int counter = activityMap.get(dayKey).getOrDefault(activity, 0) + 1;
                    activityMap.get(dayKey).put(activity, counter);
                } else {
                    activityMap.put(dayKey, new HashMap<>());
                    activityMap.get(dayKey).put(activity, 1);
                }
                dialogInterface.cancel();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.show();
            }
        });
    }
}
