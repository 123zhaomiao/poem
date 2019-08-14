package miaodetangshi.analyze.model;

import lombok.Data;

@Data
public class WordCount{
    private String word;
    private Integer count;
    public void setWord(String word) {
        this.word = word;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "word='" + word + '\'' +
                ", count=" + count;
    }
}
