function getSalesUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/dailysales";
}
var last_run = null;
function displaySalesList(data) {

    last_run =data[0].lastRun;
    console.log(last_run);

    $("#last-run input[name=lastRun]").val(last_run);


    var $tbody = $('#Sales-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>'  + e.invoicedOrderCount + '</td>'
		+ '<td>' + e.invoicedItemsCount + '</td>'
		+ '<td>' + parseFloat(e.totalRevenue).toFixed(2) + '</td>'
		+ '</tr>';
        $tbody.append(row);
    }
}



function getSalesList() {
  var url = getSalesUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displaySalesList(data);
    },
    error: handleAjaxError,
  });
}

function resetForm() {
  var element = document.getElementById("sales-form");
  element.reset();
}

function getFilteredList(event) {
  var $form = $("#sales-form");
  var json = toJson($form);
  console.log(json);

  var url = getSalesUrl() + "/filter";
  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      resetForm();
      displaySalesList(response);
    },
    error: handleAjaxError,
  });

  return false;
}


function refreshData() {
  var url = getSalesUrl() + "/scheduler";
  $.ajax({
    url: url,
    type: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    success: function () {
      getSalesList();
    },
    error: handleAjaxError,
  });

  return false;
}

function validateDate(input) {
  var dateFormat = /^\d{4}-\d{2}-\d{2}$/;
  var today = new Date();
  var thirtyDaysAgo = new Date();
  thirtyDaysAgo.setDate(today.getDate() - 31);
  var inputDate = new Date(input.value);
     if (inputDate < thirtyDaysAgo || inputDate > today) {
    toastr.error("Date must be within the last 30 days to today.");
    input.value = "";
  } else {
    input.setCustomValidity("");
  }
}

function init() {
  $("#refresh-data").click(refreshData);
  $("#apply-filter").click(getFilteredList);
      var dateInput = document.getElementById("inputSD");
       var dateInput2 = document.getElementById("inputED");
      var today = new Date();
      var thirtyDaysAgo = new Date();
      thirtyDaysAgo.setDate(today.getDate() - 30);

      dateInput.setAttribute("min", thirtyDaysAgo.toISOString().substring(0, 10));
      dateInput.setAttribute("max", today.toISOString().substring(0, 10));
      dateInput2.setAttribute("min", thirtyDaysAgo.toISOString().substring(0, 10));
      dateInput2.setAttribute("max", today.toISOString().substring(0, 10));
}

$(document).ready(init);
$(document).ready(getSalesList);
$(document).ready(autoFillDate);
