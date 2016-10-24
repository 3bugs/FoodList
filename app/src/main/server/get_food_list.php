<?php

error_reporting(E_ERROR | E_PARSE);
header('Content-type:application/json; charset=utf-8');

$response = array();

require_once 'db_config.php';
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

if (mysqli_connect_errno()) {
    $response["success"] = 0;
    $response["message"] = "การเชื่อมต่อฐานข้อมูลล้มเหลว: " . mysqli_connect_error();
    echo json_encode($response);
    exit();
}

$charset = "SET character_set_results=utf8";
$db->query($charset);
$charset = "SET character set utf8";
$db->query($charset);

$sql = "SELECT * FROM food";

if ($result = $db->query($sql)) {
    $response["success"] = 1;
    $response["food_data"] = array();

    $rowCount = $result->num_rows;

    if ($rowCount > 0) {
        while ($row = $result->fetch_assoc()) {
            $food = array();
            $food["_id"] = (int) $row["_id"];
            $food["name"] = $row["name"];
            $food["image"] = $row["image"];

            array_push($response["food_data"], $food);
        }
    }
    $result->close();
} else {
    $response["success"] = 0;
    $response["message"] = "เกิดข้อผิดพลาดในการเข้าถึงข้อมูล";
}

$db->close();
echo json_encode($response);

?>
