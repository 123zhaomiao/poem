package miaodetangshi.web;

import miaodetangshi.analyze.model.AuthorCount;
import miaodetangshi.analyze.model.WordCount;
import miaodetangshi.analyze.service.AnalyzeService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    public List<AuthorCount> analyzeAuthorCount(){
        return analyzeService.analyzeAuthorCount();
    }

    public List<WordCount> analyzeWordCount(){
        return analyzeService.analyzeWordCount();
    }
    public void lauch(){
        List<AuthorCount> list = analyzeAuthorCount();
        System.out.println("总计:"+list.size());
        for(int i = 0 ; i < list.size();i++){
            System.out.println(list.get(i));
        }
    }
    public void lauch1(){
        List<WordCount>  list1 = analyzeWordCount();
        System.out.println("总计:"+list1.size());

        Collections.sort(list1, new Comparator<WordCount>() {
            @Override
            public int compare(WordCount o1, WordCount o2) {
                return o2.getCount()-o1.getCount();
            }
        });
        for(int i = 0 ; i < list1.size();i++){
            System.out.println(list1.get(i));
        }
    }
}
