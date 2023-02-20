var brandData = {};

function getSalesReportUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/sales-report";
}

function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brands";
}

function resetForm() {
    var element = document.getElementById("sales-report-form");
    element.reset()
}

function getInventoryReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory/report";
}

function getBrandReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    console.log(baseUrl);
    return baseUrl + "/api/brands/report";
}

function printCSVUrl() {
var baseUrl = $("meta[name=baseUrl]").attr("content")
    console.log(baseUrl);
    return baseUrl + "/api/sales-report/report";
}

function getFilteredList(event) {

    var $form = $("#sales-report-form");
    var json = toJson($form);
    console.log(json);
    var url = getSalesReportUrl()+ "/filter";
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
         success: function (response) {
            printCSV()
        },
        error: handleAjaxError
     });

     return false;
}

function displaySalesList(data) {
    var $tbody = $('#Sales-report-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + parseFloat(e.revenue).toFixed(2) + '</td>'
		+ '</tr>';
        $tbody.append(row);
    }
}

function getSalesList() {
    var url = getSalesReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displaySalesList(data);
	   },
	   error: handleAjaxError
	});
}
function getBrandOption() {
        selectElement = document.querySelector('#inputBrand');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function displayCategoryOptions()
{
    var $elC = $("#inputCategory");

    $elC.empty();
    $elC.append(`<option value="all" selected >All</option>`);
    var a = getBrandOption();
    for(var i in brandData[a])
        {
            $elC.append($("<option></option>")
                .attr("value", brandData[a][i]).text(brandData[a][i]));

        }
}


function displayBrandOptions(data)
{
   console.log(data);
   for(var i in data)
      {
         var a = data[i].brand;
         var b = data[i].category;
         if(!brandData.hasOwnProperty(a))
               Object.assign(brandData, {[a]:[]});
         brandData[a].push(b);
      }
   console.log(brandData);
   var $elB = $("#inputBrand");
   $elB.empty();

   $elB.append(`<option value="all" selected >All</option>`);


   $.each(brandData, function(key,value) {
            $elB.append($("<option></option>")
               .attr("value", key).text(key));

            });

   displayCategoryOptions();

}
const prod = new Set();
function populate(data) {
    var $elC = $("#inputCategory");
        for(i in data) {
        var a = "all";
        var b = data[i].category;
        if(!brandData.hasOwnProperty(a))
            Object.assign(brandData, {[a]:[]});
        brandData[a].push(b);
        prod.add(data[i].category);
    }
        $elC.empty();
        $elC.append(`<option value="all" selected >All</option>`);

        for(const item of prod)
            {
                $elC.append($("<option></option>")
                    .attr("value", item).text(item));
            }
}


function initlists()
{
   var url = getBrandUrl();
   $.ajax({
      url: url,
      type: 'GET',
      success: function(data) {
            populate(data);
      },
      error: handleAjaxError
   });
}

function getBrandList()
{
   var url = getBrandUrl();
   $.ajax({
      url: url,
      type: 'GET',
      success: function(data) {
            displayBrandOptions(data);
      },
      error: handleAjaxError
   });
}

function printBrandCSV() {
    window.location.href = getBrandReportUrl();
}

function printInventoryCSV() {
    window.location.href = getInventoryReportUrl();
}

function printCSV() {
resetForm();
    window.location.href = printCSVUrl();

}

function validateDate(input) {
  var dateFormat = /^\d{4}-\d{2}-\d{2}$/;
  var today = new Date();
  var thirtyDaysAgo = new Date();
  thirtyDaysAgo.setDate(today.getDate() - 31);
  var inputDate = new Date(input.value);
     if (inputDate > today) {
    toastr.error("Input date cannot be after today's date.");
    input.value = "";
  } else {
    input.setCustomValidity("");
  }
}

function init() {
    $('#refresh-data').click(getSalesList);
    $('#apply-filter').click(getFilteredList);
    $('#inputBrand').on('change', displayCategoryOptions);
    $('#downloadBrandCSV').click(printBrandCSV);
    $('#downloadInventoryCSV').click(printInventoryCSV);

    var dateInput = document.getElementById("inputSD");
     var dateInput2 = document.getElementById("inputED");
    var today = new Date();
    var thirtyDaysAgo = new Date();
    thirtyDaysAgo.setDate(today.getDate() - 30);


    dateInput.setAttribute("max", today.toISOString().substring(0, 10));
    dateInput2.setAttribute("max", today.toISOString().substring(0, 10));
}

$(document).ready(init);
$(document).ready(getSalesList);
$(document).ready(getBrandList);
$(document).ready(initlists);