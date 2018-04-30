package android.mehrdad.musigram.VoiceProcess;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.mehrdad.musigram.MainPages.MainActivity;
import android.os.Handler;
import android.widget.ProgressBar;

public class GeneralPlayer {

    Thread thread;

    MediaPlayer player;

    ProgressBar pb;

    Handler handler = new Handler();

    int duration;

    public void play(final ProgressBar newpb, final String soundURL) {
        if (pb != null) pb.setProgress(0);
        pb = newpb;
        if (player != null) player.stop();
//        if (thread != null) thread.destroy();
        //////////////////////////////////
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(soundURL);
                    player.prepare();
                    pb.setMax(player.getDuration() - 1000);
                    player.start();
                    ProgressUpdater();
                } catch (Exception e) {
                    //
                }
            }
        });
        thread.start();

    }

    public void clear() {
        if (player != null) player.stop();
    }

    public void ProgressUpdater() {
        pb.setProgress(player.getCurrentPosition());
        if (player.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    ProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }
}
