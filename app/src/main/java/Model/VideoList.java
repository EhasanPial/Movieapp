package Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList {
    private String id;

    @SerializedName("results")
    private List<Video> videoList ;

    public VideoList(String id, List<Video> videoList) {
        this.id = id;
        this.videoList = videoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}

