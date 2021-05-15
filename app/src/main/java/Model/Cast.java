package Model;

public class Cast {
    private String original_name;
    private String name;

    private String profile_path ;

    public Cast(String original_name, String name, String profile_path) {
        this.original_name = original_name;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
