package miaodetangshi.analyze.model;

import lombok.Data;

@Data
public class AuthorCount {
    private String author;
    private Integer count;
    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "author='" + author + '\'' +
                ", count=" + count ;
    }
}
