
<?php 

require_once '../dbconect/DbOperations.php';

$response =array(); 

$listrequest ;

if($_SERVER['REQUEST_METHOD']=='POST')
{
if(isset($_POST["date"])){
	$db = new DbOperations(); 

	$listrequest = $db->getrequestbydate($_POST["date"]);
	if($listrequest !== false){
        $response['error']=false;
        $response['message']="Successfully";
        $response['request']=$listrequest; // Thêm thông tin người dùng vào trong phản hồi
    }
    else{
        $response['error']=true;
        $response['message']="Some error occurred";
    }

}
}
else{
    $response['error']=true;
    $response['message']="Invalid Request";
}


echo json_encode($listrequest);
?>