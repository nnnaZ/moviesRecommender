
function getItemCFRecommenderPictures(moviesId) {
    $.post("user/getUser",{},function (data) {
        var userId = data;
        $.post("movies/getItemCFRecommender",{moviesId:moviesId,userId:userId},function (data) {
            var divstr = '';
            for (var i = 0;i<data.length;i++){
                divstr += '<span><img src="image/'+data[i].pictureUrl+'.png" onclick="javascript:location.href=\'movie.html?moviesId='+data[i].moviesId+'\'">'+data[i].title+'</span>';
            }

            $("#footer-itemCFRecommender-pictures").html(divstr);
        });
    });

}