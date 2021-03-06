package mytangshi;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
class ConnectionManager {
    //使用单利模式创建数据库连接池
    private static ConnectionManager instance;
    private static DruidDataSource dataSource;

    private ConnectionManager() {
        dataSource = new DruidDataSource();

        dataSource.setUsername("root");		//用户名
        dataSource.setPassword("12345"); //密码
        dataSource.setUrl("jdbc:mysql://localhost:3306/tangshi");//数据库地址
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(5); //初始化连接数
        dataSource.setMinIdle(1);//最小连接数
        dataSource.setMaxActive(20);//最大连接数
        dataSource.setMaxWait(50);//最长等待时间
    }

    public static final ConnectionManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public synchronized final Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

public class TestHtmlUnit {

    public static void main(String[] args) throws SQLException {
        System.out.println("使用连接池................................");
        for (int i = 0; i < 40; i++) {
            long beginTime = System.currentTimeMillis();
            Connection conn = ConnectionManager.getInstance().getConnection();
            try {
                PreparedStatement pstmt =
                        conn.prepareStatement("select * from poetry_info");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    // do nothing...
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("第" + (i + 1) + "次执行花费时间为:" + (endTime - beginTime));
        }

        System.out.println("不使用连接池................................");
        for (int i = 0; i < 40; i++) {
            long beginTime = System.currentTimeMillis();
            MysqlDataSource mds = new MysqlDataSource();
            mds.setURL("jdbc:mysql://localhost:3306/tangshi?useSSL=false");
            mds.setUser("root");
            mds.setPassword("12345");
            Connection conn = mds.getConnection();
            try {
                PreparedStatement pstmt =
                        conn.prepareStatement("select * from poetry_info");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    // do nothing...
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("第" + (i + 1) + "次执行花费时间为:"
                    + (endTime - beginTime));
        }
    }
}
