
<?php 

require_once '../dbconect/DbOperations.php';

$response =array(); 

$listuser ;

if($_SERVER['REQUEST_METHOD']=='POST')
{
if(isset($_POST["idsalon"])){
	$db = new DbOperations(); 

	$listuser = $db->getstaffbyidsalon($_POST["idsalon"]);
	if($listuser !== false){
        $response['error']=false;
        $response['message']="Successfully";
        $response['users']=$listuser; // Thêm thông tin người dùng vào trong phản hồi
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


echo json_encode($listuser);
?>