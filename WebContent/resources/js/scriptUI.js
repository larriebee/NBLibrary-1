$(document).ready(function () {
	 
	$(function() {
	        $( "#slider" ).slider({
	        	value: $( "#amount" ).val(),
	            min: 0,
	            max: 2,
	            step: 1,
	            slide: function( event, ui ) {
	                $( "#amount" ).val( ui.value );
	            }
	        });
	        $( "#amount" ).val( $( "#slider" ).slider( "value" ) );
	});
	 
	 
	$("#addBorrowerButton").click(function(event) {
		 if(!validate(textRegex, "addBorrowerName", "Please enter a valid name") || 
		    !validate(emailRegex, "addBorrowerEmail", "Please enter a valid email")){
		 	$("#addBorrowerForm").attr("onsubmit", "return false");
		 }else{
			 $("#addBorrowerForm").attr("onsubmit", "return true");
		 }
	});
	 
	$("#addItemButton").click(function(event) {
		 if(!validate(textRegex, "addItemTitle", "Please enter a valid Title") || 
		    !validate(textRegex, "addItemAuthor", "Please enter a valid Author") ||
		    !validate(textRegex, "addItemEdition", "Please enter a valid Edition") ||
		    !validate(dateRegex, "addItemDate", "Please enter a valid date in the format mm/dd/yyyy")) {
		 	$("#addItemForm").attr("onsubmit", "return false");
		 }else{
			 $("#addItemForm").attr("onsubmit", "return true");
		 }
	});
	 
	 
	 
	$(".selectableRow").click(function(event) {
		if (event.target.type != 'checkbox') {
			$(':checkbox', this).trigger('click');
			$(this).toggleClass("selectedRow");
		}
	});
	
	
	path = window.location.href;		//Gets path from the address bar
	suffix = path.split("/");			//Splits the path and returns an array of strings
	suffix = suffix[suffix.length-1];	//Targets the end of the array to get the current page name
	suffix = suffix.split(".");
	suffix = suffix[0];
	
	$('a[href="/NBLibrary/'+suffix+'.html"]').parent().addClass('selected');
	
	var date = new Date();
	date.setFullYear(1900, 0, 1);
	
	$(function() {
        $( "#addItemDate" ).datepicker({
            changeMonth: true,
            changeYear: true,
            minDate: date,
            maxDate: new Date(),
            dateFormat: 'dd/mm/yy',
            yearRange: "-113:-0",
            showAnim: "slideDown"
        });
    });
	}
	

);


var textRegex = /^[a-zA-Z\s\'\.\-\&]+$/;

var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

var dateRegex = /(0?[1-9]|[12][0-9]|3[01])\/(0?[1-9]|1[012])\/((19|20)\d\d)/;

function validate(regex, inputField, errorMessage) {
	var operand = document.getElementById(inputField).value;
	if(!regex.test(operand)) {
		alert(errorMessage);
	}
	return regex.test(operand);
}

function helpBox(message) {
	return '<div class="helpMessage message">'
		+ '<img src="resources/images/help.png" />'
		+ '<p>' + message + '</p>'
		+ '</div>';
}
	