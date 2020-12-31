package DataLoader;

import MoviesServer.utils.JDBCUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BasicsLoadData {
    private String  pathName;

    public BasicsLoadData(String  pathName){
        this.pathName = pathName;
    }

    public void readCsvToTable(){

        try {
            //获取数据库连接
            Connection connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
/*使用org.apache.commons.csv.CSVParser读取csv文件   一开始可以用，导入mahout之后不能用了*/
            File file = new File(pathName);
            Reader reader = new BufferedReader(new FileReader(file));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            //去掉后缀文件名  “.”和“|”都是转义字符,必须得加"\\";
            String csvName = pathName.split("/")[6].split("\\.")[0];

            //String[] record.values里面存放获取到的数据值
            //records的第一行为读取表的表头字段，需要跳过，所以定义了变量g
            if("links".equals(csvName)){//数据格式{moviesId，imdbid，tmdbid}
                String sql = "insert into links value(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int g = 0;
                for (CSVRecord record : records) {
                    if(g == 0){
                        g++;
                        continue;
                    }
                    preparedStatement.setInt(1,Integer.parseInt(record.get(0)));
                    preparedStatement.setString(2,record.get(1));
                    preparedStatement.setString(3,record.get(2));
                    preparedStatement.addBatch();
                }
                //提交剩余的数据
                preparedStatement.executeBatch();
                connection.commit();
                JDBCUtils.close(connection,preparedStatement);
                System.out.println("links.csv数据加载完成");
            }
            if("movies".equals(csvName)){//数据格式{moviesId，title，genres}
                String sql = "insert into movies value(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int g = 0;
                for (CSVRecord record : records) {
                    if(g == 0){
                        g++;
                        continue;
                    }
                    preparedStatement.setInt(1,Integer.parseInt(record.get(0)));
                    preparedStatement.setString(2,record.get(1));
                    preparedStatement.setString(3,record.get(2));
                    preparedStatement.addBatch();
                }
                //提交剩余的数据
                preparedStatement.executeBatch();
                connection.commit();
                JDBCUtils.close(connection,preparedStatement);
                System.out.println("movies.csv数据加载完成");
            }
            if("ratings".equals(csvName)){//数据格式{userId,moviesId，Rating，timestamp}
                String sql = "insert into ratings value(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int g = 0;
                for (CSVRecord record : records) {
                    if(g == 0){
                        g++;
                        continue;
                    }
                    preparedStatement.setInt(1,Integer.parseInt(record.get(0)));
                    preparedStatement.setInt(2,Integer.parseInt(record.get(1)));
                    preparedStatement.setDouble(3,Double.parseDouble(record.get(2)));
                    preparedStatement.setLong(4,Long.parseLong(record.get(3)));
                    preparedStatement.addBatch();
                    //小批量提交,避免OOM
                    if((g+1) % 20000 == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                    g++;
                }
                //提交剩余的数据
                preparedStatement.executeBatch();
                connection.commit();
                JDBCUtils.close(connection,preparedStatement);
                System.out.println("ratings.csv数据加载完成");
            }
            if("tags".equals(csvName)){//数据格式{userId,moviesId，tag，timestamp}
                String sql = "insert into tags value(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int g = 0;
                for (CSVRecord record : records) {
                    if(g == 0){
                        g++;
                        continue;
                    }
                    preparedStatement.setInt(1,Integer.parseInt(record.get(0)));
                    preparedStatement.setInt(2,Integer.parseInt(record.get(1)));
                    preparedStatement.setString(3,record.get(2));
                    preparedStatement.setLong(4,Long.parseLong(record.get(3)));
                    preparedStatement.addBatch();
//                    if((g+1) % 1000 == 0) {
//                        preparedStatement.executeBatch();
//                        preparedStatement.clearBatch();
//                    }
//                    g++;
                }
                //提交剩余的数据
                preparedStatement.executeBatch();
                connection.commit();
                JDBCUtils.close(connection,preparedStatement);
                System.out.println("tags.csv数据加载完成");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
