package android.mehrdad.musigram.AccountPages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.mehrdad.musigram.MainPages.MainActivity;
import android.mehrdad.musigram.R;
import android.mehrdad.musigram.app.AppConfig;
import android.mehrdad.musigram.app.AppController;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity {

    Button btn;
    ImageView img;
    String pic_path;


    Map<String, String> _params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        img = (ImageView) findViewById(R.id.img);
        btn = (Button) findViewById(R.id.pic);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick();
            }
        });

        requestPermission();

        _params = new HashMap<String, String>();
        _params.put("username", "mhrd123");
        _params.put("password", "12345678");

    }

    void pick() {

        final CharSequence[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);

        builder.setTitle("Select Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Camera"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Build.VERSION.SDK_INT >= 24) {
                        try {
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp"
                            + ".jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Gallery")) {

                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);

                }

            }

        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                try {

                    String picturePath = Environment
                            .getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "temp" + ".jpg";

                    Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);

                    img.setImageBitmap(thumbnail);
                    pic_path = picturePath;
                    changePic();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail;
                try {
                    thumbnail = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));

                    img.setImageBitmap(thumbnail);
                    pic_path = picturePath;
                    changePic();

                } catch (Exception e) {
                    //
                }

            }
        }
    }

    private void changePic() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPROFILEPIC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tag", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    int code = jObj.getInt("code");

                    // Check for error node in json
                    if (code == 400) {
                        // user successfully logged in
                        // Create login session
//                        session.setLogin(true);
                        int a = 2;
                        // Now store the user in SQLite
                    /*    String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");

                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at);
*/
                        // Launch main activity
                        Intent intent = new Intent(Test.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = _params;

//                params.put("username", username);
//                params.put("password", password);

                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inDither = true;
                    options.inSampleSize = 8;

                    Bitmap bm1 = BitmapFactory.decodeFile(pic_path, options);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    String encodedImage1 = Base64.encodeToString(b,
                            Base64.DEFAULT);
                    // tt(encodedImage1.length()+"");

                    params.put("pic", encodedImage1);

                } catch (Exception e) {
                    Log.d("ppppppppiiiiiiiicccc", e.getMessage());
//                    tt("خطا در ارسال عکس");
                }


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private static final int REQUEST_WRITE_PERMISSION = 3541;

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            return;
//            openFilePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            openFilePicker();
        } else {
            this.finish();
        }
    }


}
