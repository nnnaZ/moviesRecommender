package MoviesServer.service;

import java.util.Set;

public interface CategoryService {

    /**
     * 电影分类
     * @return
     */
    Set<String> moviesClassificationQuery();
}
