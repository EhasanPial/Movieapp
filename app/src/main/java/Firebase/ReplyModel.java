package Firebase;

public class ReplyModel {

    private String msg;
    private String userId;

    public ReplyModel() {
    }

    public ReplyModel(String msg, String userId) {
        this.msg = msg;
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


