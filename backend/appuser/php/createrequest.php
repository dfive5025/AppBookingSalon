
<?php 

require_once '../dbconect/DbOperations.php';
require_once '../dbconect/checkToken.php';
$response = array(); 
if (isTokenValid()==true) {
if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['name']) and 
			isset($_POST['email']) and 
				isset($_POST['address'])and 
				isset($_POST['mobile'])
                and 
				isset($_POST['servicelist'])
                and 
				isset($_POST['staffcuthairlist'])
                and 
				isset($_POST['idsalon']))
		{
		//operate the data further 

		$db = new DbOperations(); 
		$idsalon = $_POST['idsalon'];
		$mobile = $_POST['mobile'];
		$email  = $_POST['email'];
		$ID  = $_POST['iduser'];
		$result = $db->createrequest( $_POST['name'],$_POST['email'],$_POST['address'],$_POST['mobile'],$_POST['order_method'],$_POST['note'],$_POST['servicelist'],$_POST['timebegin'],$_POST['timefinish'],$_POST['date'],$_POST['staffcuthairlist'],$_POST['idsalon'],$_POST['status'],$_POST['payment'],$_POST['amount']
									
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "successfully";

				$thoiGianHienTai = time();
				$gioPhut = date("H:i", $thoiGianHienTai); // Format giờ:phút
				$ngayThangNam = date("d/m/Y", $thoiGianHienTai); // Format ngày/tháng/năm
				$lastInsertedId = $db->getMaxid();
				$result1 = $db->createLogEvent("Creat new order #".$lastInsertedId, "iduser: #".$ID.", Mobile: ".$mobile.", Email: ".$email.", IP:".$_SERVER['REMOTE_ADDR'], $idsalon, 1, $lastInsertedId,$ID , "Customer: ".$mobile, $gioPhut." ".$ngayThangNam);
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "This service is existed , please try again";						
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