//var server="http://time2eat.eu-gb.mybluemix.net";

//when working locally:
//var server = "http://localhost:8080/CafeteriaServer"; 
//var server = "http://localhost:9080/CafeteriaServer"; 
//when working on the remote server:
//var server ="";
//var iconAddress = "http://time2eat.eu-gb.mybluemix.net";
//var iconAddress = "http://localhost:8080/CafeteriaServer";
var iconAddress;
var server;
var cafeteriaName;
var startHours;
var endHours;
                var serverCookie = 'cafeteria-server-ip' + "=";
                var startHoursCookie = 'cafeteria-start-hours' + "=";
                var endHoursCookie = 'cafeteria-end-hours' + "=";
                var cafeteriaNameCookie = 'cafeteria-name' + "=";

                var ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) === ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(serverCookie) === 0) {
                    	server = "http://"+ c.substring(serverCookie.length, c.length);
                    	iconAddress = "http://"+ c.substring(serverCookie.length, c.length);
                    	break;
                    }
                }
                
                ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) === ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(startHoursCookie) === 0) {
                    	startHours = c.substring(startHoursCookie.length, c.length);
                    	break;
                    }
                } 

                ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) === ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(endHoursCookie) === 0) {
                    	endHours = c.substring(endHoursCookie.length, c.length);
                    	break;
                    }
                } 
                
                ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) === ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(cafeteriaNameCookie) === 0) {
                    	cafeteriaName = c.substring(cafeteriaNameCookie.length, c.length);
                    	break;
                    }
                }
                
