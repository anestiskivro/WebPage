var lat;
var lon;
var valid = false;

function loadMap() {
    const data = null;
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            const obj = JSON.parse(this.responseText);
            console.log(this.responseText);
            if (this.responseText === "{}") {
                let x = document.getElementById("adderror");
                x.innerHTML = "Address Error";
                x.style.color = "red";
            } else {
                let crete = obj[0].display_name;
                lat = obj[0].lat;
                lon = obj[0].lon;
                document.getElementById("displaymap").style.display="none";
                let x = document.getElementById("adderror");
                x.innerHTML = "";
                if (crete.includes("Κρήτης") === false) {
                    document.getElementById("notcrete").innerHTML = "This service is only available for Crete";
                    document.getElementById("notcrete").style.color = "red";
                } else {
                    document.getElementById("notcrete").innerHTML = "";
                    document.getElementById("displaymap").style.display="block";
                }
            }

        }
    });
    let addressName = document.getElementById("ad").value;
    let city = document.getElementById("city").value;
    let country = document.getElementById("country").value;
    let address = addressName + " " + city + " " + country;
    xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q=" + address + "&acceptlanguage=en&polygon_threshold=0.0");
    xhr.setRequestHeader("X-RapidAPI-host", "forward-reverse-geocoding.p.rapidapi.com");
    xhr.setRequestHeader("X-RapidAPI-key", "bee5ba2fa0msha22fd23c0bc9329p10e3b2jsn4ba634030c60");
    xhr.send(data);

}
var map;
var mapnik;
var mar;
function displayMap() {
    document.getElementById("Map").style.display="block";
    map= new OpenLayers.Map("Map");
    mapnik = new OpenLayers.Layer.OSM();
    map.addLayer(mapnik);
    var markers = new OpenLayers.Layer.Markers("Markers");
    map.addLayer(markers);


    var position = setPosition();
    mar = new OpenLayers.Marker(position);
    markers.addMarker(mar);
    mar.events.register('mousedown', mar, function (evt) {
        handler(position, 'Your Address');
    });
    const zoom = 11;
    map.setCenter(position, zoom);
}

function setPosition() {
    var fromProjection = new OpenLayers.Projection("EPSG:4326");
    var toProjection = new OpenLayers.Projection("EPSG:900913"); 
    var position = new OpenLayers.LonLat(lon, lat).transform(fromProjection,
        toProjection);
    return position;
}
function handler(position, message) {
    var popup = new OpenLayers.Popup.FramedCloud("Popup",
        position, null,
        message, null,
        true
    );
    map.addPopup(popup);
}

function clearMap(){
    document.getElementById("Map").style.display="none";
    document.getElementById("displaymap").style.display="none";
   // map.removeLayer(mapnik);
    //map.removeLayer(mar);

}


function passwordCheck() {
    if (document.getElementById("pass").value !== document.getElementById("cpass").value) {
        document.getElementById("error").style = "color:red;";
        document.getElementById("error").innerHTML = "Passwords not match";
    } else {
        document.getElementById("error").innerHTML = "";
    }
}

function showPass() {
    let x = document.getElementById("pass");
    let y = document.getElementById("cpass");
    let but = document.getElementById("show");
    if (x.type === "password") {
        x.type = "text";
        y.type = "text";
        show.textContent = "Hide Password";
    } else {
        y.type = "password";
        x.type = "password";
        show.textContent = "Show Password";
    }

}

function passwordStrength() {
    let pass = document.getElementById("pass").value;
    let bar = document.getElementById("bar");
    let text = document.getElementById("strength");

    bar.value = "0";

    if (pass.includes("helmepa") || pass.includes("uoc") || pass.includes("tuc")) {
        bar.value="33";
        valid = false;
    } else if (numberPercentage(pass) > 0.5) {
        bar.value = "33";
        valid = false;
        text.textContent = "Weak Password";
    } else if (strong(pass) === true) {
        bar.value = "100"
        text.textContent = "Strong Password";
    } else if (text.value === "") {
        bar.value = "0";
        text.textContent = "Weak Password";
    } else {
        bar.value = "66.6";
        text.textContent = "Medium Password";
    }
}


