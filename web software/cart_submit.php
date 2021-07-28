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
$sql = "INSERT INTO shoppingcartinformation (products, units, price) VALUES ('".$_POST["products"]."', '".$_POST["units"]."', '".$_POST["prices"]."')";
if ($conn->query($sql) === TRUE){
  echo " New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
 ?>
