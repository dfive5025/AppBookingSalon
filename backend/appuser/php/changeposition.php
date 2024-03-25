<?php 
	define('DB_NAME','salonmanager');
	define('DB_USER','root');
	define('DB_PASSWORD','');
	define('DB_HOST','localhost');

$conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD,DB_NAME);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$response = array(); 
$id=1;
$positionX = $_POST['position_x'];
$positionY = $_POST['position_y'];

$sql = "UPDATE position SET x='$positionX', y='$positionY' WHERE id='$id'";

if ($conn->query($sql) === TRUE) {
    echo "Data saved successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();