function numberPercentage(string) {

    let cnt = 0;
    let percentage;
    for (let i = 0; i < string.length; i++) {
        if (isNumber(string[i]) === true) {
            cnt++;
        }
    }
    percentage = cnt / string.length;
    return percentage;
}
function isNumber(char) {
    return !isNaN(char);
}

function strong(string) {
    let special = "!@#$%^&*()-=,./[];'";
    let characters=[];
    let capitals = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
    let alphabet = "abcdefghijklmnopqrstuvwxyz";
    let onechar = false;
    let onecapital = false;
    let twospecial = false;
    let cnt = 0;

    for (let i = 0; i < string.length; i++) {
        for (let j = 0; j < special.length; j++) {
            if(string[i] === special[j]){
                if(characters.includes(special[j]) === false){
                    characters.push(special[j]);
                    cnt++;
                }
            }
            if(cnt===2){
                twospecial=true;
                break;
            }
        }

        for (let j = 0; j < capitals.length; j++) {
            if (string[i] === capitals[j]) {
                onechar = true;
                break;
            }
        }
        for (let j = 0; j < alphabet.length; j++) {
            if (string[i] === alphabet[j]) {
                onecapital = true;
                break;
            }
        }

    }
    if (cnt >= 2) {
        twospecial = true;
    }
    return twospecial && onecapital && onechar;
}



function emailCheck() {


    var email = document.getElementById("email").value;
    var m = document.getElementById("mailerror");
    m.style = "color:red;"

    if (document.getElementById("uoc").checked === true) {
        if (email.endsWith("uoc.gr") === false) {
            m.innerHTML = "Invalid Email address";
            valid = false;
        } else {
            m.innerHTML = "";
            valid = true;
        }
    } else if (document.getElementById("tuc").checked === true) {
        if (email.endsWith("tuc.gr") === false) {
            m.innerHTML = "Invalid Email address";
            valid = false;
        } else {
            m.innerHTML = "";
            valid = true;
        }
    } else if (document.getElementById("hel").checked === true) {
        if (email.endsWith("helmepa.gr") === false) {
            m.innerHTML = "Invalid Email address";
            valid = false;
        } else {
            m.innerHTML = "";
            valid = true;
        }
    }
}

function datesCheck() {
    let startdate = document.getElementById("startdate").value;
    let expdate = document.getElementById("expdate").value;

    if (expdate < startdate)
        valid = false;
    else
        valid = true;
}
function checkExp() {
    let startdate = new Date(document.getElementById("startdate").value);
    let expdate = new Date(document.getElementById("expdate").value);

    let years = expdate.getFullYear() - startdate.getFullYear();
    if (document.getElementById("under").checked === true) {
        if (years > 6) {
            valid = false;
        } else {
            valid = true;
        }
    } else if (document.getElementById("grad").checked === true) {
        if (years > 2) {
            valid = false;
        } else {
            valid = true;
        }
    } else if (document.getElementById("phd").checked === true) {
        if (years > 5) {
            valid = false;
        } else {
            valid = true;
        }
    }
}

function isLibrarian() {
    if (document.getElementById("Library").checked === true) {
        document.getElementById("forstudent").style.display = "none";
        document.getElementById("forlib").style.display = "block"
        document.getElementById("address").innerHTML = "Library Address:";
        document.getElementById("textarea").style.display = "block";
    }
}
function isStudent() {
    if (document.getElementById("Student").checked === true) {
        document.getElementById("forlib").style.display = "none";
        document.getElementById("forstudent").style.display = "block";
        document.getElementById("address").innerHTML = "Address:";
        document.getElementById("textarea").style.display = "none";
    }
}

