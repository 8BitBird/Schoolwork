<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cs3320";

$conn = new mysqli($servername, $username, $password, $dbname);

$conn = new mysqli("localhost", "root", "", "cs3320");


if ($conn->connect_error){
  die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";

#<select name="state">

$sql = mysqli_query($connection, "SELECT state FROM statelist");
while ($row = $sql->fetch_assoc()){
echo "<option value=\"state\">" . $row['state'] . "</option>";
}

#</select>
?>
