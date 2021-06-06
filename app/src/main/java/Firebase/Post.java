package Firebase;

import java.util.List;

public class Post {
    private long id;
    private String des;
    private String username;
    private String img;
    private float rating;
    private String movieTitle;
    private List<Comments> commentsList;

    public Post() {
    }


    public Post(long id, String des, String username, String img, float rating, String movieTitle, List<Comments> commentsList) {
        this.id = id;
        this.des = des;
        this.username = username;
        this.img = img;
        this.rating = rating;
        this.movieTitle = movieTitle;
        this.commentsList = commentsList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }


}
