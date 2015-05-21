<?php
 
require_once __DIR__ . '/connect.php';

$response = array();
$db = new DB_CONNECT();
$db->connect();
$con = $db->myconn;

if(!empty($_POST['departure_city']) && !empty($_POST['departure_location']) && !empty($_POST['departure_time']) && 
	!empty($_POST['departure_time']) && !empty($_POST['departure_hour'])  && !empty($_POST['arrival_city']) && 
	!empty($_POST['arrival_location']) && !empty($_POST['arrival_time']) && !empty($_POST['arrival_hour']) && !empty($_POST['username'])) {

	$departure_city = $_POST['departure_city'];
	$departure_location = $_POST['departure_location'];
	$departure_time = $_POST['departure_time'];
	$departure_hour = $_POST['departure_hour'];
	$departure_time = date("Y-m-d", strtotime($departure_time));
	$departure_datetime = date('Y-m-d H:i:s', strtotime("$departure_time $departure_hour"));
	

	$arrival_city = $_POST['arrival_city'];
	$arrival_location = $_POST['arrival_location'];
	$arrival_time = $_POST['arrival_time'];
	$arrival_hour = $_POST['arrival_hour'];
	$arrival_datetime = date('Y-m-d H:i:s', strtotime("$arrival_time $arrival_hour"));
	

	
	$username = $_POST['username'];

	$user_query = "SELECT * FROM users WHERE username='$username'";
	$db2 = new DB_CONNECT();
	$db2->connect();
	$con2 = $db2->myconn;
	$user_result = mysqli_query($con2, $user_query);
	$row_user = mysqli_fetch_assoc($user_result);
	$uid = $row_user['uid'];

	$result = mysqli_query($con, "INSERT INTO races (uid, departure_city, arrival_city, departure_time, arrival_time, departure_location, arrival_location) 
								VALUES ('$uid', '$departure_city', '$arrival_city', '$departure_datetime', '$arrival_datetime', '$departure_location', '$arrival_location')");
	 if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Race successfully added.";
        
    } else {
        // failed to insert row
        $response["failure"] = 0;
        $response["message"] = "Oops! An error occurred.";
    }

}
else {
    // required field is missing
    $response["failure"] = 1;
    $response["message"] = "Required field(s) missing";		    
}
echo json_encode($response);

?>