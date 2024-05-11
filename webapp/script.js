"use strict"
var lat;
var lon;

function login(){
	$("#action1").css("display","none");
	$("#log").css("display","block");
}

$(document).ready(function(){
	isLoggedIn();
	$("#log").css("display","none");
	$("#register1").css("display","none");
});

function register(){
	$("#action1").css("display","none");
	$("#register1").css("display","block");
	$(".footer1").css("display","block");
    $(".footer").css("display","none");
}

function showPass() {
    let x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
        show.textContent = "Hide Password";
    } else {
        x.type = "password";
        show.textContent = "Show Password";
    }
}

function checkCredentials(){
	var xhr = new XMLHttpRequest();
    var username=document.getElementById('username');
    var password=document.getElementById('password');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#login3").html("");
            $("#select_action").css("display","block");
            $("#logout").css("display","block");
            $("#login3").append("<h1>Statistics</h1>");
            $("#choices").html("");
            $("#choices").append("<button onclick='statistics()'  class='button'>Visualization of Students</button>");
            $("#choices").append("<button onclick='statisticsLibraries()'  class='button'>Visualization Libraries</button>");
            $("#choices").append("<button onclick='statisticsBooks()'  class='button'>Visualization Books</button>");
            $("#choices").append("<button onclick='GetStudentTable()'  class='button'>Visualization of Students Table</button>");
            $("#choices").append("<button onclick='GetLibrarianTable()'  class='button'>Visualization of Librarian's Table</button>");
            document.getElementById("log").style.display = "none";
        } else if (xhr.status === 202) {
			$("#select_action").css("display","block");
			$("#logout").css("display","block");
			$("#choices").html("");
	        $("#choices").append("<button onclick='addInfo();' >Update Book Info</button>");
	        $("#choices").append("<button onclick='setAvailable();' >Change Book Availability</button>");
	        $("#choices").append("<button onclick='setStatus();' >Set Borrow Request Status</button>");
	        $("#choices").append("<button onclick='getBorrows();' >See Current Borrows</button>");
	        $("#choices").append("<button onclick='seeBooks();' >See Books</button>");
            document.getElementById("log").style.display = "none";
        }else if(xhr.status === 203){
			$("#select_action").css("display","block");
			$("#logout").css("display","block");
			$("#choices").html(""); 
			$("#choices").append("<button onclick='bookAvailability();' >See Book Availability</button>");
			$("#choices").append("<button onclick='searchForBook();' > Search for a Book and Closest Libraries</button>");
			$("#choices").append("<button onclick='requestForBook();' > Request For a Book</button>");
			$("#choices").append("<button onclick='seeNotifications();'>See Notifications</button>");
			$("#choices").append("<button onclick='showActiveBorrowings();'>Show Active Borrowings</button>");
			$("#choices").append("<button onclick='giveReview()'>Give A Review</button>");
			document.getElementById("log").style.display = "none";
        }else{
			$("#login3").html('<h1>Request failed. Returned status of ' + xhr.status + '</h1>');
		}
    };
    xhr.open('POST', 'admin');
    xhr.setRequestHeader('Content-type','application/json');
    var data=JSON.stringify({ username: username.value, password: password.value });
    xhr.send(data);
}

