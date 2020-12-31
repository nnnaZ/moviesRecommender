package MoviesServer.entity;

import java.util.List;

public class Lists<T> {
    private int totalPageNums;// 总页数
    private int currentPage;//当前页
    private List<T> list;//当前页电影列表

    public int getTotalPageNums() {
        return totalPageNums;
    }

    public void setTotalPageNums(int totalPageNums) {
        this.totalPageNums = totalPageNums;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
