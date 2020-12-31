//搜索搜索框中的查询结果 分类的土拍你结果查询  全部图片结果查询
function loadMoviesAndPageNumber(pid, genres,searchText) {
    $.post("movies/moviesAndPageNumber", {pid: pid, genres: genres,searchText:searchText}, function (data) {
        // 数据格式：{data.currentPge当前页,data.totalPageNums总页数,data.list当前页的电影信息}
        //图片 186*268
        var picDiv = '<div class="movies-inf-one">';
        for (var i = 0; i < data.list.length; i++) {
            picDiv += '<span><img src="image/' + data.list[i].pictureUrl + '.png" onclick="javascript:location.href=\'movie.html?moviesId='+data.list[i].moviesId+'\'">' + data.list[i].title + '</span>';
            if (i == 4) picDiv += '</div><div class="movies-inf-two">';
            if (i == 9) picDiv += '</div><div class="movies-inf-three">';
            if (i == 14) picDiv += '</div><div class="movies-inf-four">';
        }
        picDiv += '</div>';
        $("#middle-pictures").html(picDiv);
        var moviesIdn = data.list[data.list.length-1].moviesId;

        //页码
        var pageDiv = '<ul>';
        var totalPages = Math.ceil(data.totalPageNums / 20);
        var firP = '<li><a href="javascript:loadMoviesAndPageNumber(1, '+genres+')">第一页</a></li>';
        var preId;
        if(pid == 1){
            preId = 1;
        }else {
            preId = pid-1;
        }

        var preP = '<li><a href="javascript:loadMoviesAndPageNumber('+preId+', '+genres+')">上一页</a></li>';
        var curP = '<li>第'+pid+'页，共'+totalPages+'页</li>';
        var nexId;
        if(pid == totalPages){
            nexId = totalPages;
        }else{
            nexId = pid +1;
        }
        var nexP = '<li><a href="javascript:loadMoviesAndPageNumber('+nexId+', '+genres+')">下一页</a></li>'
        var endP = '<li><a href="javascript:loadMoviesAndPageNumber('+totalPages+', '+genres+')">最后一页</a></li>';
        pageDiv += firP;
        pageDiv += preP;
        pageDiv += curP;
        pageDiv += nexP;
        pageDiv += endP;
        $("#pageNumber").html(pageDiv);

        //定位到页面顶部
        window.scrollTo(0,0);
    });
}