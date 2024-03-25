<?php 

require_once '../dbconect/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['iduser']) and 
			isset($_POST['password']) and 
				isset($_POST['newpassword'])
				)
		{
		//operate the data further 

		$db = new DbOperations(); 

	$result = $db->changepassworduser($_POST['iduser'],$_POST['password'],$_POST['newpassword']);
		if($result == 0 ){
			$response['error'] = false; 
			$response['message'] = "successfully";
		}else if($result == 1 ){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}
        else if ($result == 2 ){
            $response['error'] = true; 
			$response['message'] = "current password is incorrect";	
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
?>