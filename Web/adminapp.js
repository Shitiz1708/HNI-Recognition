var firebaseConfig = {
    apiKey: "AIzaSyDF65QhJb2WGnM7LODCaRmzBBJezz0VSnY",
    authDomain: "synd-innovate-admin-portal.firebaseapp.com",
    databaseURL: "https://synd-innovate-admin-portal.firebaseio.com",
    projectId: "synd-innovate-admin-portal",
    storageBucket: "",
    messagingSenderId: "875276448100",
    appId: "1:875276448100:web:f449be88b97244cd"
};

var UserfirebaseConfig = {
    apiKey: "AIzaSyCwyq9kH7RcKY9VxMqQA0_RTBE9dFxJq8w",
    authDomain: "synd-innovate-e51cc.firebaseapp.com",
    databaseURL: "https://synd-innovate-e51cc.firebaseio.com",
    projectId: "synd-innovate-e51cc",
    storageBucket: "synd-innovate-e51cc.appspot.com",
    messagingSenderId: "1018207747989",
    appId: "1:1018207747989:web:3df1eb0cc7e33e03"
};

firebase.initializeApp(firebaseConfig);
var Userfirebase = firebase.initializeApp(UserfirebaseConfig, "Userfirebase");


function adminlogin(){
    var adminusername = document.getElementById("adminusername").value;
    var adminpassword = document.getElementById("adminpassword").value;
    firebase.auth().signInWithEmailAndPassword(adminusername, adminpassword)
    .catch(function(error) {
  var errorCode = error.code;
  var errorMessage = error.message;
  if (errorCode === 'auth/wrong-password') {
    alert('Wrong password.');
  } else {
    alert(errorMessage);
  }
  console.log(error);
});
}

function adminlogout(){
    firebase.auth().signOut().catch(function(error){
        var errorCode = error.code;
        var errorMessage = error.message;
        console.log(error);
    });
}


firebase.auth().onAuthStateChanged(function(user){
    if(user){
        console.log("logged in");
        document.getElementById('notauth').style.display='none';
        document.getElementById('auth').style.display='block';


        var detected_table = document.getElementById("detected_hni")
        var requests_table = document.getElementById("requests_hni")
        var online_table = document.getElementById("online_hni")

        var rootRef=Userfirebase.database().ref();
        var hni_detectedRef = rootRef.child("admin_app/hni_detected")
        var hni_onlineRef = rootRef.child("admin_app/hni_online")
        var hni_requests = rootRef.child("admin_app/hni_requests")
        var hni_profilesRef = rootRef.child("admin_app/profiles")

        hni_detectedRef.on("child_added", snap=>{
            var uid = snap.child("UID").val();
        
            $("#detected_hni").append("<tr><td>"+uid+"<td><tr>")
        })

        hni_onlineRef.on("child_added", snapshot=>{
            var name = snapshot.child("Name").val();
            var acc = snapshot.child("Account_Number").val();
            var email = snapshot.child("Email").val();
            
            $("#online_hni").append("<tr><td>"+name+"</td><td>"+acc+"</td><td>"+email+"</td></tr>")
        })

        hni_requests.on("child_added", picture=>{
            picture.forEach(function(childpicture){
                var date = childpicture.val().date;
                var name = childpicture.val().name;
                var message = childpicture.val().message;
                var time = childpicture.val().time;
                var purpose = childpicture.val().purpose;
                $("#requests_hni").append("<tr><td>"+name+"</td><td>"+date+"</td><td>"+time+"</td><td>"+purpose+"</td><td>"+message+"</td></tr>")
            })
})
    }
    else {
        console.log("logged out")
        document.getElementById('notauth').style.display='block';
        document.getElementById('auth').style.display='none';
    }
})

