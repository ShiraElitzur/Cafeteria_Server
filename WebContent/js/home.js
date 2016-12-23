$(document).ready(function() {

    $('#timepicker-start').timepicker({
        maxHours: 24,
        showMeridian: false,
        defaultTime: 'current',
        minuteStep: 1,
        snapToStep: false,
        defaultTime: startHours
    });
    
    $('#timepicker-end').timepicker({
        maxHours: 24,
        showMeridian: false,
        defaultTime: 'current',
        minuteStep: 1,
        snapToStep: false,
        defaultTime: endHours
    });
	
	$("#sendemail").click(function() {
		sendEmail();
	});
	
    $("#cafeteriaName").val(cafeteriaName);
    $("#cafeteriaName").attr("disabled",true);
    $('#timepicker-end').attr("disabled",true);
    $('#timepicker-start').attr("disabled",true);
    
	$("#cafeteriaDetailsBtn").click(function() {
		if ($("#cafeteriaDetailsBtn").text() === "החלף פרטים"){
			$("#cafeteriaName").attr("disabled",false);
		    $('#timepicker-end').attr("disabled",false);
		    $('#timepicker-start').attr("disabled",false);
		    $("#cafeteriaDetailsBtn").text("עדכן פרטים");
		} else{
			$("#cafeteriaName").attr("disabled",true);
		    $('#timepicker-end').attr("disabled",true);
		    $('#timepicker-start').attr("disabled",true);
		    $("#cafeteriaDetailsBtn").text("החלף פרטים");
		    updateCafeteriaDetails();
		}
	    
	    
	});

});

function sendEmail() {
	var email = $('#email').val();
	var message = $('#message').val();
	var webMessage = {
		email : email,
		message : message
	};
	var urlAddress = server + "/rest/email/sendMessage";

	$.ajax({
				type : "POST",
				url : urlAddress,
				data : JSON.stringify(webMessage),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if (data === 0) { // email sent
						$('#response').html("<div class='alert alert-success'>");
						$('#response > .alert-success')
						.html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
						.append("</button>");
						$('#response > .alert-success').append("<strong>ההודעה נשלחה בהצלחה </strong>");
						$('#response > .alert-success').append('</div>');
						$("#contactForm")[0].reset();

					} else { // email wasnt sent
						$('#response').html("<div class='alert alert-danger'>");
						$('#response > .alert-danger')
						.html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
						.append("</button>");
						$('#response > .alert-danger').append("<strong>מצטערים לא הצלחנו לשלוח את ההודעה כרגע");
						$('#response > .alert-danger').append('</div>');
						//clear all fields
						$("#contactForm")[0].reset();
					}
				},
				failure : function(errMsg) {
					alert(errMsg);
				}
			});
}

function updateCafeteriaDetails(){
    var emailName = 'cafeteria-admin-email' +"=";
    var email;
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(emailName) === 0) {
        	email = c.substring(emailName.length, c.length);
        	break;
        }
    }
	
    var name = $('#cafeteriaName').val();
    var timeStart = $('#timepicker-start').val();
    var timeEnd = $('#timepicker-end').val();

	
	var urlAddress = server + "/rest/web/updateCafeteria";
	$.post(urlAddress, {
        email: email,
        cafeteriaName: name,
        timeStart: timeStart,
        timeEnd: timeEnd
    }, function (data, status) {
        if (data === null) {
            alert("null");
        } else {
        	replaceCookies(data);
        }
    });

}

function replaceCookies(data){
	//remove cookies
    var cafeteriaName = 'cafeteria-name';
    var startingHours = 'cafeteria-start-hours';
    var endingHours = 'cafeteria-end-hours';
    
    document.cookie = cafeteriaName + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;"
    document.cookie = startingHours + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;"
    document.cookie = endingHours + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;"
    
    // add cookies
    var d = new Date();
    d.setTime(d.getTime() + (365 * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    
    document.cookie = cafeteriaName + "=" + data.cafeteriaName + ";" + expires + ";path=/";
    var date = data.openingHoursStart;
    var dateString = date.hourOfDay + ":" + date.minute;

    $('#timepicker-start').timepicker({
        maxHours: 24,
        showMeridian: false,
        defaultTime: 'current',
        minuteStep: 1,
        snapToStep: false,
        defaultTime: dateString
    });
    document.cookie = startingHours + "=" + dateString + ";" + expires + ";path=/";
    date = data.openingHoursEnd;
    dateString = date.hourOfDay + ":" + date.minute;
    
    document.cookie = endingHours + "=" + dateString + ";" + expires + ";path=/";
   
    $('#timepicker-end').timepicker({
        maxHours: 24,
        showMeridian: false,
        defaultTime: 'current',
        minuteStep: 1,
        snapToStep: false,
        defaultTime: dateString
    });
    
    $('#cafeteriaName').val(data.cafeteriaName);
}