function bookReg(){
	let myForm = document.getElementById('myform');
	let formData = new FormData(myForm);
	const data = {};
	formData.forEach((value, key) => (data[key] = value));
	console.log(JSON.stringify(data));
	var xhr = new XMLHttpRequest();
	xhr.onload = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			$("#posterror").html(xhr.status);
		}else if(xhr.status!==200){
			$("#posterror").html("Error "+xhr.status);
		}
	}
	xhr.open('POST','http://localhost:8081/Libraries1/resources/Librarian/books');
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(data));
}
function isLoggedIn(){
	var xhr = new XMLHttpRequest();
		xhr.onload = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				if(xhr.responseText === "admin"){
					$("#action1").css("display","none");
					$("#login3").html("");
            		$("#login3").append("<h1>Statistics</h1>");
            		$("#choices").html("");
            		$("#choices").append("<button onclick='statistics()'  class='button'>Visualization of Students</button>");
            		$("#choices").append("<button onclick='statisticsLibraries()'  class='button'>Visualization Libraries</button>");
            		$("#choices").append("<button onclick='statisticsBooks()'  class='button'>Visualization Books</button>");
            		$("#choices").append("<button onclick='GetStudentTable()'  class='button'>Visualization of Students Table</button>");
            		$("#choices").append("<button onclick='GetLibrarianTable()'  class='button'>Visualization of Librarian's Table</button>");
            	}else if(xhr.responseText === "librarian"){
					$("#action1").css("display","none");
					$("#choices").html("");
					$("#choices").append("<button onclick='addInfo();' >Update Book Info</button>");
	        		$("#choices").append("<button onclick='setAvailable();' >Change Book Availability</button>");
	        		$("#choices").append("<button onclick='setStatus();' >Set Borrow Request Status</button>");
	        		$("#choices").append("<button onclick='getBorrows();' >See Current Borrows</button>");
					$("#choices").append("<button onclick='seeBooks();' >See Books</button>");
				}else if(xhr.responseText === "student"){
					$("#action1").css("display","none");
					$("#choices").html("");
					$("#choices").append("<button onclick='bookAvailability();' >See Book Availability</button>");
					$("#choices").append("<button onclick='searchForBook();' > Search for a Book and Closest Libraries</button>");
					$("#choices").append("<button onclick='requestForBook();' > Request For a Book</button>");
					$("#choices").append("<button onclick='seeNotifications();'>See Notifications</button>");
					$("#choices").append("<button onclick='showActiveBorrowings();'>Show Active Borrowings</button>");
					$("#choices").append("<button onclick='giveReview()'>Give A Review</button>");
				}
            	$("#select_action").css("display","block");
			} else if (xhr.status !== 200) {
				
			}
		};
		xhr.open('GET', 'admin');
		xhr.send();
}

function addInfo(){
	let registerbook = "<h1>Update Book Info</h1>";
	registerbook += "<form id='myform' onsubmit='bookReg();return false;'>";
	registerbook += "<label for='isbn'>ISBN:<input name='isbn' id='isbn'></label><br>";
	registerbook += "<label for='title'>Title:<input name='title' id='title'></label><br>";
	registerbook += "<label for='authors'>Authors:<input name='authors' id='authors'></label><br>";
	registerbook += "<label for='genre'>Genre:<input name='genre' id='genre'></label><br>";
	registerbook += "<label for='pages'>Pages:<input name='pages' id='pages'></label><br>";
	registerbook += "<label for='publicationyear'>Publication Year:<input name='publicationyear' id='publicationyear'></label><br>";
	registerbook += "<label for='url'>URL:<input name='url' id='url'></label><br>";
	registerbook += "<label for='photo'>Photo:<input name='photo' id='photo'></label><br>";
	registerbook += "<input type='submit' value='Submit' ><span id='posterror'>Awaiting for submit...</span></form>";
	$("#login3").html("");
	$("#login3").html(registerbook);
}

function setAvailable(){
	$("#login3").html("");
	$("#login3").append("<h1>Change Book Availability</h1>");
	$("#login3").append("<label for='ISBN'> ISBN of the Book to be added in your Library:");
	$("#login3").append("<input type='text' id='isbn'></label><br>");
	$("#login3").append("<button id='add' onclick = 'addBook();'>Add</button>");
}
function addBook(){
	var xhr = new XMLHttpRequest();
	let data = document.getElementById('isbn').value;
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			
		}else if(xhr.status!==200){
			
		}
	}
	xhr.open('POST', 'AddToBooksInLibrary');
	//xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send(data);
}
function setStatus(){
	$("#login3").html("");
	$("#login3").append("<h1>Change Borrowing Status</h1>");
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(createTableFromRequests(JSON.parse(xhr.responseText)));
			$("#login3").append("<label> ISBN of the Book:<input type='text' id='isbn'></label><br>");
			$("#login3").append("<label> New Status of the Book:<input type='text' id='newstatus'></label><br>");
			$("#login3").append("<button onclick = changeStatus();>Change</button>")
			$("#login3").append("<span id='message'></span>");
		}else if(xhr.status!==200){
			$("#message").html("Error at updating borrow status.");
		}
	}
	xhr.open('POST', 'GetBorrowed');
	//xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send();
}

