package android.mehrdad.musigram.model;

public class HomeCards {
    private String person, title, caption;
    private int likes, comments;
    private boolean isLiked;
    private String thumbnail;
    private String soundUrl;

    public HomeCards() {
    }

    public HomeCards(String person, String title, boolean isLiked, String soundUrl, int likes, int comments) {
        this.person = person;
        this.title = title;
        this.isLiked = isLiked;
        this.soundUrl = soundUrl;
        this.likes = likes;
        this.comments = comments;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public boolean getIsLike() {
        return isLiked;
    }

    public void setIsLike(boolean likes) {
        this.isLiked = isLiked;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }
}