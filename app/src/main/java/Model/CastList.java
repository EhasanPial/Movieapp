package Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastList {

    @SerializedName("cast")
    List<Cast> casts = null;


    public List<Cast> getCasts() {
        return casts;
    }
}