function createTableFromRequests(data){
	for(let i=0;i<data.length;i++){
		data[i]=JSON.parse(data[i]);
	}
	//for(let i=0;i<data.length;i++){
		//data[i].distance = i;
	//}
	let html = "";
	let i=0;
	data.forEach(function(object){
		i++;
		html += "<table><tr><th>Book "+i+"</th></tr>";
		Object.entries(object).forEach(function([key, value]) {
    		html += "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
		});
		html += "</table>";
	});
	return html;
	
}

function booksInLibrary(data){
	for(let i=0;i<data.length;i++){
		data[i]=JSON.parse(data[i]);
	}
	let html = "";
	let i=0;
	data.forEach(function(object){
		i++;
		html += "<table><tr><th>Book "+i+"</th></tr>";
		Object.entries(object).forEach(function([key, value]) {
    		if(key === "url"){
				html += "<tr><td>" + key + "</td><td><a href='" + value + "'>Book URL</a></td></tr>";
			}else if(key === "photo"){
				html += "<tr><td>" + key + "</td><td><img src='" + value + "' alt='Book Image' height='50' width='70'></td></tr>";
			}else{
    			html += "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
    		}

		});
		html += "</table>";
	});
	return html;
	
}

function giveReview(){
	$("#login3").html("");
	$("#login3").append("<h1>Give a Review to returned Books</h1>");
	$("#login3").append("<label for='isbn'>ISBN <input type='text' id='isbn'></label><br>");
	$("#login3").append("<label>Give your Review:</label><textarea id='review' name='review' rows='4' cols='50' </textarea>")
	$("#login3").append("<br><button onclick='review();'>Submit your Review</button>");
}
function review(){
	let review = document.getElementById('review').value;
	let isbn = document.getElementById('isbn').value;
	let data = {
		"review":review,
		"isbn":isbn
	};
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(createTableForDistances(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			
		}
	}
	xhr.open('POST', 'Review');
	xhr.send(data);
}

function showActiveBorrowings(){
	$("#login3").html("");
	$("#login3").append("<h1> Show Active Borrowings</h1>");
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(createTableForDistances(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			
		}
	}
	xhr.open('POST', 'ShowActiveBorrowings');
	xhr.send();
}

function changeStatus(){
	var xhr = new XMLHttpRequest();
	let isbn = document.getElementById('isbn').value;
	let status = document.getElementById('newstatus').value;
	let data={
		"isbn":isbn,
		"status":status
	};
	data = JSON.stringify(data);
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#message").html("Book status changed Succesfully");
		}else if(xhr.status!==200){
			$("#message").html("ERROR: ISBN given or status may be incorrect");
		}
	}
	xhr.open('POST', 'BorrowStatusChange');
	xhr.send(data);
}

function getBorrows(){
	$("#login3").html("");
	$("#login3").append("<h1>See All Books</h1>");
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(createTableFromRequests(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			
		}
	}
	xhr.open('POST', 'SeeBorrowed');
	xhr.send();
}
function seeBooks(){
	$("#login3").html("");
	$("#login3").append("<h1>See All Books</h1>");
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(booksInLibrary(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			alert('Request failed. Returned status of ' + xhr.status);
		}
	}
	xhr.open('POST', 'GetBooksInLibrary');
	xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send();
}

