$(document).ready(function() {
	$("#sendemail").click(function() {
		sendEmail();
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