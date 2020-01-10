$(document).ready(
		function() {

			 $('body').on('keypress','#onlyNumbers, .onlyNumbers', function(evt) {
				if (evt.which == 0 || evt.which == 46 || evt.which == 8 || (evt.which >= 48 && evt.which <= 57)) {
				} else {
					evt.preventDefault();
				}
			});

			 $('body').on('keypress','#decimalNumbers, .decimalNumbers', function(evt) {
					if (evt.which == 44 || evt.which == 46  || (evt.which >= 48 && evt.which <= 57)) {
					} else {
						evt.preventDefault();
					}
				});
			 
			 $('body').on('keypress','#onlyNumbersWithZero, .onlyNumbersWithZero', function(evt) {
					if (evt.which >= 48 && evt.which <= 57) {
					} else {
						evt.preventDefault();
					}
				});
			 $('body').on('keypress','#timeNumbers, .timeNumbers', function(evt) {
				if (evt.which == 0 ||  evt.which == 8 || (evt.which >= 48 && evt.which <= 58)) {
				} else {
					evt.preventDefault();
				}
			});
			 $('body').on('keypress','#onlyNumbersWithOutZero, .onlyNumbersWithOutZero', function(evt) {
					if (evt.which >= 49 && evt.which <= 57) {
					} else {
						evt.preventDefault();
					}
				});
			 
			 
			 $('body').on('keypress','#onlyAlphabets, .onlyAlphabets', function(evt) {
				if ((evt.which >= 65 && evt.which <= 90) || evt.which == 32 || (evt.which >= 97 && evt.which <= 122) || evt.which == 0 || evt.which == 46 || evt.which == 8) {
				} else {
					evt.preventDefault();
				}
			});

			 $('body').on('keypress','.bothNumbersAndAlphabets', function(evt) {
				if ((evt.which >= 65 && evt.which <= 90) || evt.which == 32 || (evt.which >= 97 && evt.which <= 122) || evt.which == 0 || evt.which == 46 || evt.which == 8 || evt.which == 0
						|| evt.which == 45 || evt.which == 8 || (evt.which >= 48 && evt.which <= 57)) {
				} else {
					evt.preventDefault();
				}
			});
			
			 $('body').on('keypress','#onlyNumbersAndSpecialChars, .onlyNumbersAndSpecialChars', function(evt) {
				 
				 var regex = new RegExp("^[0-9()+- ]+$");
		            var key = String.fromCharCode(evt.charCode ? evt.which : evt.charCode);
		            if (!regex.test(key)) {
		                event.preventDefault();
		                return false;
		            }
				});
			 
			 $('body').on('keypress','.phoneNumber', function(evt) {
			     var charCode = (evt.which) ? evt.which : event.keyCode
			     if (charCode != 43 && charCode > 31 && (charCode < 48 || charCode > 57))
			         return false;
			     return true;
			 });
		
			 $.fn.serializeObject = function()
			 {				 
			     var o = {};
			     var a = this.serializeArray();
			     $.each(a, function() {
			         if (o[this.name] !== undefined) {
			             if (!o[this.name].push) {
			                 o[this.name] = [o[this.name]];
			             }
			             o[this.name].push(this.value || '');
			         } else {
			             o[this.name] = this.value || '';
			         }
			     });
			     return o;
			 };
			 
			 
		});