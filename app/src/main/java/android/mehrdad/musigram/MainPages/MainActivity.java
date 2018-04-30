package android.mehrdad.musigram.MainPages;

import android.content.Intent;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.VoiceProcess.GeneralPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            Class fragmentClass = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentClass = Home_Page.class;
                    break;
                case R.id.navigation_search:
                    fragmentClass = Search_Page.class;
                    break;
                case R.id.navigation_add:
                    transition(Add_Page.class);
                    return true;
                case R.id.navigation_likes:
                    fragmentClass = Likes_Page.class;
                    break;
                case R.id.navigation_profile:
                    fragmentClass = Profile_Page.class;
                    break;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();

        Fragment fragment = null;
        Class fragmentClass = Home_Page.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    public static GeneralPlayer generalPlayer;

    void init() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setSelectedItemId(R.id.navigation_add);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        generalPlayer = new GeneralPlayer();
    }

    void transition(Class dest) {
        startActivity(new Intent(MainActivity.this, dest));
    }

    @Override
    protected void onDestroy() {
        generalPlayer.clear();
        super.onDestroy();
    }
}
