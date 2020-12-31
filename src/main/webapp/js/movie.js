//获取电影详情页
function getMovieDetail(moviesId){
    $.post("movies/movieDetail",{moviesId:moviesId},function (data) {
        var div = '<img src="image/'+data.moviesId+'.png">';
        $("#middle-de-img").html(div);
        $("#middle-de-p-title").html("片名："+data.title);
        $("#middle-de-p-genres").html("分类："+data.genres);
        var start_full_num =  Math.floor(data.avg);
        var hidden_num = data.avg - start_full_num;
        var start_hidden = (start_full_num +1)*2;
        var start_hidden_width = 34*hidden_num;

        // $("#middle-de-img-"+start_hidden).attr('width',start_hidden_width+'px');
        document.getElementById("middle-de-img-"+start_hidden).style.width = start_hidden_width+'px';
        start_hidden = start_hidden +2;
        while(start_hidden<=10){
            document.getElementById("middle-de-img-"+start_hidden).style.width = '0px';
            start_hidden = start_hidden +2;
        }
        $("#middle-de-p-avg-p").html(data.avg+"星");

        //获取电影的评论和评分
        $.post("movies/movieDetailRatingAndTag",{moviesId:moviesId,userId:0},function (data) {
            var divs = '';
            for(var i = 0;i<data.length;i++){
                if(data[i].tag == null) data[i].tag = '无';
                divs += '<div class="middle-tag-rating-div"><span class="middle-tag-rating-ur">';
                divs += '<div class="middle-tag-rating-ur-u">用户：'+data[i].userId+'</div>';
                divs += '评分：<div id="middle-tag-rating-ur-r-'+i+'" class="middle-tag-rating-ur-r-'+i+'"></div>';
                divs += '<div class="middle-tag-rating-ur-d">时间：'+data[i].date+'</div></span>'
                divs += '<span class="middle-tag-rating-t">评论：'+data[i].tag+'</span> </div>';
            }
            $("#middle-tag-rating").html(divs);

            layui.use(['rate'], function(){
                var rate = layui.rate;
                for(var i = 0;i<data.length;i++){
                    if(data[i].rating == 0){
                        $("#middle-tag-rating-ur-r-"+i).html("无");
                    }else{
                        rate.render({
                            elem: '#middle-tag-rating-ur-r-'+i
                            ,value: data[i].rating
                            ,half: true
                            ,text: true
                            ,readonly: true
                        })
                    }
                }
            });

            if(data.length >3){
                var height_now = 3*92;
                document.getElementById("middle-tag-rating").style.height = height_now+'px';

                var divbs = '';
                divbs += '<button id="middle-tag-rating-b-1" class="middle-tag-rating-b-1" onclick="aHideOrShow('+data.length+')">展开</button>';
                divbs += '<button id="middle-tag-rating-b-2" class="middle-tag-rating-b-2" onclick="aHideOrShow('+3+')">折叠</button>';
                $("#middle-tag-rating-b").html(divbs);
                $("#middle-tag-rating-b-2").hide();
            }else{
                $("#middle-tag-rating-b").hide();
            }

        })
    })

}


//a标签 折叠 显示
function aHideOrShow(height_len){
    var height = height_len * 92;
    document.getElementById("middle-tag-rating").style.height = height+'px';
    if(height_len == 3){//把展开按钮显示出来
        $("#middle-tag-rating-b-2").hide();
        $("#middle-tag-rating-b-1").show();
    }else {//把折叠按钮显示出来
        $("#middle-tag-rating-b-1").hide();
        $("#middle-tag-rating-b-2").show();
    }
}

//用户写评论和评分
function writeTagAndRating(){
    $.post("user/getUser",{},function (data) {
        var userId = data;
        if(userId != null){
            alert("评分为0表示没有打分,最低分为0.5!!!!!")
            var divstr = '';
            //打分框
            divstr += '<div class="middle-de-p-writeTagAndRating-rdiv">打分：<div id="middle-de-p-writeTagAndRating-rating" class="middle-de-p-writeTagAndRating-rating"></div></div>';
            //评论框
            divstr += '<div class="middle-de-p-writeTagAndRating-tdiv">评论：<div id="middle-de-p-writeTagAndRating-tag" class="middle-de-p-writeTagAndRating-tag"><input class="middle-de-p-writeTagAndRating-tag-text" type="text" placeholder="    加标点符号在内，不能超过20字(不能输入特殊符号)！"></div></div>';
            //提交按钮
            divstr += '<div class="middle-de-p-writeTagAndRating-bdiv"><button class="middle-de-p-writeTagAndRating-submit" onclick="getWriteTagAndRating()">提交</button></button></div>';
            $("#middle-de-p-writeTagAndRating").html(divstr);

            layui.use(['rate'], function(){
                var rate = layui.rate;
                rate.render({
                    elem: '#middle-de-p-writeTagAndRating-rating'
                    ,value: 0
                    ,half: true
                    ,text: true
                    ,choose: function(value){
                        $.post("movies/saveUserIdRating",{rating:value},function () {

                        })
                    }
                })
            });
        }else {
            alert("你还没有登陆，请先登录!!");
        }
    });
}

function getWriteTagAndRating(){
    //获取电影id
    var moviesId = getParameter("moviesId");
    //获取评分值 在servlet请求中的获取用户的评分
    //获取输入的评论
    var tag = $(".middle-de-p-writeTagAndRating-tag-text").val();
    $.post("movies/saveUserIdRatingAndTag",{tag:tag,moviesId:moviesId},function () {
        alert("添加成功");
        //重新加载当前页面
        document.location.reload();

        // var divb = '<button id="middle-de-p-writeTagAndRating-button" class="middle-de-p-writeTagAndRating-button" onclick="writeTagAndRating()">打分评论</button>';
        // $("#middle-de-p-writeTagAndRating").html(divb);
    })

}
