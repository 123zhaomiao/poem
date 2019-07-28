package miaodetangshi.analyze.dao;

import miaodetangshi.analyze.entity.PoetryInfo;
import miaodetangshi.analyze.model.AuthorCount;

import java.util.List;

public interface AnalyzeDao {
    //分析唐诗中作者与作者创作数量之间的关系
    List<AuthorCount> analyzeAuthorCount();
    //词云 分析唐诗的内容看哪些此频率最高
    List<PoetryInfo> queryAllPoetryInfo();
}
