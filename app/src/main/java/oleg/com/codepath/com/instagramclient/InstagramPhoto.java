package oleg.com.codepath.com.instagramclient;

/**
 * Created by oleg on 9/13/2014.
 */
public class InstagramPhoto {
    // url, , height, username, caption
    private  String username;
    private String caption;
    private String imageUrl;
    private int imageHeight;
    private int imageWidth;
    private int likes_count;
    private String profilePicUrl;
    private String[] comment = new String[PhotoActivity.NUM_COMMENTS];
    private String[] fromUser = new String[PhotoActivity.NUM_COMMENTS];;


    // public String toString() {
    //    return "Hello" + getImageUrl();
   // }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setFromUser(int ind, String value) {
        fromUser[ind] = value;
    }

    public String getFromUser (int ind) {
        return fromUser[ind];
    }
    public void setComment(int ind, String value) {
        comment[ind] = value;
    }

    public String getComment (int ind) {
        return comment[ind];
    }
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }



}
