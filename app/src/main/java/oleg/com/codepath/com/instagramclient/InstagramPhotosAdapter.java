package oleg.com.codepath.com.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by oleg on 9/14/2014.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>{
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        super(context, android.R.layout.simple_list_item_1, photos);
    }
    // built in getView method (int position)
    //default takes the model and converts to string instagramPhoto.toString()  -
    // need to have toString in instagramPhoto

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //take the data, source and return view for this data item at the position
        // get the data item
        InstagramPhoto photo = getItem(position);

        // check if use recycled view
        if (convertView == null) {
            // doesnt exist? load view from xml template
            // false means don't attach to view yet
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // get sub-views in templates
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgView);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        ImageView imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView  tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView  tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);
        TextView tvFromUser1 = (TextView) convertView.findViewById(R.id.tvFromUser1);
        TextView tvFromUser2 = (TextView) convertView.findViewById(R.id.tvFromUser2);
        // populate sub-views with correct data
        tvCaption.setText(photo.getCaption());
        tvUserName.setText(photo.getUsername());
        imgPhoto.getLayoutParams().height = photo.getImageHeight();
        imgPhoto.getLayoutParams().width = photo.getImageWidth();
        tvLikes.setText(photo.getLikes_count()+ " Likes");
        // hard coded 2 comments. Can be done with a loop for any number of comments
        if(photo.getComment(0) != "") {
            tvComment1.setText(photo.getComment(0));
            tvFromUser1.setText(photo.getFromUser(0)+ ":");
        } else {
            tvComment1.setVisibility(View.GONE );
            tvFromUser1.setVisibility(View.GONE );
        }
        if(photo.getComment(1) != "") {
            tvComment2.setText(photo.getComment(1));
            tvFromUser2.setText(photo.getFromUser(1) + ":");
        } else {
            tvComment2.setVisibility(View.GONE );
            tvFromUser2.setVisibility(View.GONE );
        }
        // reset the image from the recycled view
        // image is invisible until loaded from netwrk
        imgPhoto.setImageResource(0);
        imgProfile.setImageResource(0);
        // load image based on URL, i.e. send image request, download image bytes, convert to bitmap
        // insert bitmap into image view, may be resize image
        // use picasso library here
        Picasso.with(getContext()).load(photo.getImageUrl()).into(imgPhoto);
        Picasso.with(getContext()).load(photo.getProfilePicUrl()).into(imgProfile);
        // return the view
        return convertView;
    }
}
