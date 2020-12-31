//热门推荐
function tagNumsRecommender(){
    $.post("movies/moviesTagNumsTopN",{},function (data) {
        //data数据格式：{int moviesId，string title,int num 数目}
        var div = '<div>';
        for(var i = 0;i<5;i++){
            div += '<img src="image/'+data[i].moviesId+'.png" onclick="javascript:location.href=\'movie.html?moviesId='+data[i].moviesId+'\'">'+data[i].title+'</div><div>';
        }
        div += '<img src="image/'+data[5].moviesId+'.png" onclick="javascript:location.href=\'movie.html?moviesId='+data[i].moviesId+'\'">'+data[i].title+'</div>';
        $("#middle").html(div);
    });
}
//经典推荐
function avgRatingsRecommender(){
    $.post("movies/moviesAvgRatingTopN",{},function (data) {
        //data数据格式：{int moviesId，string title,int avg 平均分}
        var div = '<div>';
        for(var i = 0;i<5;i++){
            div += '<img src="image/'+data[i].moviesId+'.png" onclick="javascript:location.href=\'movie.html?moviesId='+data[i].moviesId+'\'">'+data[i].title+'</div><div>';
        }
        div += '<img src="image/'+data[5].moviesId+'.png" onclick="javascript:location.href=\'movie.html?moviesId='+data[i].moviesId+'\'">'+data[i].title+'</div>';
        $("#middle").html(div);
    });
}