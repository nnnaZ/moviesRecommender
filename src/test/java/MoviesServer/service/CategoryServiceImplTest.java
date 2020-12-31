package MoviesServer.service;

import MoviesServer.service.impl.CategoryServiceImpl;
import org.junit.Test;

import java.util.Set;

public class CategoryServiceImplTest {

    @Test
    public void moviesClassificationQueryTest() {
        CategoryService categoryService = new CategoryServiceImpl();
        Set<String> strings = categoryService.moviesClassificationQuery();
        for (String str : strings) {
            System.out.println(str);
        }
    }
}
