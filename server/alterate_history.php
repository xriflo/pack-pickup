<?php

require_once __DIR__ . '/connect.php';

$response = array();
$db = new DB_CONNECT();
$db->connect();
$con = $db->connect();

$v = explode(")", "  (hs)");
$v = explode("(", $v[0]);
print_r($v[1]);
//echo "1";

if(!empty($_POST['username']) && !empty($_POST['username_signed'])) {
	 $username = $_POST['username'];
	 $username_signed = $_POST['username_signed'];

	$username = explode(")", $username);
	$username = explode("(", $username[0]);

	$result = mysqli_query($con, "SELECT uid, history FROM users WHERE username = '$username[1]';");
	$var = "";
	$var2 = 0;
	if ($result) {
	    $row = mysqli_fetch_assoc($result);
	    
	    if(is_null($row['history'])) {
	    	$var = $username_signed;
	    } else {
		    $f = explode(",", $row['history']);
		    $flag = 0;
		    for ($i = 0; $i < sizeof($f); $i++) {
		    	if(strcmp($f[$i], $username_signed) == 0) {
		    		$flag = 1;
		    		break;
		    	}
		    }
		    if($flag == 0) {
		 	   $row['history'] = $row['history'] . "," . $username_signed;
		    }
	 	   	$var = $row['history']; 	   
		}

		$var2 = $row['uid'];
		
	} else {
		$response["success"] = 0;
        $response["message"] = "This user does not exist.";
	    echo json_encode($response);
	}
	
	$result = mysqli_query($con, "UPDATE users SET history = '$var' WHERE uid = '$var2';");
		
	if ($result) {
        $response["success"] = 1;
        $response["message"] = "History successfully modified.";
        
    } else {
        // failed to insert row
        $response["failure"] = 0;
        $response["message"] = "Oops! An error occurred.";
    }

    echo json_encode($response);
} else {
	$response["success"] = 0;
	$response["message"] = "Ceplmzd.";
	echo json_encode($_POST);
}

?>