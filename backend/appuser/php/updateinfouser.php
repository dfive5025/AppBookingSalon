
<?php 

require_once '../dbconect/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['iduser']) and 
			isset($_POST['name']) and 
				isset($_POST['email'])and 
				isset($_POST['address'])and 
				isset($_POST['idadmin'])and 
				isset($_POST['mobile']))
		{
		//operate the data further 

		$db = new DbOperations(); 

	$result = $db->updateuser($_POST['iduser'],$_POST['name'],$_POST['email'],$_POST['address'],$_POST['idadmin'],$_POST['mobile']);
		if($result >0 ){
			$response['error'] = false; 
			$response['message'] = "successfully";
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
?>