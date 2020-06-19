var zdrysc = {
    base: '',
    user: '',
    qq: ''
};
//个人信息设置
var zdryscuserdata = JSON.parse(sessionStorage.getItem("zdryscuser"));
if (zdryscuserdata) {
    $(".username").html(zdryscuserdata.name);
if(zdryscuserdata.name=="admin"){
    $(".left-nav").show();
}else {

   /* $(".left-nav").hide();*/
    $(".left-nav").show();
}



}else{
    $.ajax({
        url: "/logout",
        type: "get",
        xhrFields: {
            withCredentials: true
        },
        data: {},
        success: function (res) {
            if (res.code !=0) {
                return
            }
            $('.layui-tab-title li[lay-id]').find('.layui-tab-close').click();
            window.location = "./login.html";

        }
    });
    console.log("个人信息失效，请重新登录！！！")
}


//退出登录
$(".logout").click(function () {
    console.log("进到这里灭有！！！！")
    zdrysc.logoutfun();
});

//推出登录
zdrysc.logoutfun = function () {
    $.ajax({
        url: "/logout",
        type: "get",
        xhrFields: {
            withCredentials: true
        },
        data: {},
        success: function (res) {
            if (res.code !=0) {
                return
            }
            sessionStorage.removeItem("zdryscuser");
            $('.layui-tab-title li[lay-id]').find('.layui-tab-close').click();
            window.location = "./login.html";

        }
    });
}

$.ajax({
    url : "api/findUser",
    type : "post",
    xhrFields : {
        withCredentials : true
    },
    data : JSON.stringify({
        username : "admin"
    }),
    contentType : "application/json",
    success : function (res) {
        // console.log(res)

    }
});