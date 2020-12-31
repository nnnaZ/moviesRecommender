package MoviesServer.dao;

import java.util.Set;

public interface CategoryDao {

    /**
     * 获取电影分类的类别
     * @return
     */
    Set<String> moviesClassification();
}
