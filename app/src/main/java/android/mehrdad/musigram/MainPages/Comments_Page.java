package android.mehrdad.musigram.MainPages;

import android.app.ProgressDialog;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.adapter.CommentAdapter;
import android.mehrdad.musigram.app.AppController;
import android.mehrdad.musigram.model.Comment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Comments_Page extends AppCompatActivity {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Cmnts json url
    private static final String url = "https://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private List<Comment> commentList = new ArrayList<Comment>();
    private ListView listView;
    private CommentAdapter adapter;

    EditText comment_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Button send = (Button) findViewById(R.id.comment_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
        comment_text = (EditText) findViewById(R.id.comment_text);

        comment_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    post();
                }
                return false;
            }
        });
        comment_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (comment_text.getText().toString().equals("")) {
                    send.setEnabled(false);
                    send.setTextColor(getResources().getColor(R.color.btnDisabled));
                } else {
                    send.setEnabled(true);
                    send.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView = (ListView) findViewById(R.id.comment_list);
        adapter = new CommentAdapter(this, commentList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        getData();

    }

    void post() {
        comment_text.setText("");
        Toast.makeText(getBaseContext(), "Posted", Toast.LENGTH_SHORT).show();
    }

    void getData() {
        // Creating volley request obj
        JsonArrayRequest CmntReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Comment Cmnt = new Comment();
                                Cmnt.setPerson(obj.getString("title"));
                                Cmnt.setThumbnailUrl(obj.getString("image"));
                                Cmnt.setBody("This is a #great app. I #love_you mr @mhrd7\n" +
                                        "#musigram\n#music_sharing\n@development_team ");
                                Cmnt.setTime(obj.getInt("releaseYear") + "");

                                // adding to array
                                commentList.add(Cmnt);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(CmntReq);
    }

    @Override
    public void onDestroy() {
        hidePDialog();
        super.onDestroy();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}
