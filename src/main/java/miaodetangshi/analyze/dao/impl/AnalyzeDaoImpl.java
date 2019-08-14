package miaodetangshi.analyze.dao.impl;

import miaodetangshi.analyze.dao.AnalyzeDao;
import miaodetangshi.analyze.entity.PoetryInfo;
import miaodetangshi.analyze.model.AuthorCount;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  AnalyzeDaoImpl implements AnalyzeDao {
    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> datas = new ArrayList<>();
        String sql = " select count(*) as count ,author from poetry_info group by author;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
            while (rs.next()){
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(rs.getString("author"));
                authorCount.setCount(rs.getInt("count"));
                datas.add(authorCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetryInfo() {
        List<PoetryInfo> datas = new ArrayList<>();
        String sql = " select title,dynasty,author,content from poetry_info;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ){
            while(rs.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(rs.getString("title"));
                poetryInfo.setDenasty(rs.getString("dynasty"));
                poetryInfo.setAuthor(rs.getString("author"));
                poetryInfo.setContent(rs.getString("content"));

                datas.add(poetryInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