function searchForBook(){
	$("#login3").html("");	
	$("#login3").append("<h1>Search For A Book</h1>");
	$("#login3").append("<label> ISBN of the Book to be Searched in Libraries:<input type='text' id='isbn'></label><br>");
	$("#login3").append("<button onclick='getLibraries();'>Search</button>");
	$("#login3").append("<span id='message' style='color:red;'></span>")
}
function getLibraries(){
	let data = document.getElementById('isbn').value;
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			console.log(xhr.responseText);
			$("#message").html("");
			let json = JSON.parse(xhr.responseText);
			lat = json[json.length-1].latt;
			lon = json[json.length-1].lonn;
			let json1=new Array();
			for(let i=0;i<json.length-1;i++){
				json1.push(json[i]);
			}
			console.log(json1);
			distanceFromLibrary(json1);
			
		}else if(xhr.status!==200){
			$("#message").html("There are no available books with the given ISBN");
		}
	}
	xhr.open('POST', 'SeeLibraries');
	xhr.send(data);
}
function distanceFromLibrary(data){
	/*let lats = new Object();
	lats = {
		"lat": 35.3053121,
		"lon": 25.0722869
	}*/
	let data1 ="https://trueway-matrix.p.rapidapi.com/CalculateDrivingMatrix?origins=";
	data1 += lat+"%2C";
	data1 += lon+"&destinations=";
	for(let j = 0;j<data.length;j++){
		data1+= data[j].lat+"%2C";
		data1+= data[j].lon;
		if(j!=data.length-1){
			data1+="%3B";
		}
	}
	console.log(data1);
	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;
	
	
	xhr.addEventListener("readystatechange", function () {
		if (this.readyState === this.DONE) {
			let temp = JSON.parse(this.responseText);
			console.log(JSON.parse(this.responseText));
			for(let i = 0;i<data.length;i++){
				data[i].distance_in_km = temp.distances[0][i]/1000;
				let minutes = Math.floor(temp.durations[0][i]/60);
				let seconds = temp.durations[0][i] - minutes * 60;
				data[i].ByCar = minutes +"minutes and " + seconds + "seconds";
			}
			let sorted = data.sort(
				(m1,m2)=>(m1.distance_in_km > m2.distance_in_km)? 1 : (m1.distance_in_km < m2.distance_in_km)? -1 :0);
			$("#login3").html("");
			$("#login3").append("<h1> Search For A Book</h1>")
			$("#login3").append(createTableForDistances(data));
			console.log(sorted);
		}
	});

	xhr.open("GET",data1);
	xhr.setRequestHeader("X-RapidAPI-Key", "bee5ba2fa0msha22fd23c0bc9329p10e3b2jsn4ba634030c60");
	xhr.setRequestHeader("X-RapidAPI-Host", "trueway-matrix.p.rapidapi.com");

	xhr.send();
}
function seeNotifications(){
	$("#login3").html("");
	$("#login3").append("<h1>See Notifications</h1>");
	$("#login3").append("<span style='color:red'>WARNING: You have to return these books in 3 days or less</span>")
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(createTableForDistances(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			
		}
	}
	xhr.open('POST', 'SeeNotifications');
	xhr.send();
}


function createTableForDistances(data){
	let html = "";
	let i=0;
	data.forEach(function(object){
		i++;
		html += "<table><tr><th>Book "+i+"</th></tr>";
		Object.entries(object).forEach(function([key, value]) {
			if(key !== "libraryinfo")
    			html += "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
    		else
    			html += "<tr><td>" + key + "</td><td><textarea cols='32' rows='11'>"+value+"</textarea></td></tr>";
    		
		});
		html += "</table>";
	});
	return html;
}

function requestForBook(){
	$("#login3").html("");
	$("#login3").append("<h1> Request a Book </h1>");
	$("#login3").append("<label for='libid'>Library ID of the Library(press the Search For a Book button to see the libraries)");
	$("#login3").append("<input type='text' id='libid'></label><br>");
	$("#login3").append("<label for='ISBN'> ISBN of the Book <br>");
	$("#login3").append("<input type='text' id='isbn'></label><br>");
	$("#login3").append("<button id='add' onclick = 'requestBook();'>Add</button>");
}

function requestBook(){
	let isbn = document.getElementById('isbn').value;
	let libid = document.getElementById('libid').value;
	let data = {
		"isbn":isbn,
		"libid":libid
	};
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			
		}else if(xhr.status!==200){
			alert('Request failed. Returned status of ' + xhr.status);
		}
	}
	xhr.open('POST', 'Borrow');
	xhr.send(JSON.stringify(data));
}
function logout(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			setChoicesforLogout();		
		}else if(xhr.status!==200){
			alert('Request failed. Returned status of ' + xhr.status);
		}
	}
	xhr.open('POST', 'Logout');
	xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send();
}	
function bookAvailability(){
	$("#login3").html("");
	$("#login3").append("<h1>See All Books</h1>");
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.readyState === 4 && xhr.status === 200){
			$("#login3").append(booksInLibrary(JSON.parse(xhr.responseText)));
		}else if(xhr.status!==200){
			alert('Request failed. Returned status of ' + xhr.status);
		}
	}
	xhr.open('POST', 'http://localhost:8081/Libraries1/resources/Librarian/booksavailable');
	xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhr.send();
}

