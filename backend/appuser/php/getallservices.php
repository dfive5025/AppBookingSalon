
<?php 

require_once '../dbconect/DbOperations.php';

$response =array(); 

$listservice ;

if($_SERVER['REQUEST_METHOD']=='POST')
{
if(isset($_POST["idsalon"])){
	$db = new DbOperations(); 

	$listservice = $db->getservicebysalonid($_POST["idsalon"]);
	if($listservice !== false){
        $response['error']=false;
        $response['message']="Successfully";
        $response['services']=$listservice; // Thêm thông tin người dùng vào trong phản hồi
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


echo json_encode($listservice);
?>