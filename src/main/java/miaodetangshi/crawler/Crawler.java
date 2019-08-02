package miaodetangshi.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import miaodetangshi.crawler.common.Page;
import miaodetangshi.crawler.parse.Parse;
import miaodetangshi.crawler.pipeline.Pipeline;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 爬虫调度窗口
 */
public class Crawler {
    //放置文档页面(只是超链接) 也就是未被执行的页面
    private final Queue<Page> docQueue = new LinkedBlockingQueue<>();
    //放置详情页面(处理完成)
    private final Queue<Page> detailQueue = new LinkedBlockingQueue<>();
    //采集器
    private final WebClient webClient;

    //所有的解析器
    private List<Parse> parseList = new LinkedList<>();
    //所有的清洗器
    private List<Pipeline> pipelineList = new LinkedList<>();

    //线程调度器
    private final ExecutorService executorService;

    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);

        //初始化线程池为固定大小的线程池
        this.executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
            private  final AtomicInteger id = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thread-"+id.getAndIncrement());
                return thread;
            }
        });

    }
    public void addPage(Page page){
        this.docQueue.add(page);
    }
    public void addPipeline(Pipeline pipeline){
        this.pipelineList.add(pipeline);
    }
    public void addParse(Parse parse){
        this.parseList.add(parse);
    }

    public void start(){
        //submit(Runnable对象)
        //1.爬取、解析
        this.executorService.submit(this::parse);
        //2.清理
        this.executorService.submit(this::pipeline);
    }
    //爬虫的具体实现
    private void parse(){
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //1.从未被处理的页面中出队列一个准备进行爬取
            final Page page = Crawler.this.docQueue.poll();
            if (page == null) {
                continue;
            }
            //2.提交一个任务
            this.executorService.submit(() -> {
                try {
                    //2.1 根据url取得页面
                    //采集
                    HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                    page.setHtmlPage(htmlPage);
                    //2.2 给解析器解析、解析器有两个、根据页面判断用何种方法解析
                    for(Parse parse:Crawler.this.parseList){
                        parse.parse(page);
                    }
                    //2.3 如果取出的页面是个详情页面
                    if(page.isDetail()){
                        //2.3.1 detailQueue 加入处理过的页面
                        Crawler.this.detailQueue.add(page);
                    }else{
                        Iterator<Page> iterator =  page.getSubpage().iterator();
                        while(iterator.hasNext()){
                            Page subPage = iterator.next();
                            Crawler.this.docQueue.add(subPage);
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    private void pipeline(){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //1.从处理过的页面中取出一个页面
            final Page page = this.detailQueue.poll();
            if(page == null){
                continue;
            }
            this.executorService.submit(() -> {
                //2.进行解析
                for(Pipeline pipeline:Crawler.this.pipelineList){
                    pipeline.pipeline(page);
                }
            });
        }
    }
    public void stop(){
        //停止爬虫
        if(this.executorService!=null && this.executorService.isShutdown()){
            this.executorService.shutdown();
        }
    }
}
