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
$sql = "SELECT description FROM products ORDER BY description";
if ($conn->query($sql) === TRUE){
  echo " Fetched successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
 ?>
