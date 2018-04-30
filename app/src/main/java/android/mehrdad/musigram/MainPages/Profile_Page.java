package android.mehrdad.musigram.MainPages;

import android.graphics.Color;
import android.mehrdad.musigram.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Profile_Page extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

//        Button b4 = (Button) view.findViewById(R.id.b4);
//        b4.setBackgroundColor(Color.RED);

        return view;
    }
}
