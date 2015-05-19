<?php
 
require_once __DIR__ . '/connect.php';

$response = array();
$db = new DB_CONNECT();
$db->connect();
$con = $db->myconn;

// array for JSON response
$response = array();

if(!empty($_POST['departure']) && !empty($_POST['arrival']) && !empty($_POST['date'])) {
	$departure = $_POST['departure'];
	$arrival = $_POST['arrival'];
	$date = $_POST['date'];
	/*
	$departure = 'timisoara';
	$arrival = 'Bucuresti';
	$date = '13-05-2015';*/

    $query = "SELECT * FROM races WHERE LOWER(departure_city)=LOWER('$departure') AND LOWER(arrival_city)=LOWER('$arrival')";
	$result = mysqli_query($con, $query);
	while($row = mysqli_fetch_assoc($result)) {
		$entry = array();
		$uid = $row["uid"];
		$user_query = "SELECT * FROM users WHERE uid='$uid'";
		$db2 = new DB_CONNECT();
		$db2->connect();
		$con2 = $db2->myconn;
		$user_result = mysqli_query($con2, $user_query);
		$row_user = mysqli_fetch_assoc($user_result);
		$entry['username'] = $row_user['username'];
		$entry['email'] = $row_user['email'];
		$entry['phone'] = $row_user['phone'];
		$entry['name'] = $row_user['firstname']." ".$row_user['lastname'];
		$entry['image'] = file_get_contents('images/'.$entry['username'].'.txt');
		$entry['departure_city'] = $row["departure_city"];
		$entry['arrival_city'] = $row["arrival_city"];
		$departure_datetime = new DateTime($row["departure_time"]);
		$entry['departure_date'] = $departure_datetime->format('d-m-Y');
		$entry['departure_time'] = $departure_datetime->format('H:i:s');
		$arrival_datetime = new DateTime($row["arrival_time"]);
		$entry['arrival_date'] = $arrival_datetime->format('d-m-Y');
		$entry['arrival_time'] = $arrival_datetime->format('H:i:s');
		$entry['departure_location'] = $row["departure_location"];
		$entry['arrival_location'] = $row["arrival_location"];
		if((new DateTime($date))->format('d-m-Y')==$departure_datetime->format('d-m-Y'))
			$response[] = $entry;
	}
	echo json_encode($response);

}

?>