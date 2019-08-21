package miaodetangshi;

import miaodetangshi.config.ObjectFactory;
import miaodetangshi.crawler.Crawler;

public class TangShiAnalyzeApplication {
    public static void main(String[] args) {
        if(args.length ==  1 && args[0].equals("run") ) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.start();
        }
        Print print =
                ObjectFactory.getInstance().getObject(Print.class);
        //运行web接口
        print.lauch();
        print.lauch1();
    }
}
