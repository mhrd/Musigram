package android.mehrdad.musigram.adapter;

/**
 * Created by Mr.Anonymous on 4/2/2018.
 */

import android.graphics.Color;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.app.AppController;
import android.mehrdad.musigram.model.Comment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Comment> commentItem;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CommentAdapter(Activity activity, List<Comment> commentItem) {
        this.activity = activity;
        this.commentItem = commentItem;
    }

    @Override
    public int getCount() {
        return commentItem.size();
    }

    @Override
    public Object getItem(int location) {
        return commentItem.get(location);
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

        if (convertView == null)
            convertView = inflater.inflate(R.layout.comments_page_row, null);
        // getting movie data for the row
        Comment m = commentItem.get(position);

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.comment_thumbnail);
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        TextView person = (TextView) convertView.findViewById(R.id.comment_person);
        person.setText("@" + m.getPerson());
        TextView time = (TextView) convertView.findViewById(R.id.comment_time);
        time.setText(m.getTime());
        TextView comment = (TextView) convertView.findViewById(R.id.comment_comment);
        String body = m.getBody();
        MySetText(body, MySetText1(body), comment);


        return convertView;
    }

    Spannable MySetText1(String text) {
        Spannable spannable = new SpannableString(text);
        int charSt = -1;
        do {
            charSt = text.indexOf("#", charSt);
            if (charSt != -1) {
                int charEn;
                int charEn1 = text.indexOf(" ", charSt);
                int charEn2 = text.indexOf("\n", charSt);
                if (charEn1 == -1 && charEn2 != -1) {
                    charEn = charEn2;
                } else if (charEn1 != -1 && charEn2 == -1) {
                    charEn = charEn1;
                } else if (charEn1 == charEn2) {
                    charEn = text.length();
                } else {
                    charEn = (charEn1 < charEn2) ? charEn1 : charEn2;
                }
                //span
                spannable.setSpan(new ForegroundColorSpan(Color.RED), charSt, charEn, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //updating
                charSt = charEn;
            } else {
                return spannable;
            }
        } while (true);
    }

    void MySetText(String text, Spannable spannable, TextView tv) {
        int charSt = -1;
        do {
            charSt = text.indexOf("@", charSt);
            if (charSt != -1) {
                int charEn;
                int charEn1 = text.indexOf(" ", charSt);
                int charEn2 = text.indexOf("\n", charSt);
                if (charEn1 == -1 && charEn2 != -1) {
                    charEn = charEn2;
                } else if (charEn1 != -1 && charEn2 == -1) {
                    charEn = charEn1;
                } else if (charEn1 == charEn2) {
                    charEn = text.length();
                } else {
                    charEn = (charEn1 < charEn2) ? charEn1 : charEn2;
                }
                //span
                spannable.setSpan(new ForegroundColorSpan(Color.BLUE), charSt, charEn, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //updating
                charSt = charEn;
            } else {
                tv.setText(spannable, TextView.BufferType.SPANNABLE);
                return;
            }
        } while (true);
    }

}