
<?php 

require_once '../dbconect/DbOperations.php';
require_once '../dbconect/checkToken.php';
$response = array(); 
if (isTokenValid()==true) {
if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['idrequest']) and 
			isset($_POST['status']) and 
			isset($_POST['note'])
				)
		{
		//operate the data further 

		$db = new DbOperations(); 
		$idrq = $_POST['idrequest'];
		$stt = $_POST['status'];
		$note = $_POST['note'];
		$role = $_POST['role'];
		$idcus = $_POST['idcus'];
		$result = $db->updaterequest1($_POST['idrequest'],$_POST['status'],$_POST['note']);
		if($result >0 ){
			$response['error'] = false; 
			$response['message'] = "successfully";

			$idsl = $db->getidsaloonOfRequest($idrq);
			$userInfo = $db->getMobileEmailByIdorder($idrq);
			$thoiGianHienTai = time();
			$gioPhut = date("H:i", $thoiGianHienTai); // Format giờ:phút
			$ngayThangNam = date("d/m/Y", $thoiGianHienTai); // Format ngày/tháng/năm
			$db->createLogEvent( "Cập nhật trạng thái lịch hẹn có ID:".$idrq, "Update status: ".$stt.", note: ".$note.",địa chỉ IP: " .$_SERVER['REMOTE_ADDR'], 
				$idsl, "4", $idrq,$idcus, "Customer: ".$userInfo['mobile'], $gioPhut." ".$ngayThangNam);
		}else{
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
}else {
    // Token is invalid, return a 401 Unauthorized response
	http_response_code(401);
	$response['error'] = true;
	$response['message'] = "Unauthorized access";
	echo json_encode($response);
}
?>