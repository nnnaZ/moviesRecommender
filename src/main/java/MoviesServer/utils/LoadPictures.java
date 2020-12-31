package MoviesServer.utils;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 加载图片
 */
public class LoadPictures {
    public LoadPictures(){}

    /**
     * 根据imdbId获取到到电影的图片，将图片存储的名字为moviesId
     * @param imdbIdAndMoviesIdList key imdbId ;value moviesId
     * @return
     */
    public void loadPicturesUrl(Map<String,Integer> imdbIdAndMoviesIdList) {
        //存放图片链接
        ArrayList<String> urls = new ArrayList<>();
        //图片连接前面部分
        String headUrl = "https://www.imdb.com/title/tt";

        Set<String> imdbIdList = imdbIdAndMoviesIdList.keySet();

        for (String imdbId: imdbIdList) {
            String url = headUrl+imdbId;
            //下载图片到webapp下的image 图片名为moviesId
            getPictureUrlAndDownload(url,imdbIdAndMoviesIdList.get(imdbId));
        }

    }

    public void getPictureUrlAndDownload(String url,int moviesId){
        URL urlObj = null;
        URLConnection urlConnection = null;
        String pic_url = null;
//        图片存储路径为webapp下的image 图片名为moviesId
        String dir = "D:\\Java\\IdeaProjects\\Movies\\src\\main\\webapp\\image\\";

        try {
            urlObj = new URL(url);
            //打开URL连接
            urlConnection = urlObj.openConnection();
            // 将HTML内容解析成UTF-8格式
            Document doc = Jsoup.parse(urlConnection.getInputStream(), "utf-8", url);
            // 提取电影图片所在的HTML代码块
            Elements elems = doc.getElementsByClass("poster");
            //获取图片url地址
            pic_url = elems.first().getElementsByTag("img").attr("src");
            // 利用FileUtils.copyURLToFile()实现图片下载
            FileUtils.copyURLToFile(new URL(pic_url), new File(dir+Integer.toString(moviesId)+".png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
