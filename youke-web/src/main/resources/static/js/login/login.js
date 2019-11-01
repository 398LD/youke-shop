const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', function () {
    container.classList.add("right-panel-active");
});


signInButton.addEventListener('click', function () {
    container.classList.remove("right-panel-active");
});


function register(obj) {

    var publicKey_url = "http://127.0.0.1:8080/youkeshop/getPublicKey";

    var register_url = "http://127.0.0.1:8080/youkeshop/register";

    $.get(publicKey_url, function (data) {

        if (data.code == 200) {
            var publicKey = data.data.publicKey;

            var username = $("#rusername").val();

            var password = $("#rpassword").val();

            var email = $("#remail").val();

            var code = $("#rcode").val();

            //加密
            var encrypt = new JSEncrypt();
            //设置公钥
            encrypt.setPublicKey(publicKey);
            //加密后的密码
            password = encrypt.encrypt(password);

            //注册操作
            $.ajax({
                type: "post",
                url: register_url,
                async: true,
                data: {"username": username, "password": password, "email": email, "code": code},
                dataType: "json",
                xhrFields: {
                    withCredentials: true
                }, //与服务器配合使用标识允许携带cookie
                success: function (loginresult) {
                    if (loginresult.code == 200) {
                        //保存用户信息到本地
                        sessionStorage.setItem("username", loginresult.data.username);
                        window.location.href = "http://127.0.0.1:8080/youkeshop/index";
                    } else {
                        $.message({
                            message: loginresult.message,
                            type: 'error'
                        });
                    }

                },
                error: function (data) {
                    var a = JSON.stringify(data);
                    alert("error" + a);
                }
            });

        } else {
            alert("获取数据失败！！！")
        }

    });

}


function login() {

    $.get("http://127.0.0.1:8080/youkeshop/getPublicKey", function (data) {

        if (data.code == 200) {
            var publicKey = data.data.publicKey;

            var username = $("#username").val();

            //获取密码进行加密
            var password = $("#password").val();

            var code = $("#code").val()

            //加密
            var encrypt = new JSEncrypt();
            //设置公钥
            encrypt.setPublicKey(publicKey);
            //加密后的密码
            password = encrypt.encrypt(password);

            $.ajax({
                type: "post",
                url: "http://127.0.0.1:8080/youkeshop/login",
                data: {
                    "username": username,
                    "password": password,
                    "code": code
                },
                dataType: "json",
                xhrFields: {withCredentials: true},     //与服务器配合使用标识允许携带cookie
                success: function (loginresult) {
                    if (loginresult.code == 200) {
                        //保存用户信息到本地
                        sessionStorage.setItem("username", loginresult.data.username);
                        window.location.href = "http://127.0.0.1:8080/youkeshop/index";
                    } else {
                        $.message({
                            message: loginresult.message,
                            type: 'error'
                        });
                    }

                },
                error: function (data) {
                    var a = JSON.stringify(data);
                    alert("error" + a);
                }
            });

        }


    })


}
