package Firebase;

public class Post {
    private String msg;
    private String username;
    private String img ;
    private int upVotes;
    private int downVotes;

    public Post() {
    }

    public Post(String msg, String username, String img, int upVotes, int downVotes) {
        this.msg = msg;
        this.username = username;
        this.img = img;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public String getMsg() {
        return msg;
    }

    public String getUsername() {
        return username;
    }

    public String getImg() {
        return img;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }
}
