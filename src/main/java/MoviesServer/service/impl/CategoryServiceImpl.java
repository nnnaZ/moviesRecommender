package MoviesServer.service.impl;

import MoviesServer.dao.CategoryDao;
import MoviesServer.dao.impl.CategoryDaoImpl;
import MoviesServer.service.CategoryService;

import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Set<String> moviesClassificationQuery() {
        return categoryDao.moviesClassification();
    }
}
