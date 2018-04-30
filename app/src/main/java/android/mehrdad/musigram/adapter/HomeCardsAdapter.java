package android.mehrdad.musigram.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.mehrdad.musigram.ImageProcess.CircleImage;
import android.mehrdad.musigram.MainPages.Comments_Page;
import android.mehrdad.musigram.MainPages.MainActivity;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.VoiceProcess.GeneralPlayer;
import android.mehrdad.musigram.app.AppController;
import android.mehrdad.musigram.model.HomeCards;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class HomeCardsAdapter extends RecyclerView.Adapter<HomeCardsAdapter.MyViewHolder> {

    private Context mContext;
    private List<HomeCards> HomeCardList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView card_music;
        public TextView person, title, caption, LCnumber;
        public NetworkImageView thumbnail;
        ImageView comment_button;
        LikeButton lb;
        ProgressBar musicPlayer_pb;
        String soundUrl;

        public MyViewHolder(View view) {
            super(view);
            card_music = (CardView) view.findViewById(R.id.card_music);
            person = (TextView) view.findViewById(R.id.user_name);
            title = (TextView) view.findViewById(R.id.user_title);
            caption = (TextView) view.findViewById(R.id.user_description);
            LCnumber = (TextView) view.findViewById(R.id.user_likecomments_number);
            thumbnail = (NetworkImageView) view.findViewById(R.id.user_image);
            lb = (LikeButton) view.findViewById(R.id.user_like_button);
            comment_button = (ImageView) view.findViewById(R.id.user_comment_button);
            musicPlayer_pb = (ProgressBar) view.findViewById(R.id.user_progress);
        }
    }


    public HomeCardsAdapter(Context mContext, List<HomeCards> HomeCardList) {
        this.mContext = mContext;
        this.HomeCardList = HomeCardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_post_card, parent, false);

        return new MyViewHolder(itemView);
    }

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HomeCards homeCards = HomeCardList.get(position);
        // loading SearchCard cover using Glide library
        holder.soundUrl = homeCards.getSoundUrl();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl(homeCards.getThumbnail(), imageLoader);
//        Glide.with(mContext).load(homeCards.getThumbnail()).into(holder.thumbnail);
        holder.title.setText(homeCards.getTitle());
        holder.person.setText("@" + homeCards.getPerson());
//        holder.caption.setText(homeCards.getCaption());
        String body = "";
        body = homeCards.getCaption();
        if (!body.equals("")) MySetText(body, MySetText1(body), holder.caption);
        holder.LCnumber.setText("Likes " + homeCards.getLikes() + "  Comments " + homeCards.getComments());
        holder.lb.setLiked(homeCards.getIsLike());
        holder.card_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.musicPlayer_pb.setIndeterminate(true);
                MainActivity.generalPlayer.play(holder.musicPlayer_pb, holder.soundUrl);
//                holder.musicPlayer_pb.setIndeterminate(false);
//                showPopupMenu(holder.card_music);
            }
        });
        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Comments_Page.class);
                //i.putextra(url)
                mContext.startActivity(i);
            }
        });
        holder.lb.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, holder.person.getText() + " liked !!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, "dislike", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.search_card_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_follow:
                    Toast.makeText(mContext, "follow", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_view_page:
                    Toast.makeText(mContext, "view", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return HomeCardList.size();
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
