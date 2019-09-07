var firebaseConfig = {
    apiKey: "AIzaSyCwyq9kH7RcKY9VxMqQA0_RTBE9dFxJq8w",
    authDomain: "synd-innovate-e51cc.firebaseapp.com",
    databaseURL: "https://synd-innovate-e51cc.firebaseio.com",
    projectId: "synd-innovate-e51cc",
    storageBucket: "synd-innovate-e51cc.appspot.com",
    messagingSenderId: "1018207747989",
    appId: "1:1018207747989:web:3df1eb0cc7e33e03"
};

firebase.initializeApp(firebaseConfig);

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        console.log("logged in")
        document.getElementById('notauth').style.display='none';
        document.getElementById('auth').style.display='block';
        var profileRef = firebase.database().ref("admin_app/profiles/"+(user.uid));
        profileRef.once('value').then(function(snapshot){
            if(snapshot.child('is_hni').val()=="true"){
                var hni_onlineRef = firebase.database().ref("admin_app/hni_online");
                var addOnlineStatusRef = hni_onlineRef.child(user.uid);
                addOnlineStatusRef.set({
                    Name: snapshot.child("Name").val(),
                    Account_Number: snapshot.child("Account Number").val(),
                    Email: snapshot.child("Email Address").val()
                    uid: user.uid;
            })
            }
        })
    } else {
        console.log("logged out")
        document.getElementById('notauth').style.display='block';
        document.getElementById('auth').style.display='none';
    }
  });

function login(){
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    firebase.auth().signInWithEmailAndPassword(username, password)
    .catch(function(error) {
  // Error handling
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

function logout(){
    firebase.auth().signOut().catch(function(error){
        var errorCode = error.code;
  var errorMessage = error.message;
  console.log(error);
    });
}

function mess(){
    var date = document.getElementById('date').value
    var time = document.getElementById('time').value
    var purpose = document.getElementById('purpose').value
    var message = document.getElementById('message').value
    firebase.auth().onAuthStateChanged(function(user) {
        var uid=user.uid;
        console.log(uid);
        if (user) {
            var messageRef = firebase.database().ref("admin_app/hni_requests");
            var addMessageRef = messageRef.child(uid).push();
            addMessageRef.set({
                    'date': date,
                    'message': message,
                    'purpose': purpose,
                    'time': time
            })
            window.alert("Message Sent");
        } else {
            window.alert("You are not logged in")
        }
      });
}