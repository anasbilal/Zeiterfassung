var rootURL = "http://localhost:8080/i_advise_zeiterfassung/rest/mitarbeiter/";

		var data=[];
		$(document).ready(function() {
			findAlle();
			});

		function addMitarbeiter() {
			console.log('addMitarbeiter');
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : rootURL,
				dataType : "json",
				data : formToJSON(),
				success : function(data, textStatus, jqXHR) {
					alert('Mitarbeiter created successfully');
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('addMitarbeiter error: ' + textStatus);
				}
			});
		}

		$('#btnSave').click(function() {
			addMitarbeiter();
			return false;
		});

		function formToJSON() {
			return JSON.stringify({
				"vorname" : $('#vorname').val(),
				"personalnummer" : 123,
				"mitID" : 6,
				"nachname" : $('#nachname').val(),
				"geburtstag" : "1972-03-01",
				"geburtsort" : "Marrakech",
				"straße" : $('#strasse').val(),
				"plz" : $('#plz').val(),
				"stadt" : $('#stadt').val(),
				"telefonnummerIADVISE" : "112234",
				"mobilnummerIADVISE" : "123245",
				"festnetzprivat" : "123245",
				"mobilnummerprivat" : "123245",
				"notfallnummer" : "123245",
				"standort" : "Muenchen",
				"position" : "Manager",
				"email" : "at@gmail.com"
			});
		}

		function findAll() {
			console.log('findAlle');
			$.ajax({
				type : 'GET',
				url : rootURL,
				dataType : "json", // data type of response
				data : data,
				success : renderList
			});
		}
		function findAlle() {
			console.log('findAlle');
			$.ajax({
				type : 'GET',
				url : rootURL,
				dataType : "json", // data type of response
				success : renderSelect
			});
		}
		function renderSelect(data){
			var list = data == null ? [] : (data instanceof Array ? data
					: [ data ]);
			$.each(list, function(index, mitarb){
				$("<option></option>", {value: mitarb.vorname+' '+mitarb.nachname, text: mitarb.vorname+' '+mitarb.nachname})
				.appendTo('.select-group');
			});
		}

		/*function renderList(data) {
			// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
			var list = data == null ? [] : (data instanceof Array ? data
					: [ data ]);

			$('#mitarbeiterList li').remove();
			$.each(list, function(index, mitarb) {
						$('#mitarbeiterList').append(
						'<li id="' + mitarb.mitid + '">' + mitarb.vorname + ' '
								+ mitarb.nachname + '</li>');
			});
		}*/