package miaodetangshi.crawler.common;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/*
存储清洗的数据
比如:标题:啥、作者:谁、正文:谁
*/
public class DataSet {
    private Map<String,Object> data = new HashMap<>();

    public void putData(String key,Object value){
        this.data.put(key,value);
    }

    public Object getData(String key){
        return this.data.get(key);
    }

    public Map<String,Object> getData(){
        //创建新对象、防止在外部恶意破坏数据
        return new HashMap<>(this.data);
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "data=" + data +
                '}';
    }
}
