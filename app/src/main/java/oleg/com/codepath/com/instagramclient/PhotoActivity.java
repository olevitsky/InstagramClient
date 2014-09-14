package oleg.com.codepath.com.instagramclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotoActivity extends Activity {
    public static final String CLIENT_ID = "8242f2d612d942128123ff382f72121c";
    public static final int NUM_COMMENTS = 2;
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        photos = new ArrayList<InstagramPhoto>();
        // create adapter and bind to the data in array list
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // populate data into list view
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // set adapter to list view - trigger population of items
        lvPhotos.setAdapter(aPhotos);
        //https://api.instagram.com/v1/media/popular?client_id=8242f2d612d942128123ff382f72121c
        //setup popular URL endpoint
        String popularURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //create network client
        AsyncHttpClient client = new AsyncHttpClient();
        //trigger network request
        client.get(popularURL, new JsonHttpResponseHandler() {
        //async network request

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // fired when successful response coming back
                // response object is popular photo JSON object
                //{"data"=> [x] => "images" => "standard resolution" => "url"
              //  Log.i("INFO" , response.toString());
                // url, , height, username, caption
                //{"data"=> [x] => "images" => "standard resolution" => "url"
                //{"data"=> [x] => "user" => "username"
                //{"data"=> [x] => "caption" => "text"
                JSONArray photosJSON = null; // support the fact that JSON can stale
                try {
                    photos.clear();
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        if(photoJSON.has("caption") && !photoJSON.isNull("caption")) {
                            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        }
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setImageWidth(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width"));
                        if( photoJSON.has("likes")&& !photoJSON.isNull("likes")) {
                            photo.setLikes_count(photoJSON.getJSONObject("likes").getInt("count"));
                        } else {
                            photo.setLikes_count(0);
                        }
                        photo.setProfilePicUrl(photoJSON.getJSONObject("user").getString("profile_picture"));
                        for (int j = 0; j < NUM_COMMENTS ; j++) {
                            photo.setComment(j,"");
                            photo.setFromUser(j, "");
                        }
                        JSONArray commentArray = photoJSON.getJSONObject("comments").getJSONArray("data");
                        for (int j = 0; j < NUM_COMMENTS && j < commentArray.length(); j++) {
                            photo.setComment(j,commentArray.getJSONObject(j).getString("text") );
                            photo.setFromUser(j, commentArray.getJSONObject(j).getJSONObject("from").getString("username"));

                        }

                        //Log.i("info" , photo.getUsername());
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();

                } catch (JSONException e) {
                    // Fire if json parser fails
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        //handle successful response of network request
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
