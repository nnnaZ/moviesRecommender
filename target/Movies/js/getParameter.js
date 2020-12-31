// 获取url后面的参数
function getParameter(para) {
    var reg = new RegExp("(^|&)" + para + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}