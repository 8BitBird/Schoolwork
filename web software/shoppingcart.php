<?php
$connect = mysql_connect(“localhost”); 

if (!connect) { 
	die('Connection Failed: ' . mysql_error()); 
	}
	
mysql_select_db(“cs3320”, $connect);

$shop_info = “INSERT INTO shoppingcartinformation (products, units, price)
	VALUES ('$_POST[products]', '$_POST[units]', '$_POST[price]')”; 
	
if (!mysql_query($user_info, $connect)) { die('Error: ' . mysql_error()); }
echo “Your information was added to the database.”;
mysql_close($connect); ?>