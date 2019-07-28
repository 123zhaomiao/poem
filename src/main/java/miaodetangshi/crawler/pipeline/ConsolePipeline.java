package miaodetangshi.crawler.pipeline;

import miaodetangshi.crawler.common.Page;

import java.util.Map;

public class ConsolePipeline implements Pipeline {
    @Override
    public void pipeline(Page page) {
       Map<String,Object> data =  page.getDataSet().getData();
       System.out.println(data);
    }
}
