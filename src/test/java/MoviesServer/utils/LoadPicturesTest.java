package MoviesServer.utils;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadPicturesTest {

    @Test
    public void loadPicturesTest(){
        Map<String,Integer> map = new HashMap<>();
        map.put("0114709",1);
        LoadPictures loadPictures = new LoadPictures();
        loadPictures.loadPicturesUrl(map);

    }
}
