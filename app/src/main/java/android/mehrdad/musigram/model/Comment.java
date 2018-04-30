package android.mehrdad.musigram.model;

/**
 * Created by Mr.Anonymous on 4/2/2018.
 */

public class Comment {
    private String person, thumbnailUrl, body, time;

    public Comment() {
    }

    public Comment(String person, String thumbnailUrl, String body, String time) {
        this.thumbnailUrl = thumbnailUrl;
        this.person = person;
        this.body = body;
        this.time = time;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}