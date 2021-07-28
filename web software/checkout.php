<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cs3320";

$m=$_POST["month"];
$y=$_POST["year"];
$ex = $m.$y;

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error){
  die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";
$sql = "INSERT INTO paymentinformation (cardType, cardNumber, expDate) VALUES ('".$_POST["cardType"]."', '".$_POST["cardNumber"]."', '".$ex."')";
if ($conn->query($sql) === TRUE){
  echo " Payment information entered!";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
 ?>
