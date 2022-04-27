package edu.neu.babycare.ui.training;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;

public class VideoPlayActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button checkBtn;
    private TextView videoDescriptionTv;

    private int videoIndex;
    private String videoUrl;
    private int[] videoDescriptions = new int[]{
            R.string.video_description1,
            R.string.video_description2,
            R.string.video_description3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initViews();
    }

    private void initViews() {
        videoUrl = getIntent().getExtras().getString("url");
        videoIndex = getIntent().getIntExtra("index", -1);

        videoView = findViewById(R.id.videoView);
        checkBtn = findViewById(R.id.checkBtn);
        videoDescriptionTv = findViewById(R.id.video_description);
        videoDescriptionTv.setText(videoDescriptions[videoIndex]);

        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mc = new MediaController(VideoPlayActivity.this);
                        videoView.setMediaController(mc);
                        mc.setAnchorView(videoView);
                    }
                });
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //videoView.start();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        videoView.start();

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_checked, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayActivity.this);
                builder.setView(dialogView).show();
                setBtnChecked();
                //update the score
                int score = MyApplication.getInstance().getScore();
                MyApplication.getInstance().setScore(score + 10);
                //update the checked status
                MyApplication.getInstance().setVideoCheckedList(true, videoIndex);
            }
        });
    }

    private void setBtnChecked() {
        if (checkBtn != null) {
            checkBtn.setText("checked");
            checkBtn.setBackground(null);
            checkBtn.setTextColor(getResources().getColor(R.color.gray, null));
            checkBtn.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean[] videoCheckedList = MyApplication.getInstance().getVideoCheckedList();
        if (videoCheckedList[videoIndex]) {
            setBtnChecked();
        }

    }

}
