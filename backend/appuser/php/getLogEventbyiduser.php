
<?php 

require_once '../dbconect/DbOperations.php';
require_once '../dbconect/checkToken.php';
$response =array(); 

$listrequest = null;
if (isTokenValid()==true) {
    if($_SERVER['REQUEST_METHOD']=='POST')
    {
        if(isset($_POST["iduser"])){
           $db = new DbOperations(); 

           $listrequest = $db->getLogeventbyiduser($_POST["iduser"], "1");
           if($listrequest !== false){
        // $response['error']=false;
        // $response['message']="Successfully";
        $response['LogEventModel']=$listrequest; // Thêm thông tin người dùng vào trong phản hồi
        echo json_encode($listrequest);
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

}else {
    // Token is invalid, return a 401 Unauthorized response
    http_response_code(401);
    $response['error'] = true;
    $response['message'] = "Unauthorized access";
    echo json_encode($response);
}
?>