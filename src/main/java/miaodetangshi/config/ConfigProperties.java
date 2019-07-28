package miaodetangshi.config;

import lombok.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Data
public class ConfigProperties {
    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClass;

    public ConfigProperties(){
        //从外部文件加载
        InputStream inputStream = ConfigProperties.class.getClassLoader()
                .getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.crawlerBase = String.valueOf(p.get("crawler.base"));
        this.crawlerPath = String.valueOf(p.get("crawler.path"));
        this.crawlerDetail = Boolean.parseBoolean(String.valueOf(p.get("crawler.detail")));

        this.dbUsername = String.valueOf(p.get("db.username"));
        this.dbPassword = String.valueOf(p.get("db.password"));
        this.dbDriverClass = String.valueOf(p.get("db.driver_class"));
        this.dbUrl = String.valueOf(p.get("db.url"));


    }
}
