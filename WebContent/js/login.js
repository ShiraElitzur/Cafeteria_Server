var date;
var dateString;
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
        var mainServer = "http://time2eat.eu-gb.mybluemix.net";
        var local = "CafeteriaServer";
//        var urlAddress ="http://localhost:8080/CafeteriaServer/rest/web/adminLogin";
        var urlAddress ="/rest/web/adminLogin";

        $.post(urlAddress, {
            email: email,
            password: password
        }, function (data, status) {
            //            alert("Data: " + data + "\nStatus: " + status);
            if (data === null || data == undefined) {
                alert("פרטים שגויים!");
            } else {

                var d = new Date();
                var emailName = 'cafeteria-admin-email';
                var passwordName = 'cafeteria-admin-password';
                var serverIp = 'cafeteria-server-ip';
                var cafeteriaName = 'cafeteria-name';
                var startingHours = 'cafeteria-start-hours';
                var endingHours = 'cafeteria-end-hours';

                d.setTime(d.getTime() + (365 * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toUTCString();
                document.cookie = emailName + "=" + data.adminEmail + ";" + expires + ";path=/";

                document.cookie = passwordName + "=" + data.adminPassword + ";" + expires + ";path=/";

                document.cookie = serverIp + "=" + data.serverIp + ";" + expires + ";path=/";                
               
                document.cookie = cafeteriaName + "=" + data.cafeteriaName + ";" + expires + ";path=/";
                date = data.openingHoursStart;
                dateString = date.hourOfDay + ":" + date.minute;
                document.cookie = startingHours + "=" + dateString + ";" + expires + ";path=/";
                date = data.openingHoursEnd;
                dateString = date.hourOfDay + ":" + date.minute;
                document.cookie = endingHours + "=" + dateString + ";" + expires + ";path=/";

                window.location = "./home.html";
            }
        });
    });

    $('#forgotPasswordBtn').click(function () {
        var email = $('#forgot-email').val();
        var mainServer = "http://time2eat.eu-gb.mybluemix.net";
        var urlAddress = "/rest/web/getAdminPassword";

        $.post(urlAddress, {
            email: email
        }, function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            if (data === null || data == undefined) {
                alert("Wrong email!");
            } else {
                $('#passwordResult').append("הסיסמה שלך היא: " + data.password)
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

function sendEmail() {
	var email = $('#forgot-email').val();
	var cafeteria = {
		id : 0,
		cafeteriaName: "",
		serverIp: "",
		adminEmail: email,
		adminPassword: "",
		openingHoursStart: "",
		openingHoursEnd: ""
	};
	var urlAddress = server + "/rest/email/adminForgotPassword";

	$.ajax({
				type : "POST",
				url : urlAddress,
				data : JSON.stringify(cafeteria),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if (data === 0) { // email sent
		                $('#passwordResult').append("<strong>דואר אלקטרוני נשלח עם הסיסמא </strong>");
		                $('#passwordResult').show();

					} else { // email wasnt sent
						alert("<strong>פרטים שגויים, לא נמצא משתמש עם הדואר האלקטרוני שהוכנס");
					}
				},
				failure : function(errMsg) {
					alert(errMsg);
				}
			});
}
