package miaodetangshi.analyze.service.impl;

import miaodetangshi.analyze.dao.AnalyzeDao;
import miaodetangshi.analyze.entity.PoetryInfo;
import miaodetangshi.analyze.model.AuthorCount;
import miaodetangshi.analyze.model.WordCount;
import miaodetangshi.analyze.service.AnalyzeService;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

public class AnaylzeServiceImpl implements AnalyzeService {

    private final AnalyzeDao analyzeDao;
    public AnaylzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        //此处结果并未排序
        //排序方式1、DAO层用语句排序2、在业务层对List集合进行排序
        List<AuthorCount> authorCounts =  analyzeDao.analyzeAuthorCount();
        authorCounts.sort(new Comparator<AuthorCount>() {
            @Override
            public int compare(AuthorCount o1, AuthorCount o2) {
                return -o1.getCount().compareTo(o2.getCount());
            }
        });
        return authorCounts;
    }

    @Override
    public List<WordCount> analyzeWordCount() {
        //1.查询出所有的数据
        //2.取出 title content
        //3.分词 过滤 /w null length < 2
        //4.统计k—v k是词 v是频率
        Map<String,Integer> map = new HashMap<>();
        List<PoetryInfo> poetryInfos = analyzeDao.queryAllPoetryInfo();

        for(PoetryInfo poetryInfo:poetryInfos){

            List<Term> terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();

            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            Iterator<Term> iterator = terms.iterator();
            while(iterator.hasNext()){
                Term term = iterator.next();
                //过滤掉词性为w或者词性为null的值
                if(term.getNatureStr() == null ||
                        term.getNatureStr().equals("w")){
                    iterator.remove();
                    continue;
                }
                //过滤长度
                if(term.getRealName().length()<2){
                    iterator.remove();
                    continue;
                }

                String realName = term.getRealName();
                Integer count = 0 ;

                if(map.containsKey(realName)){
                   count =  map.get(realName)+1;
                }else{
                    count = 1;
                }
                map.put(realName,count);
            }
        }
        List<WordCount> wordCounts = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }
}
