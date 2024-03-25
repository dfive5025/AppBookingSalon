
<?php 

require_once '../dbconect/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['IDlogevent']))
		{
		//operate the data further 

		$db = new DbOperations(); 

	$result = $db->UpdateLogEvent($_POST['IDlogevent']);
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