var server="/CafeteriaServer";
//var server="http://cafeteriaserver.eu-gb.mybluemix.net";

$(document).ready(function () {
    $('.message a').click(function () {
        $('form').animate({
            height: "toggle",
            opacity: "toggle"
        }, "slow");
    });
    
    $('#passwordResult').hide();

    $('#loginBtn').click(function () {
        var email = $('#email').val();
        var password = $('#password').val();
        var urlAddress = server+ "/rest/web/adminLogin";

        $.post(urlAddress, {
            email: email,
            password: password
        }, function (data, status) {
            //            alert("Data: " + data + "\nStatus: " + status);
            if (data === null || data == undefined) {
                alert("Wrong details!");
            } else {

                var d = new Date();
                var emailName = 'cafeteria-admin-email';
                var passwordName = 'cafeteria-admin-password';

                d.setTime(d.getTime() + (365 * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toUTCString();
                document.cookie = emailName + "=" + data.email + ";" + expires + ";path=/";

                document.cookie = passwordName + "=" + data.password + ";" + expires + ";path=/";

                window.location = "./home.html";
            }
        });
    });

    $('#forgotPasswordBtn').click(function () {
        var email = $('#forgot-email').val();
        var urlAddress = server + "/rest/web/getAdminPassword";

        $.post(urlAddress, {
            email: email
        }, function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            if (data === null || data == undefined) {
                alert("Wrong email!");
            } else {
                $('#passwordResult').append("Your password is: " + data.password)
                $('#passwordResult').show();

//                alert("Your password is: " + data.password);
//                $('form').animate({
//                    height: "toggle",
//                    opacity: "toggle"
//                }, "slow");
            }
        });
    });
});
