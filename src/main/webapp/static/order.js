var completeOrder = []
let processedItems = {};


function getOrderItemUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orders";
}

function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orders";
}

function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/products";
}

function getInventoryUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/inventory";
}
function getInvoiceUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    console.log(baseUrl);
    return baseUrl + "/api/orders/invoice";
}

function resetForm() {
    var element = document.getElementById("order-item-form");
    element.reset()
}



function deleteOrderItem(id) {
    let keys = Object.keys(processedItems);
    let barCode = keys[id];
    delete processedItems[barCode];
    displayOrderItemList();
}

function displayOrderItemList(data) {
    completeOrder=Object.values(processedItems);
    var $tbody = $('#order-item-table').find('tbody');
    $tbody.empty();
    for (var i in completeOrder) {
        var e = completeOrder[i];
        var buttonHtml = '<button type="button" class="btn btn-secondary" onclick="deleteOrderItem(' + i + ')" class="btn">Delete</button>'
        buttonHtml +=' <button type="button" class="btn btn-secondary" onclick="editOrderItem(' + i + ')" class="btn"> Edit </button>'
        var row = '<tr>' +
            '<td>' + e.barCode + '</td>' +
            '<td>' + e.quantity + '</td>' +
            '<td>' + parseFloat(e.sellingPrice).toFixed(2) + '</td>' +
            '<td>' + buttonHtml + '</td>' +
            '</tr>';

        $tbody.append(row);
    }

}

function checkInventory(event){
   var $form = $("#order-item-form");
   	  if(!validateForm($form)){
             return;
       }

   var json = JSON.parse(toJson($form));
   if(json.barCode=="" || json.sellingPrice=="" || json.quantity==""){
        toastr.error("All fields are mandatory!");
        return false;
   }
   if(json.quantity<1){
           toastr.error("Quantity should be greater than 0.");
           return false;
      }
   if(json.sellingPrice<1){
              toastr.error("Selling Price should be greater than 0.");
              return false;
         }
   if(parseFloat(json.quantity)-parseInt(json.quantity)>0){
                 toastr.error("Quantity cannot be in fractions.");
                 return false;
            }
   var url = getInventoryUrl() + "/barcode/"+json.barCode;
    let prevQuantity=0;
    if(processedItems[json.barCode]){
     prevQuantity= parseInt(processedItems[json.barCode].quantity);
    }
$.ajax({
        url: url,
        type: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            if(response.quantity-prevQuantity<json.quantity){
            console.log(response.quantity-prevQuantity);
            toastr.error( (response.quantity-prevQuantity).toString()+" units left of this product.");
             return false;
            }
            checkMrp(json);
        },
        error: function (error) {
            toastr.error(error.responseJSON.message);
        }
    });
}

function checkMrp(json){
     var url = getProductUrl() + "/barcode/"+json.barCode;

    $.ajax({
            url: url,
            type: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            success: function (response) {
                if(response.mrp<json.sellingPrice){
                toastr.error("Selling Price (Rs. "+json.sellingPrice+") cannot be greater than MRP (Rs. "+response.mrp+")." );
                return false;
                }
                addOrderItem(json);
            },
            error: function (error) {
                toastr.error(error.responseJSON.message);
            }
        });
}

function addOrderItem(json) {

    if (processedItems[json.barCode]) {
        if (processedItems[json.barCode].sellingPrice !== json.sellingPrice) {
        toastr.error("Error: Selling Price mismatch for item with Barcode: "+ json.barCode);
        return false;
        }else{
        let qty= parseInt(processedItems[json.barCode].quantity) + parseInt(json.quantity);
        processedItems[json.barCode].quantity=qty.toString();
        }
    }
    else {
      processedItems[json.barCode] = json;
    }
    resetForm();
    displayOrderItemList();
}

function displayCart() {
    $('#add-order-item-modal').modal('toggle');
    var $tbody = $('#order-item-table').find('tbody');
    $tbody.empty();
    processedItems={};
}

function getOrderItemList() {
    var jsonObj = $.parseJSON('[' + completeOrder + ']');
    console.log(jsonObj);
}

function editOrderItem(id) {
    let keys = Object.keys(processedItems);
    let barCode = keys[id];
    let temp=processedItems[barCode];
    $("#order-item-form input[name=barCode]").val(temp.barCode);
    $("#order-item-form input[name=quantity]").val(temp.quantity);
    $("#order-item-form input[name=sellingPrice]").val(temp.sellingPrice);
    deleteOrderItem(id);
}


function displayOrder(id) {
    $('#view-order-item-modal').modal('toggle');

    var url = getOrderUrl() + "/" + id + "/items";

    var $tbody = $('#view-order-item-table').find('tbody');
    $tbody.empty();

    $.ajax({
        url: url,
        type: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $("#view-order-id").text(id);
            response.forEach(element => {
                var row = '<tr>' +
                    '<td>' + element.barCode + '</td>' +
                    '<td>' + parseInt(element.quantity) + '</td>' +
                    '<td>' + parseFloat(element.sellingPrice).toFixed(2) + '</td>' +
                    '</tr>';

                $tbody.append(row);
            });
        },
        error: function (error) {
            toastr.error(error.responseJSON.message);
        }
    });
}


function placeOrder() {
    var url = getOrderItemUrl();
    completeOrder=Object.values(processedItems);
    console.log("CompleteOrder: ");
    console.log(completeOrder);
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(completeOrder),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#add-order-item-modal').modal('toggle');
            completeOrder = []
            processedItems = Object.assign({});
            getOrderList();
        },
        error: handleAjaxError
    });

    return false;
}

function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}

function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();

	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button type="button" class="btn btn-secondary" onclick="displayOrder(' + e.id + ')">View Order</button>'
            buttonHtml+= ' <button type="button" class="btn btn-secondary" onclick="printOrder(' + e.id + ')">Invoice</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.updated + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function printOrder(id) {
    window.location.href = getInvoiceUrl() + "/" + id;
}

function init() {
    $("#add-order-item-modal").on('shown', function () {
        completeOrder = [];
        processedItems = Object.assign({});
    });
    $('#add-order').click(displayCart);
    $('#add-order-item').click(checkInventory);
    $('#place-order').click(placeOrder);
    $('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(getOrderItemList);