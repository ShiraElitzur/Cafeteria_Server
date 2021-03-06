var mains = [];
var index;
var main;
var row;
$(document).ready(function () {

    $("#updateMain").click(function () {
        var title = $('#title').val();
        updateMain(main.id, title);
        $("#updateMain").hide();

    });

    $("#addMain").click(function () {
        var title = $('#title').val();
        $('#title').val("");
        addMain(title);
    });

    $("#updateMain").hide();

});

function initTable(data) {
    mains = [];
    var theUrl = server + "/rest/web/getMains";
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: theUrl,
        timeout: 5000,
        success: function (data, textStatus) {
            //            alert('request successful');
            $.each(data, function (index, element) {
                $('#mainsTable').append($('<tr id="' + element.id + '"><td>' + element.title + '</td>' + '<td> <button type="button" class="btn my-button" id="editMain" >ערוך<span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteMain"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));


                mains.push({
                    "id": element.id,
                    "title": element.title
                });
            })

            $(document).on("click", "#mainsTable #deleteMain", function (e) {
                index = $(this).closest('tr').index();
                main = mains[index];
                confirmDelete(main);

            });

            $(document).on("click", "#mainsTable #editMain", function (e) {
                index = $(this).closest('tr').index();
                main = mains[index];
                row = $(this).closest('tr');
                $("#addMain").hide();
                $("#updateMain").show();
                $('#title').val("" + main.title);
            });


        },
        error: function (xhr, textStatus, errorThrown) {
            alert('request failed');
        }
    });

}

function addMain(title) {
    var urlAddress = server + "/rest/web/addMain";
    var main = {
        id: 0,
        title: title
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(main),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data === -1) {
                alert("עיקרית זו קיימת כבר !")
            } else {
                main = {
                    id: data.id,
                    title: data.title
                };

                $('#mainsTable').append($('<tr id="' + data.id + '"><td>' + data.title + '</td>' + '<td> <button type="button" class="btn my-button" id="editDrink" >ערוך<span' + ' class="glyphicon glyphicon-pencil"></span></button></td> <td><button type="button" class="btn my-button" id="deleteDrink"' + '>הסר<span class="glyphicon glyphicon-remove"></span></button></td></tr>'));

                mains.push(main);
            }


        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

}

function deleteMain(main) {
    var urlAddress = server + "/rest/web/deleteMain";

    var main = {
        id: main.id,
        title: main.title
    };

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(main),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            var tableRowId = "#" + mains[index].id;
            $(tableRowId).remove();
            mains.splice(index, 1);

        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });
}

function confirmDelete(main) {
    BootstrapDialog.confirm({
        title: ' שים לב !',
        message: ' האם אתה בטוח שברצונך למחוק פריט זה? \n כל הארוחות עם העיקרית הזו ימחקו',
        type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
        closable: true, // <-- Default value is false
        draggable: true, // <-- Default value is false
        btnCancelLabel: 'לא', // <-- Default value is 'Cancel',
        btnOKLabel: 'כן', // <-- Default value is 'OK',
        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
        callback: function (result) {
            // result will be true if button was click, while it will be false if users close the dialog directly.
            if (result) { //no clicked
                deleteMain(main);
            }
        }
    });
}

function updateMain(id, title) {
    var main = {
        id: id,
        title: title
    };
    var urlAddress = server + "/rest/web/editMain";

    $.ajax({
        type: "POST",
        url: urlAddress,
        data: JSON.stringify(main),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data === -1) {
                alert("עיקרית זו קיימת כבר !")
            } else {
                row.find('td:eq(0)').html(title);
                mains[index] = main;
            }
            $("#addMain").show();
            $('#title').val("");
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });



}
