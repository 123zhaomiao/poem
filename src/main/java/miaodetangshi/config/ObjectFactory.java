package miaodetangshi.config;

import com.alibaba.druid.pool.DruidDataSource;
import miaodetangshi.analyze.dao.AnalyzeDao;
import miaodetangshi.analyze.dao.impl.AnalyzeDaoImpl;
import miaodetangshi.analyze.service.AnalyzeService;
import miaodetangshi.analyze.service.impl.AnaylzeServiceImpl;
import miaodetangshi.crawler.Crawler;
import miaodetangshi.crawler.common.Page;
import miaodetangshi.crawler.parse.DataPageParse;
import miaodetangshi.crawler.parse.DocumentParse;
import miaodetangshi.crawler.pipeline.DatabasePipeline;
import miaodetangshi.Print;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public final class ObjectFactory {
    //单例模式 饿汗
    private static final ObjectFactory instance = new ObjectFactory();
    private  final Map<Class,Object> objectHashMap = new HashMap<>();

    private ObjectFactory(){
        //1.初始化配置对象
        initConfigProperties();
        //2.创建数据源对象
        initDatabase();
        //3.爬虫对象
        initCrawler();
        //4.打印
        initPrint();
    }
    private void initPrint() {
        DataSource dataSource = getObject(DataSource.class);

        AnalyzeDao analyzeDao= new AnalyzeDaoImpl(dataSource);
        AnalyzeService anaylzeService = new AnaylzeServiceImpl(analyzeDao);

        Print print = new Print(anaylzeService);
        objectHashMap.put(Print.class, print);
    }
    private void initCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);

        final Page page = new Page(configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath()
        ,configProperties.isCrawlerDetail());

        //2.new一个爬虫的调度器
        Crawler crawler = new Crawler();
        //将页面加入到page中
        crawler.addPage(page);

        //添加解析器 分为详情页的解析和超链接页的解析
        crawler.addParse(new DocumentParse());
        crawler.addParse(new DataPageParse());

        crawler.addPipeline(new DatabasePipeline(dataSource));
        objectHashMap.put(Crawler.class,crawler);
    }

    private void initDatabase() {

        ConfigProperties configProperties = getObject(ConfigProperties.class);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

        objectHashMap.put(DataSource.class,dataSource);
    }

    private void initConfigProperties() {
        ConfigProperties configProperties =
                new ConfigProperties();
        objectHashMap.put(ConfigProperties.class,configProperties);
    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    public <T> T getObject(Class classz){
        if(!objectHashMap.containsKey(classz)){
            throw new IllegalArgumentException("Class" + classz.getName()+
            "not found object");
        }
        return (T)objectHashMap.get(classz);
    }
}
