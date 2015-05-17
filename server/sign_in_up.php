<?php
 
require_once __DIR__ . '/connect.php';

// array for JSON response
$response = array();

// check for required fields
if(!empty($_POST['logging'])) {
	$db2 = new DB_CONNECT();
	$db2->connect();
	$db = $db2->myconn;
	
	if(strcmp($_POST['logging'], "signup") == 0) {
		if (!empty($_POST['username']) && !empty(urldecode($_POST['email'])) && !empty($_POST['password']) && !empty($_POST['location'])) {
		    $username = $_POST['username'];
		    $email = $_POST['email'];
		    $password = $_POST['password'];
		    $location = $_POST['location'];
		    
		    
		    $result = mysqli_query($db, "SELECT COUNT(*) as no FROM users WHERE ((username = '$username' OR email = '$username') AND password = '$password')");
		 	$row = mysqli_fetch_assoc($result);
		 	$image = 'NO';
		 	$image_name='';
		    if($row['no'] == 0) {
		    	if(!empty($_POST['image'])) {
		    		if (!file_exists('images/')) {
   					    mkdir('images/', 0777, true);
   					}
		    		$data = $_POST['image'];
		    		$image_name = 'images/'.$username.'.txt';
					$file = fopen($image_name, 'wb');
					fwrite($file, $data);
    				fclose($file);
					$image = 'YES';

		    	}

			    // mysql inserting a new row
			    if($image=='YES')
			    	$result = mysqli_query($db, "INSERT INTO users (username, email, password, location, photo_name) 
			                            VALUES ('$username', '$email', '$password', '$location', '$image_name')");
			    else
			    	$result = mysqli_query($db, "INSERT INTO users (username, email, password, location) 
			                            VALUES ('$username', '$email', '$password', '$location')");
			 
			 	// check if row inserted or not
			    if ($result) {
			        // successfully inserted into database
			        $response["success"] = 1;
			        $response["message"] = "User successfully added.";
			        
			    } else {
			        // failed to insert row
			        $response["failure"] = 0;
			        $response["message"] = "Oops! An error occurred.";
			    }
		    } else {
		    	$response["failure"] = 0;
			    $response["message"] = "User already exists. Please log in!";
		    }
		} else {
		    // required field is missing
		    $response["failure"] = 1;
		    $response["message"] = "Required field(s) missing";		    
		}
	} else if(strcmp($_POST['logging'], "signin") == 0) {
		if (!empty($_POST['username']) && !empty($_POST['password'])) {
			$username = $_POST['username'];
	   		$password = $_POST['password'];

	   		$result = mysqli_query($db, "SELECT COUNT(*) as no FROM users WHERE ((username = '$username' OR email = '$username') AND password = '$password')");
		 	$row = mysqli_fetch_assoc($result);

		 	if($row['no'] != 0) {
		 		$response["success"] = 1;
		    	$response["message"] = "Successfully logged in";
		 	} else {
			    $response["failure"] = 0;
			    $response["message"] = "This account does not exist";
		 	}

		}
	}
	mysqli_close($db);
	echo json_encode($response);
}

?>