function setChoicesforLogout(){
	$("#logout").css("display","none");
	$("#select_action").css("display","none");
	$("#action1").css("display","block");
	$("#login3").html("");
	$("#login3").html("<h1> Information </h1>")
}
function statistics(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
             console.log(responseData);
             $('#login3').html("<h2>Students </h2>");                 
           createPieGraphicsStudent(responseData);
        } else if (xhr.status !== 200) {
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('GET', 'Statistics?type=student');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}
function statisticsLibraries(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
             console.log(responseData);
            $('#login3').html("<h2>Libraries</h2>");
           createPieGraphicsLibrary(responseData);
        } else if (xhr.status !== 200) {
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('GET', 'Statistics?type=library');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}

function statisticsBooks(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
             const responseData = xhr.responseText;
             console.log(responseData);
            $('#login3').html("<h2>Books</h2>");
           createPieGraphicsBooks(responseData);
        } else if (xhr.status !== 200) {
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('GET', 'Statistics?type=books');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}



function createPieGraphicsStudent(jsonData) {
   $('#login3').append("<div  id='piechart_3d'></div>");
   google.charts.load("current", {packages: ["corechart"]});
    var i=0;
    const labels = new Array();
    const values = new Array();
     for (const x in jsonData) {
         labels.push(x);
         if(!isNaN(jsonData[x])){
             if(i===1){
                 labels[x]="BSc";
                 i++;
             }else if(i===2){
              labels[x]="PhD";
              i++;
             }else if(i===0){
                labels[x]="MSc";
                i++;
             }
         }
         values.push(parseInt(jsonData[x]));     
     }
     console.log(labels);
      console.log(values);
  google.charts.setOnLoadCallback(function () {
       drawPieChart(labels, values);
   });
}

function createPieGraphicsLibrary(jsonData) {
     $('#login3').append("<div  id='piechart_3d'></div>");
     google.charts.load("current", {packages: ["corechart"]});
     var i=1;
     const labels = new Array();
     const values=new Array();
     for (const x in jsonData){
         labels.push(x);
          if(!isNaN(jsonData[x])){
              if(i===1){
                  labels[x]="library"+i;
                  i++;
              }else{
                  labels[x]="library"+i;
                  i++;
              }
          }
     values.push(parseInt(jsonData[x]));      
     }
     google.charts.setOnLoadCallback(function () {
      drawPieChart(labels, values);
  });
      
}

function createPieGraphicsBooks(jsonData) {
     $('#login3').append("<div  id='piechart_3d'></div>");
     google.charts.load("current", {packages: ["corechart"]});
     var i=0;
     const labels = new Array();
     const values=new Array();
     for (const x in jsonData){
         labels.push(x);
         if(!isNaN(jsonData[x])){
              if(i===0){
                  labels[x]="Fantasy";
                  i++;
              }else if(i===1){
                  labels[x]="Romance";
                  i++;
              }else if(i===2){
                  labels[x]="Novel";
                  i++;
              }else if(i===3){
                  labels[x]="Sports";
                  i++;
              }else if(i===4){
                  labels[x]="Adventure";
                  i++;
              }
         }
          values.push(parseInt(jsonData[x]));
     }
     google.charts.setOnLoadCallback(function () {
          drawPieChart(labels, values);
     });
     
}


function drawPieChart(column1, column2) { 
    var dataVis = new google.visualization.DataTable();
    dataVis.addColumn('string', 'category');
    dataVis.addColumn('number', 'value');
    for (let i = 0; i < column1.length; i++) {
        dataVis.addRow([column1[i], column2[i]]);
    }
    var options = {
        title: 'Students',
        'height': 200,
        is3D: true
        
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
    chart.draw(dataVis, options);
}