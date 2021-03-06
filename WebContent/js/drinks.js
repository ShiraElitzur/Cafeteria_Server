var drinks = [];
var index;
var drink;
var row;
$(document).ready(function () {

    $("#updateDrink").click(function () {
        var title = $('#title').val();
        var price = $('#price').val();
        updateDrink(drink.id, title, price);
        $("#updateDrink").hide();

    });

    $("#addDrink").click(function () {
        var title = $('#title').val();
        var price = $('#price').val();
        $('#title').val("");
        $('#price').val("");
        addDrink(title, price);
    });

    $("#updateDrink").hide();

});


function initTable(data) {
    drinks = [];
    var theUrl = server + "/rest/web/getDrinks";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        timeout: 5000,
        success: function (data, textStatus) {
            //            alert('request successful');
            $.each(data, function (index, element) {
                $('#drinksTable').append($('<tr id="' + element.id + '"><td>' + element.title + '</td>' + '<td> <button type="button" class="btn my-button" id="editDrink" >ערוך<span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteDrink"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));


                drinks.push({
                    "id": element.id,
                    "title": element.title,
                    "price": element.price
                });
            })

            $(document).on("click", "#drinksTable #deleteDrink", function (e) {
                index = $(this).closest('tr').index();
                drink = drinks[index];
                confirmDelete(drink);

            });

            $(document).on("click", "#drinksTable #editDrink", function (e) {
                index = $(this).closest('tr').index();
                drink = drinks[index];
                row = $(this).closest('tr');
                $("#addDrink").hide();
                $("#updateDrink").show();
                $('#price').val("" + drink.price);
                $('#title').val(drink.title);
            });


        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });

}

function addDrink(title, price) {
    var urlAddress = server + "/rest/web/addDrink";
    var drink = {
        id: 0,
        title: title,
        price: price
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(drink),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data === -1) {
                alert("משקה זה קיים כבר!")
            } else {
                var drink = {
                    id: data.id,
                    title: data.title,
                    price: data.price
                };

                $('#drinksTable').append($('<tr id="' + data.id + '"><td>' + data.title + '</td>' + '<td> <button type="button" class="btn my-button" id="editDrink" >ערוך<span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteDrink"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));


                drinks.push(drink);
            }
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}

function deleteDrink(drink) {
    var urlAddress = server + "/rest/web/deleteDrink";

    var drink = {
        id: drink.id,
        title: drink.title,
        price: drink.price
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(drink),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            var tableRowId = "#" + drinks[index].id;
            $(tableRowId).remove();
            drinks.splice(index, 1);

        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });
}

function confirmDelete(drink) {
    BootstrapDialog.confirm({
        title: ' שים לב !',
        message: 'האם אתה בטוח שברצונך למחוק פריט זה ?',
        type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
        closable: true, // <-- Default value is false
        draggable: true, // <-- Default value is false
        btnCancelLabel: 'לא', // <-- Default value is 'Cancel',
        btnOKLabel: 'כן', // <-- Default value is 'OK',
        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
        callback: function (result) {
            // result will be true if button was click, while it will be false if users close the dialog directly.
            if (result) { //no clicked
                deleteDrink(drink);
            }
        }
    });
}

function updateDrink(id, title, price) {
    var drink = {
        id: id,
        title: title,
        price: price
    };
    var urlAddress = server + "/rest/web/editDrink";

    $.ajax({
            type: "POST",
            url: urlAddress,
            data: JSON.stringify(drink),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data === -1) {
                    alert("משקה זה קיים כבר !")
                } else {
                    row.find('td:eq(0)').html(title);
                    drinks[index] = drink;
                }
                $("#addDrink").show();
                $('#title').val("");
                $('#price').val("");
            },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });



}
