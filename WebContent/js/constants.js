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
                var name = 'cafeteria-server-ip' + "=";
                var ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(name) == 0) {
                    	server = "http://"+ c.substring(name.length, c.length);
                    	iconAddress = "http://"+ c.substring(name.length, c.length);
                    	break;
                    }
                }