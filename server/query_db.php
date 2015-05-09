<?php

include 'connect.php';

$connection = new createCon();
$connection->connect();

$query = "SELECT * FROM users";
$result = mysqli_query($connection->myconn, $query);

if($numrows = mysqli_num_rows($result)) {
        echo $numrows."\r\n";
    while ($row = mysqli_fetch_assoc($result)) {
        $dbusername = $row['user'];
        $dbpassword = $row['phone'];
        echo $dbusername.PHP_EOL;
        echo $dbpassword.PHP_EOL;
    }}
?>