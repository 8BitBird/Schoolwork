<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cs3320";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error){
  die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";
$sql = "INSERT INTO userinformation (fullname, phone, email, address1, address2, city, state, zip) VALUES ('".$_POST["fullname"]."', '".$_POST["phone"]."', '".$_POST["email"]."', '".$_POST["address1"]."', '".$_POST["address2"]."', '".$_POST["city"]."', '".$_POST["state"]."', '".$_POST["zip"]."')";
if ($conn->query($sql) === TRUE){
  echo " New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
 ?>
