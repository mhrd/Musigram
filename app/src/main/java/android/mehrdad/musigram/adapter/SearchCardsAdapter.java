package android.mehrdad.musigram.adapter;

import android.content.Context;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.model.SearchCard;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchCardsAdapter extends RecyclerView.Adapter<SearchCardsAdapter.MyViewHolder> {

    private Context mContext;
    private List<SearchCard> SearchCardList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public SearchCardsAdapter(Context mContext, List<SearchCard> SearchCardList) {
        this.mContext = mContext;
        this.SearchCardList = SearchCardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SearchCard SearchCard = SearchCardList.get(position);
        holder.title.setText(SearchCard.getName());
        if (SearchCard.getNumOfSongs() == 11) {
            holder.count.setText("New to MusiGram");
        } else {
            holder.count.setText(SearchCard.getNumOfSongs() + " songs");
        }

        // loading SearchCard cover using Glide library
        Glide.with(mContext).load(SearchCard.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
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
        return SearchCardList.size();
    }
}
