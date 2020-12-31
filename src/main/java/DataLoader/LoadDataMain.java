package DataLoader;


/**
 * 读取ml-25m（数据量很大）的一个数据集  读取tags.csv  216640
 *                     ratings.csv数据加载完成 3781125
 */
public class LoadDataMain {
    public static void main(String[] args) {
//        String path = LoadDataMain.class.getClassLoader().getResource("movies.csv").getPath();
        String[] pathNames = {"D:/Java/IdeaProjects/Movies/target/classes/movies.csv"
                            ,"D:/Java/IdeaProjects/Movies/target/classes/ratings.csv"
                            ,"D:/Java/IdeaProjects/Movies/target/classes/tags.csv"
                            ,"D:/Java/IdeaProjects/Movies/target/classes/links.csv"};

        for(String pathName : pathNames){
            Long startTime = System.currentTimeMillis();
            BasicsLoadData basicsLoadData = new BasicsLoadData(pathName);
            basicsLoadData.readCsvToTable();
            System.out.println(System.currentTimeMillis()-startTime);
        }
    }
}
