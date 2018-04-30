package android.mehrdad.musigram.MainPages;

import android.mehrdad.musigram.R;
import android.mehrdad.musigram.adapter.HomeCardsAdapter;
import android.mehrdad.musigram.model.HomeCards;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends Fragment {

    private RecyclerView recyclerView;

    private HomeCardsAdapter adapter;
    private List<HomeCards> HomeCardList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        HomeCardList = new ArrayList<>();
        adapter = new HomeCardsAdapter(getContext(), HomeCardList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new Search_Page.GridSpacingItemDecoration(3, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareHomeCardss();

        return view;

    }

    private void prepareHomeCardss() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11
        };

        HomeCards a = new HomeCards("mhrd7", "amad bahare janha", false,
                "http://musicgram-001-site1.1tempurl.com/samplemusic/mus1.ogg",
                56, 74);
        a.setCaption("This is a #great app. I #love_you mr @mhrd7\n" +
                "#musigram\n#music_sharing\n@development_team ");
        a.setThumbnail("https://api.androidhive.info/json/movies/1.jpg");
        HomeCardList.add(a);

        a = new HomeCards("amirStrang", "مداحی", true,
                "http://musicgram-001-site1.1tempurl.com/samplemusic/mus2.ogg",
                56, 74);
        a.setCaption("واقعا زیباست");
        a.setThumbnail("https://api.androidhive.info/json/movies/2.jpg");
        HomeCardList.add(a);

        a = new HomeCards("hamid69", "amad   janha", false,
                "http://musicgram-001-site1.1tempurl.com/samplemusic/mus3.ogg",
                56, 74);
        a.setThumbnail("https://api.androidhive.info/json/movies/3.jpg");
        a.setCaption("This is a #great app. I #love_you mr @mhrd7\n" +
                "#musigram\n#music_sharing\n@development_team ");
        HomeCardList.add(a);

        a = new HomeCards("mrsadeghian24", "amad bahare janha", true,
                "http://musicgram-001-site1.1tempurl.com/samplemusic/mus4.ogg",
                56, 74);
        a.setCaption("thats great!");
        a.setThumbnail("https://api.androidhive.info/json/movies/4.jpg");
        HomeCardList.add(a);
        a = new HomeCards("mrsadeghian24", "amad bahare janha", true, "", 56, 74);
        a.setCaption("thats great!");
        a.setThumbnail("https://api.androidhive.info/json/movies/5.jpg");
        HomeCardList.add(a);
        a = new HomeCards("mrsadeghian24", "amad bahare janha", true, "", 56, 74);
        a.setCaption("thats great!");
        a.setThumbnail("https://api.androidhive.info/json/movies/6.jpg");
        HomeCardList.add(a);
        a = new HomeCards("mrsadeghian24", "amad bahare janha", true, "", 56, 74);
        a.setCaption("thats great!");
        a.setThumbnail("https://api.androidhive.info/json/movies/7.jpg");
        HomeCardList.add(a);
        a = new HomeCards("mrsadeghian24", "amad bahare janha", true, "", 56, 74);
        a.setCaption("thats great!");
        a.setThumbnail("https://api.androidhive.info/json/movies/8.jpg");
        HomeCardList.add(a);

        adapter.notifyDataSetChanged();
    }
}
