<?php 
function isTokenValid() {
    $token = 'token';
    $check = false;
	$headers = getallheaders();
  	if (isset($headers['Authorization'])) {
    $authorizationHeader = $headers['Authorization'];

    // Kiểm tra xem tiêu đề có bắt đầu bằng "Bearer " không
    if (strpos($authorizationHeader, 'Bearer ') === 0) {
        // Lấy token từ phần đuôi "Bearer "
        $bearerToken = substr($authorizationHeader, 7);
        $token = $bearerToken;
        $check = true;
    } else {
        // Nếu không bắt đầu bằng "Bearer ", có thể đây không phải là token Bearer
        $check = false;
    	}
	} else {
    // Nếu không có tiêu đề Authorization, có thể không có token
   	$check = false;
	}
	//echo $token;
    require("jwt.php");

  
        $json = JWT::decode($token, "keybiamt", true);
  		
        if ($json == '1' || $json == '2' ||$json == '3' || $check == false) {
            return false;
        } else {
            return true;
        }

}
?>
	
