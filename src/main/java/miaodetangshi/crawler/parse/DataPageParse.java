package miaodetangshi.crawler.parse;

import com.gargoylesoftware.htmlunit.html.*;
import miaodetangshi.crawler.common.Page;

/**
 * 详情页面解析
 */
public class DataPageParse implements Parse {
    @Override
    public void parse(Page page) {
        //如果不是详情页面直接返回
        if(!page.isDetail()){
            return;
        }else{
            HtmlPage htmlPage = page.getHtmlPage();
            //获取body
            HtmlElement body = htmlPage.getBody();
            //标题:
            String titlePath = "//div[@class='cont']/h1/text()";
            DomText titleDom = (DomText)body.getByXPath(titlePath).get(0);
            String title = titleDom.asText();
            //朝代
            String dynastyPath = "//div[@class='cont']/p/a[1]";
            HtmlAnchor dynastyDom = (HtmlAnchor)body.getByXPath(dynastyPath).get(0);
            String  dynasty = dynastyDom.asText();
            //作者
            String authorPath = "//div[@class='cont']/p/a[2]";
            HtmlAnchor anchorDom = (HtmlAnchor)body.getByXPath(authorPath).get(0);
            String author = anchorDom.asText();
            //正文
            String contentPath = "//div[@class='cont']/div[@class='contson']";
            HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
            String content = contentDom.asText();

            page.getDataSet().putData("title",title);
            page.getDataSet().putData("dynasty",dynasty);
            page.getDataSet().putData("author",author);
            page.getDataSet().putData("content",content);
        }
    }
}
