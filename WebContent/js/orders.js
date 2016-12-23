var orders = [];
var index;
var row;
var test;
var date;
function getOrdersByDate() {
	date = $('.date-picker').val();
	$('#ordersTable tbody').empty();
    orders.length = 0;
    var theUrl = server + "/rest/web/getOrdersByDate";
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: theUrl,
        data: {date: date},
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

                $('#ordersTable').append($('<tr id="' + order.id + '"><td>' + order.date + '</td>' + '<td>' + order.payment + " &#x20AA;" + '</td></tr>'));
                orders.push(order);

            })

        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });
}

function initOrders(data) {
	getOrdersByDate();

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
            	    legend: {textStyle:  {fontSize: 18,bold: false}}

            };
            chart.draw(data, options);
        }
    }
    
}

$(document).ready(function () {
	$('.date-picker').datepicker(
            {
                dateFormat: "mm/yy",
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                onClose: function(dateText, inst) {


                    function isDonePressed(){
                        return ($('#ui-datepicker-div').html().indexOf('ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all ui-state-hover') > -1);
                    }

                    if (isDonePressed()){
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(year, month, 1)).trigger('change');
                        
                         $('.date-picker').focusout()//Added to remove focus from datepicker input box on selecting date
                     	getOrdersByDate();

                    }
                },
                beforeShow : function(input, inst) {

                    inst.dpDiv.addClass('month_year_datepicker')

                    if ((datestr = $(this).val()).length > 0) {
                        year = datestr.substring(datestr.length-4, datestr.length);
                        month = datestr.substring(0, 2);
                        $(this).datepicker('option', 'defaultDate', new Date(year, month-1, 1));
                        $(this).datepicker('setDate', new Date(year, month-1, 1));
                        $(".ui-datepicker-calendar").hide();
                    }
                }
            });
	
	$('.date-picker').datepicker("setDate", new Date());
	date = $('.date-picker').val();
	initOrders();

	
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


