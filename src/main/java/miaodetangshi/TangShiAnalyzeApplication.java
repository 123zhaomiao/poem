package miaodetangshi;

import miaodetangshi.config.ObjectFactory;
import miaodetangshi.crawler.Crawler;
import miaodetangshi.web.WebController;

public class TangShiAnalyzeApplication {
    public static void main(String[] args) {

        WebController webController =
                ObjectFactory.getInstance().getObject(WebController.class);
        //运行web接口
        webController.lauch();
        webController.lauch1();
        if(args.length ==  1 && args[0].equals("run") ) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.start();
        }
    }
}
