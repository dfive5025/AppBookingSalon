<?php

require_once '../dbconect/DbOperations.php';

// Đọc dữ liệu JSON từ ứng dụng Android
$inputJSON = file_get_contents('php://input');
$inputData = json_decode($inputJSON);

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Kiểm tra xem các trường dữ liệu đã được gửi từ Android hay chưa
    if (isset($inputData->name) &&
        isset($inputData->email) &&
        isset($inputData->address) &&
        isset($inputData->idadmin) &&
        isset($inputData->mobile) &&
        isset($inputData->password)) {

        // Tiến hành xử lý dữ liệu
        $db = new DbOperations();
        $result = $db->createusersalon(
            $inputData->name,
            $inputData->email,
            $inputData->address,
            $inputData->idadmin,
            $inputData->mobile,
            $inputData->password
        );

        if ($result == 1) {
            $response['error'] = false;
            $response['message'] = "successfully";
        } elseif ($result == 2) {
            $response['error'] = true;
            $response['message'] = "Some error occurred please try again";
        } elseif ($result == 0) {
            $response['error'] = true;
            $response['message'] = "This user is existed, please try again";
        }
    } else {
        $response['error'] = true;
        $response['message'] = "Required fields are missing";
    }
} else {
    $response['error'] = true;
    $response['message'] = "Invalid Request";
}

// Trả về dữ liệu JSON kết quả cho ứng dụng Android
echo json_encode($response);
?>