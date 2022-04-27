package edu.neu.babycare.ui.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;
import edu.neu.babycare.ui.training.adapter.TrainingCenterRvAdapter;

public class TrainingCenterFragment extends Fragment {
    private static final String TAG = TrainingCenterFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private TrainingCenterRvAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private ArrayList<String> trainingCenterItemList = new ArrayList<>();
    private TextView mPointTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_center, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < 24; i++) {
            //String str = "Level " + (i + 1) + "\t\t" + i + "~" + (i + 1) + " Month";
            String str = i + " ~ " + (i + 1) + " Month";
            trainingCenterItemList.add(str);
        }

        rLayoutManger = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rv_training_center);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new TrainingCenterRvAdapter(trainingCenterItemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(Context context, String text, int position) {
                Log.d(TAG, "onItemClick: " + text + " , " + position);
                Intent intent = new Intent(getActivity(), TrainingByMonthActivity.class);
                intent.putExtra("title", text);
                startActivity(intent);
            }
        };
        recyclerViewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

        mPointTv = view.findViewById(R.id.pointTv);
    }

    @Override
    public void onResume() {
        super.onResume();
        int score = MyApplication.getInstance().getScore();
        mPointTv.setText(score + "");
    }
}
