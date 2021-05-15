package Model;

import java.util.Date;

public class Author_details {

    public String name;

    public Date created_at;



    public Author_details(String author , Date created_at) {
        this.name = author;

        this.created_at = created_at;
    }


    public String getAuthor() {
        return name;
    }



    public Date getCreated_at() {
        return created_at;
    }
}
