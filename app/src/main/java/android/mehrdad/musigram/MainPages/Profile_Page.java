package android.mehrdad.musigram.MainPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.app.AppConfig;
import android.mehrdad.musigram.app.AppController;
import android.mehrdad.musigram.model.Like;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Profile_Page extends Fragment {

    private ProgressDialog pDialog;
    String _user_name;
    String _following_num;
    String _followers_num;
    String _post_num;
    String _bio;
    String _pic_url;

    Toolbar toolbar;
    TextView post;
    TextView followers;
    TextView following;
    TextView bio;
    NetworkImageView pic;
    Button action;

    String un;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_profile);
        post = (TextView) view.findViewById(R.id.profile_post);
        followers = (TextView) view.findViewById(R.id.profile_followers);
        following = (TextView) view.findViewById(R.id.profile_following);
        pic = (NetworkImageView) view.findViewById(R.id.profile_image);
        action = (Button) view.findViewById(R.id.profile_button);
        bio = (TextView) view.findViewById(R.id.profile_bio);

        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        un = readFileAsString(getContext(),
                getContext().getFilesDir().getAbsolutePath() + "/.mg/data.txt")
                .split("@")[0];

//        toolbar.setTitle("mhrd7");
//        setSupportActionBar(toolbar);
//        Button b4 = (Button) view.findViewById(R.id.b4);
//        b4.setBackgroundColor(Color.RED);

        getProfileData("20011");

        return view;
    }

    void getProfileData(String id) {

        StringRequest prof_Req = new StringRequest(Request.Method.GET,
                AppConfig.URL_PROFILE + "?id=" + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Profile Response: " + response.toString());
                hidePDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    _user_name = jObj.getString("username");
                    _bio = jObj.getString("bio");
                    _followers_num = jObj.getString("followerNum");
                    _following_num = jObj.getString("followingNum");
                    _post_num = jObj.getString("postNum");
                    _pic_url = jObj.getString("pic");

                    set_datas();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(prof_Req, getTag());
    }

    void set_datas() {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        if (!_pic_url.equals("standard")) {
            pic.setImageUrl("https://api.androidhive.info/json/movies/1.jpg", imageLoader);
        }

        toolbar.setTitle(_user_name);
        post.setText(_post_num);
        followers.setText(_followers_num);
        following.setText(_following_num);
        bio.setText(_bio.equals("null") ? "" : _bio);

        action.setText(_user_name.equals(un) ? "Edit Profile" : "Follow");
//        hidePDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public String readFileAsString(Context context, String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(filePath)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
        } catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
            //
        }

        return stringBuilder.toString();
    }
}
