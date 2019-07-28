package miaodetangshi.crawler.parse;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import miaodetangshi.crawler.common.Page;

public class DocumentParse implements Parse{
    @Override
    public void parse(Page page) {
        if(page.isDetail()){
            return;
        }
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody().getElementsByAttribute("div",
                "class","typecont")
                .forEach(htmlElement -> {
                        DomNodeList<HtmlElement> aNodeList =
                                htmlElement.getElementsByTagName("a");
                        aNodeList.forEach(
                                aNode ->{
                                    String path = aNode.getAttribute("href");
                                    //新建一个详情页
                                    Page page1 = new Page(page.getBase(),path,true);
                                    //将详情页加入子页面的集合
                                    page.getSubpage().add(page1);
                                }
                        );
                });
    }
}
