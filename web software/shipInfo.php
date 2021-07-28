<?php
$connect = mysql_connect(“localhost”); 

if (!connect) { 
	die('Connection Failed: ' . mysql_error()); 
	}
	
mysql_select_db(“cs3320”, $connect);

$ship_info = “INSERT INTO shippinginformation (address1, address2, city, state, zip) 
	VALUES ('$_POST[address1]', '$_POST[address2]', '$_POST[city]', '$_POST[state]', '$_POST[zip]')”; 
	
if (!mysql_query($user_info, $connect)) { die('Error: ' . mysql_error()); }
echo “Your information was added to the database.”;
mysql_close($connect); ?>