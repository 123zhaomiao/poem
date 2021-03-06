package miaodetangshi.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

public class Page {
    //数据网站的根地址:https://www.gushiwen.org/
    private final String base;
    //具体网页的路径
    private final String path;
    //标识网页是否是详情页
    private final boolean detail;
    //网页DOM对象
    private HtmlPage htmlPage;
    //非详情页子页面对象集合
    private Set<Page> subpage = new HashSet<>();
    //数据对象(最终要提取数据)
    DataSet dataSet = new DataSet();

    //具体的url = base+path 并且两个属性用final修饰相当于必须从构造方法中传入值
    public String getUrl(){
        return this.base + this.path;
    }

    public String getBase() {
        return base;
    }

    public boolean isDetail() {
        return detail;
    }

    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    public void setHtmlPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public Set<Page> getSubpage() {
        return subpage;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public Page(String base, String path, boolean detail) {
        this.base = base;
        this.path = path;
        this.detail = detail;
    }
}