function submitForm() {
    datesCheck();
    checkExp();
    if (valid === false) {
        document.getElementById("sub").style = "color:red";
        document.getElementById("sub").innerHTML = "Something went wrong";
        return false;
    } else {
        document.getElementById("sub").innerHTML = "";
        return true;
    }
}
function Register(){
    
     var student = document.getElementById("Student").checked;
     var lib = document.getElementById("Library").checked;
     if((student===true) && (lib===false)){
         addtoBaseStudent();
     }else{
          addtoBaseLibrarian();
     }
}function createTableFromJSONStudents(data){
//    for(let i=0;i<data.length;i++){
//        data[i]=JSON.parse(data[i]);
//    }
    let html = "";
    let i=0;
    data.forEach(function(object){
        i++;
        html += "<table><tr><th>Students "+i+"</th></tr>";
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
function createTableFromJSONBooks(data){
//    for(let i=0;i<data.length;i++){
//        data[i]=JSON.parse(data[i]);
//    }
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
function createTableFromJSONLibrarians(data){
//    for(let i=0;i<data.length;i++){
//        data[i]=JSON.parse(data[i]);
//    }
    let html = "";
    let i=0;
    data.forEach(function(object){
        i++;
        html += "<table><tr><th>Librarians "+i+"</th></tr>";
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

function createTableFromJSON(data){
       let html="";
        html += "<table><tr><th>Category</th><th>Value</th></tr>";
        data=JSON.parse(data);
        for(const x in data){
            var category = x;
            var value = data[x];
                 html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
        }
            html += "</table>";
            
        return html;
}

function GetStudentTable(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
             var responseData =JSON.parse(xhr.responseText);
             $('#login3').html("<h2>TableofStudents</h2>");
              console.log(responseData);
             $('#login3').html(createTableFromJSONStudents(responseData));
             $("#login3").append("<button onclick='deleteStudent()'  class='button'>Delete Student</button><br>");
             $("#login3").append("<input type='text' id='del1'><br><br>");
             $("#login3").append("<input type='text' id='del2'><br><br>");
           
        } else if (xhr.status !== 200) {
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('GET', 'TableContents?type=student');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}

function deleteLibrarian(){
   var xhr=new XMLHttpRequest();
    var x = document.getElementById("del").value;
    var y = document.getElementById("del3").value;
   xhr.onload=function(){
     if(xhr.readyState === 4 &&xhr.status===200){
//         var responsedata=xhr.responseText;
//         console.log(responsedata);
         $('#login3').html("<h2>Succesfully Deleted");
         GetLibrarianTable();
     }else if(xhr.status!==200){
          $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
     }
   };
   xhr.open('POST', 'TableContentsLibr');
   xhr.setRequestHeader("Content-type", "application/json");
//   var jsondata=JSON.stringify({firstname:x});
   xhr.send(JSON.stringify({id:x, lastname:y}));
}
function deleteStudent(){
    var xhr=new XMLHttpRequest();
     var x = document.getElementById("del1").value;
     var y = document.getElementById("del2").value;
    xhr.onload=function(){
        if(xhr.readyState===4 && xhr.status===200){
            $('#login3').html("<h2>Succesfully Deleted");
            GetStudentTable();
        }else if(xhr.status!==200){
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'TableContents');
    xhr.setRequestHeader("Content-type", "application/json");
//    var jsondata=JSON.stringify({firstname:x});
   xhr.send(JSON.stringify({username:x, id:y}));
}
function GetLibrarianTable(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var responseData = JSON.parse(xhr.responseText);
             $('#login3').html("<h2>TableofLibrarians</h2>");
             $('#login3').html(createTableFromJSONLibrarians(responseData));
             $("#login3").append("<button onclick='deleteLibrarian()' id='button' class='button'>Delete Librarian</button><br>");
             $("#login3").append("<input type='text' id='del'><br><br>");
             $("#login3").append("<input type='text' id='del3'><br><br>");
        } else if (xhr.status !== 200) {
            $('#login3').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('GET', 'TableContents?type=librarian');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(); 
}
function addtoBaseStudent() {
        let myForm = document.getElementById('myform');
        let formData = new FormData(myForm);
        const data = {};
        formData.forEach((value, key) => (data[key] = value));
        console.log(JSON.stringify(data));
        data.lat = lat;
        data.lon = lon;
        var xhr = new XMLHttpRequest();
        xhr.onload = function() {
           if(xhr.readyState===4 && xhr.status===200){
             var responseData = xhr.responseText;
            $('#response').html(createTableFromJSON(responseData));
            $("#response").append("<button onclick='UpdateStudent()' id='button' class='button'>Update Student</button><br>");
             $("#response").append("<input type='text' id='upd2'><br><br>");
             $("#response").append("<input type='text' id='upd3'><br><br>"); 
             $("#response").append("<input type='text' id='upd5'><br><br>");
        }else if(xhr.status!==200){
            document.getElementById('response').innerHTML='Request failed'+xhr.status+"<br>";
        }
        };
        document.getElementById("sub").innerHTML = "";
        xhr.open('POST', 'NewServletRequest');
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send(JSON.stringify(data));
    }
function addtoBaseLibrarian() {
        let myForm = document.getElementById('myform');
        let formData = new FormData(myForm);
        const data = {};
        formData.forEach((value, key) => (data[key] = value));
//        console.log(JSON.stringify(data));
        data.lat = lat;
        data.lon = lon;
        var xhr = new XMLHttpRequest();
        xhr.onload = function() {
           if(xhr.readyState===4 && xhr.status===200){
             var responseData = xhr.responseText;
            $('#response').html(createTableFromJSON(responseData));
            $("#response").append("<button onclick='UpdateLibrarian()' id='button' class='button'>Update Librarian</button><br>");
            $("#response").append("<input type='text' id='upd'><br><br>");
             $("#response").append("<input type='text' id='upd1'><br><br>");
             $("#response").append("<input type='text' id='upd4'><br><br>");
        }else if(xhr.status!==200){
            document.getElementById('response').innerHTML='Request failed'+xhr.status+"<br>";
        }
        };
        xhr.open('POST', 'NewServletRequestLibr');
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send(JSON.stringify(data));
    }  
function UpdateLibrarian(){
     var xhr=new XMLHttpRequest();
     var x = document.getElementById("upd").value;
     var y = document.getElementById("upd1").value;
      var z = document.getElementById("upd4").value;
    xhr.onload=function(){
        if(xhr.readyState===4 && xhr.status===200){
            $('#response').html("<h2>Succesfully Updated");
             GetLibrarianTable();
             $("#response").append("<button onclick='UpdateLibrarian()' id='button' class='button'>Update Librarian</button><br>");
            $("#response").append("<input type='text' id='upd'><br><br>");
             $("#response").append("<input type='text' id='upd1'><br><br>");
             $("#response").append("<input type='text' id='upd4'><br><br>");
        }else if(xhr.status!==200){
            $('#response').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'NewServletRequestLibrUpd');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify({id:x, category:y,data:z}));   
   }

function visitor(){
    $("#action1").css("display","none");
    $("#visitor").css("display","block");
}
function Books(){
    var xhr = new XMLHttpRequest();
        xhr.onload = function() {
           if(xhr.readyState===4 && xhr.status===200){
             var responseData = JSON.parse(xhr.responseText);
             console.log(responseData);
            $('#login3').html(createTableFromJSONBooks(responseData));
        }else if(xhr.status!==200){
            document.getElementById('login3').innerHTML='Request failed'+xhr.status+"<br>";
        }
        };
        
        xhr.open('GET', 'Books?type=lib');
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send();
}
function UpdateStudent(){
     var xhr=new XMLHttpRequest();
     var x = document.getElementById("upd2").value;
     var y = document.getElementById("upd3").value;
      var z = document.getElementById("upd5").value;
    xhr.onload=function(){
        if(xhr.readyState===4 && xhr.status===200){
            $('#response').html("<h2>Succesfully Updated");
            GetStudentTable();
            $("#response").append("<button onclick='UpdateStudent()' id='button' class='button'>Update Student</button><br>");
             $("#response").append("<input type='text' id='upd2'><br><br>");
             $("#response").append("<input type='text' id='upd3'><br><br>"); 
             $("#response").append("<input type='text' id='upd5'><br><br>");
        }else if(xhr.status!==200){
            $('#response').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
   
     xhr.open('POST', 'NewServletRequestUpd');
     xhr.setRequestHeader("Content-type", "application/json");
     xhr.send(JSON.stringify({id:x, category:y,data:z}));   
   }