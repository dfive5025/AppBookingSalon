<?php 

require_once '../dbconect/DbOperations.php';
require_once '../dbconect/jwt.php';
$response = array(); 
$salon = array();
$token = array();
// 12345678@Argerg
if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['email']) and isset($_POST['password'])){
		$db = new DbOperations(); 
		$email = $_POST['email'];
		if($db->userLogin($_POST['email'], $_POST['password'])){
			$salon = $db->getidadminbyemailusername($_POST['email']);
			$response['error'] = false; 
			$response['data']=$salon;
			$response['message'] = "Login success";


			$token["role"] = "user";
			$token["email"] = $email;
			$jsonwebtoken = JWT::encode($token, "keybiamt");
			$response['token'] 	= $jsonwebtoken;
			$salon['token'] = $jsonwebtoken;
			$response['data']=$salon;
		}else{
			$response['error'] = true; 
			$response['message'] = "Invalid username or password";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);
?>