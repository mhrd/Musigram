package android.mehrdad.musigram.adapter;

/**
 * Created by Mr.Anonymous on 4/2/2018.
 */

import android.mehrdad.musigram.R;
import android.mehrdad.musigram.app.AppController;
import android.mehrdad.musigram.model.Like;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class LikesListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Like> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public LikesListAdapter(Activity activity, List<Like> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // getting movie data for the row
        Like m = movieItems.get(position);

        if (m.getYear() == 2014) {
            ///////////////////////////like

            //layout row mapping
//            if (convertView == null)
                convertView = inflater.inflate(R.layout.like_list_row, null);

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.lthumbnail);
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
            TextView person = (TextView) convertView.findViewById(R.id.lperson);
            person.setText("@" + m.getTitle());
            TextView time = (TextView) convertView.findViewById(R.id.ltime);
            int a = m.getYear() % 2000;
            time.setText(String.valueOf(a) + "h");
            TextView post = (TextView) convertView.findViewById(R.id.lpost);
            post.setText(m.getTitle());

        } else if (m.getYear() < 2000) {
            //////////////////////////mentioned

            //layout row mapping
//            if (convertView == null)
                convertView = inflater.inflate(R.layout.mentioned_list_row, null);

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.mthumbnail);
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
            TextView person = (TextView) convertView.findViewById(R.id.mperson);
            person.setText("@" + m.getTitle());
            TextView time = (TextView) convertView.findViewById(R.id.mtime);
            int a = m.getYear() % 2000;
            time.setText(String.valueOf(a) + "d");
            TextView comment = (TextView) convertView.findViewById(R.id.mcomment);
            comment.setText(m.getTitle());

        } else if (m.getYear() > 2000 && m.getYear() < 2005) {
            //////////////////////////follow

            //layout row mapping
//            if (convertView == null)
                convertView = inflater.inflate(R.layout.follow_list_row, null);

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.fthumbnail);
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
            TextView person = (TextView) convertView.findViewById(R.id.fperson);
            person.setText("@" + m.getTitle());
            TextView time = (TextView) convertView.findViewById(R.id.ftime);
            int a = m.getYear() % 2000;
            time.setText(String.valueOf(a) + "w");
            if (m.getYear() == 2004) {
                Button Follow = (Button) convertView.findViewById(R.id.fbutton);
                Follow.setVisibility(View.VISIBLE);
            } else {
                Button Following = (Button) convertView.findViewById(R.id.fingbutton);
                Following.setVisibility(View.VISIBLE);
            }

        } else if (m.getYear() <= 2013 && m.getYear() >= 2009) {
            //////////////////////////Comment

            //layout row mapping
//            if (convertView == null)
                convertView = inflater.inflate(R.layout.comment_list_row, null);

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView cthumbNail = (NetworkImageView) convertView.findViewById(R.id.cthumbnail);
            cthumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
            TextView person = (TextView) convertView.findViewById(R.id.cperson);
            person.setText("@" + m.getTitle());
            TextView time = (TextView) convertView.findViewById(R.id.ctime);
            int a = m.getYear() % 2000;
            time.setText(String.valueOf(a) + "m");
            TextView post = (TextView) convertView.findViewById(R.id.cpost);
            post.setText(m.getTitle());
            TextView comment = (TextView) convertView.findViewById(R.id.ccomment);
            comment.setText(m.getTitle());
        }

        return convertView;
    }
}