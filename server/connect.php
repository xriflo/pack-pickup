<?php
require_once __DIR__ . '/config.php';

class DB_CONNECT  {
    var $myconn;
    var $response = array();
    function connect() {
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
        if (!$con) {
            $this->response["success"] = 0;
            $this->response["message"] = "Could not connect to database!";
        } else {
            $this->myconn = $con;
            $this->response["success"] = 1;
            $this->response["message"] = "Connection established!";
        }
        return $this->myconn;
    }

    function close() {
        mysqli_close($this->myconn);
        $this->response["success"] = 1;
        $this->response["message"] = "Connection closed!";
    }

}
?>