package MoviesServer.dao.impl;

import MoviesServer.dao.CategoryDao;
import MoviesServer.entity.Movie;
import MoviesServer.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Set<String> moviesClassification() {
        HashSet<String> classify = new LinkedHashSet<>();

        String sql = "select genres from movies";
        //使用jdbcTemplate.query需要结果的类型类中含有set()方法，否则结果集中的值为null
//        List<Movie> classifyStringList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Movie>(Movie.class));
        List<String> classifyStringList = jdbcTemplate.queryForList(sql, String.class);

        String str1 = null;
        for (String str : classifyStringList) {
            if(str.startsWith("(")){// (no genres listed)
                if(str1 == null) str1 = str.substring(1,str.length()-1);
            }
            else{
                String[] split = str.split("\\|");
                for (String s : split) {
                    classify.add(s);
                }
            }
        }

        classify.add(str1);
        return classify;
    }
}
