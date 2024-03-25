
<?php 

require_once '../dbconect/DbOperations.php';

$response =array(); 

$listrequest = null;

if($_SERVER['REQUEST_METHOD']=='POST')
{
if(isset($_POST["idorder"])){
    $db = new DbOperations(); 

    $listrequest = $db->getrequestbyidorder($_POST["idorder"]);
    if($listrequest !== false){
        // $response['error']=false;
        // $response['message']="Successfully";
        $response['request']=$listrequest; // Thêm thông tin người dùng vào trong phản hồi
    }
    else{
        // $response['error']=true;
        // $response['message']="Some error occurred";
    }

}
}
else{
    // $response['error']=true;
    // $response['message']="Invalid Request";
}


// echo json_encode($response);
echo json_encode($listrequest);
?>