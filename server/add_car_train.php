<?php
 
require_once __DIR__ . '/connect.php';

// array for JSON response
$response = array();
$db = new DB_CONNECT();
$db->connect();
$con = $db->myconn;
$dest = "timisoara";
$sursa = "bucuresti";



$sql = "SELECT * FROM races WHERE departure_city='$dest' AND arrival_city='$sursa'";
$result = mysqli_query($con, $sql);
while($row = mysqli_fetch_assoc($result)) {
	$uid = $row["uid"];
	$departure_city = $row["departure_city"];
	$arrival_city = $row["arrival_city"];
	$departure_datetime = new DateTime($row["departure_time"]);
	$departure_date = $departure_datetime->format('d-m-Y');
	$departure_time = $departure_datetime->format('H:i:s');
	$arrival_datetime = new DateTime($row["arrival_time"]);
	$arrival_date = $arrival_datetime->format('d-m-Y');
	$arrival_time = $arrival_datetime->format('H:i:s');
	$departure_location = $row["departure_location"];
	/*
	echo $uid,",",$departure_city,",",$arrival_city,",",$departure_date,",",$departure_time,",",$arrival_date,",",$arrival_time,",",$departure_location.PHP_EOL;
	*/
}


?>