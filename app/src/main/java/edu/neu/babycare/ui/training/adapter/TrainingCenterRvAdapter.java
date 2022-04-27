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
import edu.neu.babycare.ui.training.ItemClickListener;

public class TrainingCenterRvAdapter extends RecyclerView.Adapter<TrainingCenterRvAdapter.RecyclerViewHolder> {
    private final ArrayList<String> itemList;
    private ItemClickListener listener;

    public TrainingCenterRvAdapter(ArrayList<String> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainingCenterRvAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_training_center_item, parent, false);
        return new RecyclerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingCenterRvAdapter.RecyclerViewHolder holder, int position) {
        String str = itemList.get(position);
        holder.textView.setText(str);
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
            textView = itemView.findViewById(R.id.msgTv);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String text = (String) textView.getText();
                            listener.onItemClick(v.getContext(), text, position);
                        }
                    }
                }
            });
        }
    }
}