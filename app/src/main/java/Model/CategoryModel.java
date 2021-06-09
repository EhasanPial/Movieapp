package Model;

public class CategoryModel {

    private int type ;
    private String categoryName;


    public CategoryModel(int type, String categoryName) {
        this.type = type;
        this.categoryName = categoryName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
