<?php

error_reporting(E_ERROR | E_PARSE);
header('Content-type:application/json; charset=utf-8');

$response = array();

if (!isset($_GET['name'])) {
    $response["success"] = 0;
    $response["message"] = "ส่งพารามิเตอร์มาไม่ครบ";
    echo json_encode($response);
    exit();
}

require_once 'db_config.php';
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

if (mysqli_connect_errno()) {
    $response["success"] = 0;
    $response["message"] = "การเชื่อมต่อฐานข้อมูลล้มเหลว: " . mysqli_connect_error();
    echo json_encode($response);
    exit();
}

$name = $_GET['name'];

$charset = "SET character_set_results=utf8";
$db->query($charset);
$charset = "SET character set utf8";
$db->query($charset);

$sql = "INSERT INTO food(`name`) VALUES ('$name')";

if ($sql = $db->query($insertSql)) {
    $response["success"] = 1;
    $response["message"] = "เพิ่มข้อมูลอาหารใหม่เรียบร้อย";
} else {
    $response["success"] = 0;
    $response["message"] = "เกิดข้อผิดพลาดในการเพิ่มข้อมูลอาหารใหม่: $sql";
}

$db->close();
echo json_encode($response);

?>
