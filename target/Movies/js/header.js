//获取用户session中的userId显示在页面用户出
function userSession(){
    $.post("user/getUser",{},function (data) {
        var userId = data;
        var ul_nav = '<li>';
        // alert(userId+"type:"+typeof(userId));
        if(userId != null){
            ul_nav += '<a href="userInformation.html">欢迎用户'+userId+'</a>';
            ul_nav += '<a href="javascript:userExit()">退出</a></li>'

            $("#nav-l").html(ul_nav);
        }
    });
}
//用户退出
function userExit(){
    $.post("user/userExit",{},function () {
        window.location.href = "index.html";
    });
}
