//修改密码
function userAndPasswordText() {
    var div = '';
    div += '<input id="password" name="password" type="password" placeholder="  用户更改密码"><br>';
    div += '<button id="update-button" class="update-button" onclick="updatePassword()">更改</button>' ;

    $("#middle-text").html(div);


}

function updatePassword() {
    var password = $("#password").val();
    if(password == "") alert("不能为空");
    else{
        $.post("user/getUser",{},function (data) {
            var userId = data;
            $.post("user/updateUserPassword",{userId:userId,password:password},function () {
                var divs = '<p>密码更改成功<p>';
                $("#middle-text").html(divs);
            })
        });
    }
}

//我的评分和评论
// function myTagsAndRatings(){
//     $.post("user/getUser",{},function (data) {
//         var userId = data;
//         $.post("movies/movieDetailRatingAndTag",{moviesId:0,userId:userId},function (data) {
//             var table = '<table border="1"><tr><th>电影</th><th>评论</th><th>评分</th></tr>';
//             for(var i = 0;i<data.length;i++){
//                 if(data[i].tag == null) data[i].tag = '无';
//                 if(data[i].rating == 0) {
//                     table += '<tr><td>'+data[i].title+'</td><td>'+data[i].tag+'</td><td>无</td></tr>';
//                 }else {
//                     table += '<tr><td>'+data[i].title+'</td><td>'+data[i].tag+'</td><td>'+data[i].rating+'</td></tr>';
//                 }
//             }
//             table += '</table>';
//             $("#middle-text").html(table);
//         })
//     });
// }

function myTagsAndRatings(){

    $.post("user/getUser",{},function (data) {
        var userId = data;
        var div_table = '无评论表示用户没有对该电影写评论，评分为0表示用户没有对该用户打分，最低分为0.5！！！<table class="layui-hide" id="tagAndRatingTable" lay-filter="test"></table>';
        $("#middle-text").html(div_table);

        layui.use('table', function(){
            var table = layui.table;
            table.render({
                elem: '#tagAndRatingTable'
                ,url:'movies/movieDetailRatingAndTagTable?moviesId=0&userId='+userId
                // ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                ,defaultToolbar: []
                ,title: '用户数据表'
                ,cols: [[
                    {field:'title', title:'电影', width:120}
                    ,{field:'tag', title:'评论', width:80, sort: true}
                    ,{field:'rating', title:'评分', width:100,  sort: true}
                    ,{field:'date', title:'时间', width:120, sort: true}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
                ]]
                ,page: false
            });
        });
    });
}


function timeAdd0(m){return m<10?'0'+m:m }



