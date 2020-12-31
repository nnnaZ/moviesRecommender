//用户注册
function userRegister(){
    $.post("user/register",{},function (data) {
        if(data.flag == true){
            location.href = "index.html";
        }else {
            $("#errorMsg").html(data.message);
        }
    });
    //不让表单提交
    return false;
}