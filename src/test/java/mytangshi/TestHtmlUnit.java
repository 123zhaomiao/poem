package mytangshi;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class TestHtmlUnit {
    public static void main(String[] args) {
        //chrome浏览器
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
            try {
                //不解析js
                webClient.getOptions().setJavaScriptEnabled(false);

                //返回值是:<P extends Page> 即返回值是Page或者Page的子类
                HtmlPage htmlPage =  webClient.getPage("https://www.gushiwen.org/");
                //获取正文 返回值为HtmlElement
                HtmlElement htmlElement = htmlPage.getBody();

                //文本asTest、 网页 asXml
                String text = htmlElement.asText();
                System.out.println(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void test(){
        //                //采集
//                HtmlDivision domElement =
//                        (HtmlDivision)htmlPage.getElementById("contson4a96c8287eb5");
//                System.out.println(domElement.getClass().getName());
//                String divContent = domElement.asText();
//                System.out.println(divContent);

//                HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_eeb217f8cb2d.aspx");
//                HtmlElement body = htmlPage.getBody();
//                //标题:
//                String titlePath = "//div[@class='cont']/h1/text()";
//                DomText titleDom = (DomText)body.getByXPath(titlePath).get(0);
//                String title = titleDom.asText();
//                //朝代
//                String dynastyPath = "//div[@class='cont']/p/a[1]";
//                HtmlAnchor dynastyDom = (HtmlAnchor)body.getByXPath(dynastyPath).get(0);
//                String  dynasty = dynastyDom.asText();
//
//                //作者
//                String authorPath = "//div[@class='cont']/p/a[2]";
//                HtmlAnchor anchorDom = (HtmlAnchor)body.getByXPath(authorPath).get(0);
//                String author = anchorDom.asText();
//
//                //正文
//                String contentPath = "//div[@class='cont']/div[@class='contson']";
//                HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
//                String content = contentDom.asText();


    }
}
