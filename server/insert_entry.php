<?php
    require_once __DIR__ . '/connect.php';
    $response = array();

    // check for required fields
    if (!(isset($_GET['user']) && isset($_GET['email']) && 
        isset($_GET['firstname'])&& isset($_GET['lastname']) && 
        isset($_GET['phone']))) {

        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing";
        $con->close();
        exit(json_encode($response));
    }
     
    $user = $_GET['user'];
    $email = $_GET['email'];
    $firstname = $_GET['firstname'];
    $lastname = $_GET['lastname'];
    $phone = $_GET['phone'];


    // connecting to db
    $con = new DB_CONNECT();
    $con->connect();

    echo(json_encode($con->response));

    //check if the user already exists
    $sql = "SELECT * FROM users WHERE user='$user' OR email='$email'";
    $result = mysqli_query($con->myconn,$sql);
    $numrows = mysqli_num_rows($result);

    if($numrows) {
        $response["success"] = 0;
        $response["message"] = "The user '$user' or the email '$email' already exists in our database";
        $con->close();
        exit(json_encode($response));
    }

    //insert the user in database and check if everything was ok
    $sql = "INSERT INTO users(user, email, firstname, lastname, phone) VALUES('$user', '$email', '$firstname', '$lastname', '$phone')";
    $result = mysqli_query($con->myconn,$sql);

    if(!$result) {
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        $con->close();
        exit(json_encode($response));
    }

    //exit with success message
    $response["success"] = 1;
    $response["message"] = "Account successfully created.";
    $con->close();
    exit(json_encode($response));

?>