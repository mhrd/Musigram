package android.mehrdad.musigram.MainPages;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.VoiceProcess.MakePost;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Add_Page extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    Button rec_btn, play_btn, go_btn, retry_btn, storage_btn;
    TextView selectst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        init();
        listeners();
    }

    void init() {
        rec_btn = (Button) findViewById(R.id.recBtn);
        go_btn = (Button) findViewById(R.id.nextBtn);
        retry_btn = (Button) findViewById(R.id.retryBtn);
        play_btn = (Button) findViewById(R.id.playBtn);
        storage_btn = (Button) findViewById(R.id.storageBtn);
        selectst = (TextView) findViewById(R.id.selectst);
    }

    void listeners() {
        rec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rec_btn.setVisibility(View.INVISIBLE);
                play_btn.setVisibility(View.VISIBLE);
                go_btn.setVisibility(View.VISIBLE);
                retry_btn.setVisibility(View.VISIBLE);
            }
        });
        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_btn.setVisibility(View.INVISIBLE);
                rec_btn.setVisibility(View.VISIBLE);
                go_btn.setVisibility(View.INVISIBLE);
                retry_btn.setVisibility(View.INVISIBLE);
            }
        });
        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(MakePost.class);
            }
        });
    }

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    void transition(Class dest) {
        startActivity(new Intent(Add_Page.this, dest));
    }


}
