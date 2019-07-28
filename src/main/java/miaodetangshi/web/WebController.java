package miaodetangshi.web;

import com.google.gson.Gson;
import miaodetangshi.analyze.model.AuthorCount;
import miaodetangshi.analyze.model.WordCount;
import miaodetangshi.analyze.service.AnalyzeService;
import miaodetangshi.config.ObjectFactory;
import miaodetangshi.crawler.Crawler;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/**
 * Web API
 * 1. Sparkjava 框架完成Web API开发
 * 2. Servlet 技术实现Web API的开发
 * 3. java-Httpd 实现Web API(纯Java语言实现的web服务器)
 *  socket技术、Http协议非常清楚
 */
public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    //->http://127.0.0.1:4567/
    // ->/analyze/author_count
    public List<AuthorCount> analyzeAuthorCount(){
        return analyzeService.analyzeAuthorCount();
    }
    //->http://127.0.0.1:4567/
    // ->/analyze/author_count
    public List<WordCount> analyzeWordCount(){
        return analyzeService.analyzeWordCount();
    }

    public void lauch(){
        ResponseTransformer responseTransformer
                = (ResponseTransformer) new JSONResoneseTransformer();

        //src/main/resouce/static
        Spark.staticFileLocation("/static");

        Spark.get("/analyze/author_count",((request,response)->analyzeAuthorCount()),
                responseTransformer);
        Spark.get("/analyze/word_clode",((request,response)->analyzeWordCount())
                ,responseTransformer);

        Spark.get("/crawler/stop",((request,response)-> {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.stop();
            return "爬虫停止";
        }));
    }

        public static class JSONResoneseTransformer implements ResponseTransformer{
        private  Gson gson = new Gson();

        @Override
        public String render(Object o) {
            return gson.toJson(o);
        }
    }

}
