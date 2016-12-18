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
        var items = [];
        $.each(orders, function (index, element) {
                if (element.items.length > 0) {
                    for (var i = 0; i < element.items.length; i++) {
                        var item = {
                            "id": element.items[i].parentItem.id,
                            "title": element.items[i].parentItem.title,
                            "qty": 0
                        };

                        alert("item " + item.title);
                        for (var p = 0; p < items.length; p++) {
                            if (items[p].id == item.id) {
                                found = true;
                                items[p].qty++;
                                break;
                            }
                        }
                        if (found === false) {
                            item.qty++;
                            items.push(item);
                        }
                    }
                }

            })
            // Add rows + data at the same time
            // -----------------------------
        test = items;
        var data = new google.visualization.DataTable();

        // Declare columns
        data.addColumn('string', 'item Name');
        data.addColumn('number', 'buy');
        data.addRows(items.length);
        for (var i = 0; i < items.length; i++) {
            for (var j = 0; j < 2; j++) {
                if (j === 0) {
                    data.setCell(i, j, items[i].title);
                } else {
                    data.setCell(i, j, items[i].qty);
                }
            }
        }


        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, {
            width: 400,
            height: 240
        });
    }

}
