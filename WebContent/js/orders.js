var orders = [];
var index;
var row;
var test;
$(document).ready(function () {
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
            $(this).addClass("hide");
        });

        $(".results tbody tr:containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'true');
            $(this).removeClass("hide");
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


function initOrders(data) {
    orders = [];
    var order;
    var theUrl = server + "/rest/web/getOrders";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        async: false,
        timeout: 5000,
        success: function (data, textStatus) {
            test = data;
            $.each(data, function (index, element) {
                order = {
                    "id": element.id,
                    "customer": element.customer,
                    "meals": element.meals,
                    "items": element.items,
                    "date": element.date,
                    "payment": element.payment,
                    "isDelivered": element.isDelivered,
                    "isReady": element.isReady,
                    "comment": element.comment,
                    "pickupTime": element.pickupTime
                };

                $('#ordersTable').append($('<tr id="' + order.id + '"><td>' + order.date + '</td>' + '<td>' + order.payment + "&#x20AA;" + '</td></tr>'));
                orders.push(order);

            })

        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });

    //    var chart = new CanvasJS.Chart("chartContainer", {
    //        backgroundColor: "#eee",
    //        title: {
    //            text: "המוצרים המוזמנים ביותר"
    //        },
    //        animationEnabled: true,
    //        theme: "theme2",
    //        data: [
    //            {
    //                type: "doughnut",
    //                indexLabelFontFamily: "Garamond",
    //                indexLabelFontSize: 20,
    //                startAngle: 0,
    //                indexLabelFontColor: "dimgrey",
    //                indexLabelLineColor: "darkgrey",
    //                toolTipContent: "{y} %",
    //
    //                dataPoints: [
    //                    {
    //                        y: 51.04,
    //                        indexLabel: "שניצל בצלחת {y}%"
    //                    },
    //                    {
    //                        y: 40.83,
    //                        indexLabel: "קוקה קולה {y}%"
    //                    },
    //                    {
    //                        y: 3.20,
    //                        indexLabel: "קפה ומאפה {y}%"
    //                    },
    //                    {
    //                        y: 1.11,
    //                        indexLabel: "המבורגר בלחמניה {y}%"
    //                    },
    //                    {
    //                        y: 2.29,
    //                        indexLabel: "במבה {y}%"
    //                    },
    //                    {
    //                        y: 1.53,
    //                        indexLabel: "קפה שחור {y}%"
    //                    }
    //
    //					]
    //				}
    //				]
    //    });
    //
    //    chart.render();

    if (orders.length > 0){
    	 // Load the Visualization API and the corechart package.
        google.charts.load('current', {
            'packages': ['corechart']
        });

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {
            var found = false;
            var foundMeal = false;
            var items = [];
            var meals = [];
            var favorites = [];
            $.each(orders, function (index, element) {
                    if (element.items.length > 0) {
                        for (var i = 0; i < element.items.length; i++) {
                            var item = {
                                "id": element.items[i].parentItem.id,
                                "title": element.items[i].parentItem.title,
                                "qty": 0
                            };

                            for (var p = 0; p < items.length; p++) {
                                if (items[p].id === item.id) {
                                    found = true;
                                    items[p].qty++;
                                    favorites[p].qty++;
                                    break;
                                }
                            }
                            if (found === false) {
                                item.qty++;
                                items.push(item);
                                favorites.push(item);
                            }
                        }
                    }
                    if (element.meals.length > 0) {
                        for (var m = 0; m < element.meals.length; m++) {
                        	var meal = {
                                "id": element.meals[m].id,
                                "title": element.meals[m].parentMeal.title,
                                "qty": 0
                            };

                            for (var r = 0; r < meals.length; r++) {
                                if (meals[r].id === meal.id) {
                                	foundMeal = true;
                                    meals[r].qty++;
                                    favorites[r].qty++;
                                    break;
                                }
                            }
                            if (foundMeal === false) {
                                meal.qty++;
                                meals.push(meal);
                                favorites.push(meal);
                            }
                        }
                    }

                })

                // Add rows + data at the same time
                // -----------------------------
            favorites.sort(function(a, b){return b.qty-a.qty});
            var data = new google.visualization.DataTable();

            // Declare columns
            data.addColumn('string', 'item Name');
            data.addColumn('number', 'buy');
            data.addRows(favorites.length);
            var max = 6;
            for (var i = 0; i < max; i++) {
                for (var j = 0; j < 2; j++) {
                    if (j === 0) {
                        data.setCell(i, j, favorites[i].title);
                    } else {
                        data.setCell(i, j, favorites[i].qty);
                    }
                }
            }
            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            var options = {'title':'המוצרים הנמכרים ביותר',
            	    'backgroundColor': 'transparent',   
            		'is3D':true,
            	    titleTextStyle: {
            	        fontSize: 30, // 12, 18 whatever you want (don't specify px)
            	        bold: true,    // true or false
            	    },
            	    tooltip: {textStyle:  {fontSize: 18,bold: false}},
            	    legend: {textStyle:  {fontSize: 20,bold: false}}

            };
            chart.draw(data, options);
        }
    }
   

}
