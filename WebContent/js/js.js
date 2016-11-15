//var server="http://time2eat.eu-gb.mybluemix.net";

//when working locally:
var server = "http://localhost:8080/CafeteriaServer"; 
//when working on the remote server:
//var server ="";

$(document).ready(function () {

    $("#navbar").load("navbar.html");

    $(".search").keyup(function () {
        var searchTerm = $(".search").val();
        var listItem = $('.results tbody').children('tr');
        var searchSplit = searchTerm.replace(/ /g, "'):containsi('")

        $.extend($.expr[':'], {
            'containsi': function (elem, i, match, array) {
                return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

        $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'false');
        });

        $(".results tbody tr:containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'true');

        });

        var count = $('.results tbody tr[visible="true"]').length;
        $('.counter').text(count + ' item');

        if (count == '0') {
            $('.no-result').show();
        } else {
            $('.no-result').hide();
        }
    });

});

function areYouSure(callback) {
    var res;
    BootstrapDialog.confirm({
        title: 'Pay Attention!',
        message: 'Are you sure you want to delete this item ?',
        type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
        closable: true, // <-- Default value is false
        draggable: true, // <-- Default value is false
        btnCancelLabel: 'No', // <-- Default value is 'Cancel',
        btnOKLabel: 'Yes', // <-- Default value is 'OK',
        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
        callback: function (result) {
            // result will be true if button was click, while it will be false if users close the dialog directly.
            if (result) {
                res = true;
            } else {
                res = false;
            }
        }
    });
    if (res === true) {
        callback(true);
    } else {
        callback(false);
    }
}

function logout() {
    //        localStorage.removeItem('customer');
    var emailCookieName = 'cafeteria-admin-email';
    var passwordCookieName = 'cafeteria-admin-password';

    document.cookie = emailCookieName + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;"
    document.cookie = passwordCookieName + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;"


    window.location = "./index.html";
}
