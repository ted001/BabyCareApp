package edu.neu.babycare.ui.training.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import edu.neu.babycare.R;
import edu.neu.babycare.model.Video;
import edu.neu.babycare.ui.training.ItemClickListener;

public class TrainingByMonthRvAdapter extends RecyclerView.Adapter<TrainingByMonthRvAdapter.RecyclerViewHolder> {
    private final ArrayList<Video> itemList;
    private ItemClickListener listener;

    public TrainingByMonthRvAdapter(ArrayList<Video> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainingByMonthRvAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_training_by_month_item, parent, false);
        return new RecyclerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingByMonthRvAdapter.RecyclerViewHolder holder, int position) {
        Video video = itemList.get(position);
        holder.textView.setText(video.getVideoName());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public RecyclerViewHolder(View itemView, final ItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleTv);
            imageView = itemView.findViewById(R.id.typeIv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Video video = itemList.get(position);
                            String text = video.getUrl();
                            listener.onItemClick(v.getContext(), text, position);
                        }
                    }
                }
            });
        }
    }
}