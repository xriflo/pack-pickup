<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
define('DB_USER', "user"); // db user
define('DB_PASSWORD', "Patqv26KdUUVhmXM"); // db password (mention your db password here)
define('DB_DATABASE', "users"); // database name
define('DB_SERVER', "localhost"); // db server

class DB_CONNECT {
 
    // constructor
    function __construct() {
        // connecting to database
        $this->connect();
    }
 
    // destructor
    function __destruct() {
        // closing db connection
        $this->close();
    }
 
    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        //require_once __DIR__ . '/db_config.php';
 
        // Connecting to mysql database
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
 
        // Selecing database
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
 
        // returing connection cursor
        return $con;
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysql_close();
    }
 
}

// array for JSON response
$response = array();
// check for required fields
if(!empty($_POST['logging'])) {
	if(strcmp($_POST['logging'], "signup") == 0) {
		if (!empty($_POST['username']) && !empty(urldecode($_POST['email'])) && !empty($_POST['password']) && !empty($_POST['location'])) {
		    $username = $_POST['username'];
		    $email = $_POST['email'];
		    $password = $_POST['password'];
		    $location = $_POST['location'];
		    
		    // connecting to db
		    $db = new DB_CONNECT();
		 
		    $result = mysql_query("SELECT COUNT(*) as is_present FROM users WHERE (username = '$username' OR email = '$username') AND password = '$password'");
		 	$row = mysql_fetch_assoc($result);
		    if($row['is_present'] == 0) {

			    // mysql inserting a new row
			    $result = mysql_query("INSERT INTO users (username, email, password, location) 
			                            VALUES('$username', '$email', '$password', '$location')");
			 
			 	$row = mysql_fetch_assoc($result);
			    // check if row inserted or not
			    if ($result) {
			        // successfully inserted into database
			        $response["success"] = 1;
			        $response["message"] = "User successfully added.";
			 
			        // echoing JSON response
			        echo json_encode($response);
			    } else {
			        // failed to insert row
			        $response["failure"] = 0;
			        $response["message"] = "Oops! An error occurred.";
			 
			        // echoing JSON response
			        echo json_encode($response);
			    }
		    } else {
		    	$response["failure"] = 0;
			    $response["message"] = "User already exists. Please log in!";
				echo json_encode($response);
		    }
		} else {
		    // required field is missing
		    $response["success"] = 0;
		    $response["message"] = "Required field(s) missing";
		    echo json_encode($_POST);
		    // echoing JSON response
		    echo json_encode($response);
		}
	} else if(strcmp($_POST['logging'], "signin") == 0) {
		if (!empty($_POST['username']) && !empty($_POST['password'])) {
			$username = $_POST['username'];
	   		$password = $_POST['password'];

	   		$db = new DB_CONNECT();
	   		$result = mysql_query("SELECT COUNT(*) as is_present FROM users WHERE (username = '$username' OR email = '$username') AND password = '$password'");
		 	$row = mysql_fetch_assoc($result);

		 	
		    $response["success"] = 0;
		    $response["message"] = "Ma nigga";
			echo json_encode($response);
		}
	}
}

?>