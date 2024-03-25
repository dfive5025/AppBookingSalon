<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */
		public function generateRandomToken($length) {
    		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    		$randomString = '';

    		for ($i = 0; $i < $length; $i++) {
       		 $randomString .= $characters[rand(0, strlen($characters) - 1)];
    			}

    		return $randomString;
		}

		public function createLogEvent($name_action,$description,$id_saloon,$type_logevent,$id_order,$idcustomer, $creator, $creatAt){
				$stmt = $this->con->prepare("INSERT INTO `logevent` (`name_action`, `description`, `id_saloon`, `type_logevent`, `id_order`,`idCustomer`, `creator`, `creatAt`) VALUES ( ?, ?, ?, ?,?, ?,?,?);");
				$stmt->bind_param("ssssssss",$name_action,$description,$id_saloon,$type_logevent,$id_order,$idcustomer, $creator, $creatAt);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}
			public function getMobileEmailByIdorder($id_order) {
    		$stmt = $this->con->prepare("SELECT email, mobile FROM request WHERE id_order = ?");
    		$stmt->bind_param("i", $id_order); // Giả sử iduser là kiểu integer

    		$stmt->execute();

    		if ($stmt->error) {
       		 // Xử lý lỗi
    		die('Query error: ' . $stmt->error);
    		}

    		$stmt->bind_result($email, $mobile);
    		$stmt->fetch();

    		$stmt->close();

    		// Trả về một mảng chứa email và mobile
    		return array("email" => $email, "mobile" => $mobile);
}
		public function getLogeventbyiduser($id, $type_logevent){
			$stmt = $this->con->prepare("SELECT * FROM `logevent` WHERE idCustomer = ? AND (type_logevent = 1 OR type_logevent = 4)");
			$stmt->bind_param("i",$id);
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
			while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}
				return $request;
		}
		public function UpdateLogEvent($id){
				$stmt = $this->con->prepare("UPDATE logevent SET CustomerMarkAsRead=0 WHERE id=?");
			 $stmt->bind_param("s", $id);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			// }
		}
		public function getMaxid() {
    		$stmt = $this->con->prepare("SELECT Max(id_order) FROM request");
    		$stmt->execute();
    		$stmt->bind_result($lastInsertedId);
			$stmt->fetch();
    		return $lastInsertedId;
		}
		public function getrequestbyidorder($id){
			$stmt = $this->con->prepare("SELECT * FROM `request` WHERE `id_order` LIKE ?");
			$stmt->bind_param("i",$id);
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}


				return $request;
			
		}
		public function createUser($username, $pass, $email, $firstname, $lastName, $mobile, $state, $city, $area){
			if($this->isUserExist($username,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO adminsaloons (`id_adminSaloons`, `username`, `password`, `email`, `firstname`, `lastName`, `mobile`, `state`, `city`, `area`) VALUES (NULL, ?, ?, ?,?,?,?,?,?,?);");
				$stmt->bind_param("sssssssss",$username,$password,$email,$firstname,$lastName,$mobile,$state,$city,$area);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function createAccountSalon($name, $address, $mobileNo, $workingHr, $area, $email, $pass, $image, $id_AdminSaloons){
			if($this->isAccountSalonExist($mobileNo,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO saloon (`id_saloon`, `name`, `address`, `mobileNo`, `workingHr`, `area`, `email`, `password`, `image`,`id_AdminSaloons`) VALUES (NULL, ?, ?, ?,?,?,?,?,?,?);");
				$stmt->bind_param("sssssssss",$name,$address,$mobileNo,$workingHr,$area,$email,$password,$image,$id_AdminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function createWorkStaff($name, $address, $mobile, $email, $pass, $experience, $work, $salary,$image , $idsaloon, $id_adminSaloons){
			if($this->isWorkStaffExist($mobile,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO workstaff (`idstaff`, `name`, `address`, `mobile`, `email`, `password`, `experience`, `work`,`salary`, `image`,`idsaloon`, `id_adminSaloons`) VALUES (NULL, ?, ?, ?,?,?,?,?,?,?,?,?);");
				$stmt->bind_param("sssssssssss",$name,$address,$mobile,$email,$password,$experience,$work,$salary,$image,$idsaloon,$id_adminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}
		public function updateInfoAdmin($id_adminSaloons, $firstname, $mobile, $area){
			// if($this->isUserExist1($mobile,$email)){
			// 	return 0; 
			// }else{
				$stmt = $this->con->prepare("UPDATE adminsaloons SET firstname=?, mobile=?, area=? WHERE id_adminSaloons=?");
			 $stmt->bind_param("sssi", $firstname, $mobile, $area, $id_adminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			// }
		}
		public function updateInfoAdmin123($id_adminSaloons,$firstname,$lastname, $mobile,$state,$city, $area,$username,$email,$password){
			// if($this->isUserExist1($mobile,$email)){
			// 	return 0; 
			// }else{
				$pass = md5($password);
				$stmt = $this->con->prepare("UPDATE adminsaloons SET firstname=?,lastName=?,mobile=?,state=?,city=?, area=?,userName=?,email=? WHERE id_adminSaloons=?");
			 $stmt->bind_param("ssssssssi", $firstname,$lastname, $mobile,$state,$city, $area,$username,$email, $id_adminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			// }
		}



		public function resetpasswordadmin($id_adminSaloons){
			// if($this->isUserExist1($mobile,$email)){
			// 	return 0; 
			// }else{
				$password="123456789";
				$pass = md5($password);
				$stmt = $this->con->prepare("UPDATE adminsaloons SET password=? WHERE id_adminSaloons=?");
			 $stmt->bind_param("si", $pass, $id_adminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			// }
		}

		public function ChangPassWordAdmin($id_adminSaloons,$oldpassword, $password){
			$passmd5 = md5($password);
			if($this->isOldPassword($oldpassword,$id_adminSaloons)){
				return 0; 
			}else{
				$stmt = $this->con->prepare("UPDATE adminsaloons SET password=? WHERE id_adminSaloons=?");
			 $stmt->bind_param("ss", $passmd5, $id_adminSaloons);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function ChangImageSalon($id_saloon,$image){
			// $passmd5 = md5($password);
			// if($this->isOldPassword($image,$id_adminSaloons)){
			// 	return 0; 
			// }else{
				$stmt = $this->con->prepare("UPDATE saloon SET image=? WHERE id_saloon=?");
			 $stmt->bind_param("ss", $image, $id_saloon);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			// }
		}
		public function updateInfoSalon($id_saloon, $name, $address, $mobileNo, $workingHr, $area, $email, $pass) {
			// if($this->isAccountSalonExist($mobileNo,$email)){
			// 	return 0; 
			// }else{
    		$password = md5($pass);
   			$stmt = $this->con->prepare("UPDATE saloon SET name=?, address=?, mobileNo=?, workingHr=?, area=?, email=?, password=? WHERE id_saloon=?");
		    $stmt->bind_param("ssssssss", $name, $address, $mobileNo, $workingHr, $area, $email, $password, $id_saloon);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    }
		// }
		}
		public function updateInfoWorkStaff($idstaff, $name, $address, $mobile, $pass, $experience, $salary, $idsaloon, $work) {
			// if($this->isWorkStaffExist($mobile,$email)){
			// 	return 0; 
			// }else{
    		$password = md5($pass);
   			$stmt = $this->con->prepare("UPDATE workstaff SET name=?, address=?, mobile=?, password=?, experience=?, salary=?, idsaloon=?, work=? WHERE idstaff=?");
		    $stmt->bind_param("sssssissi", $name, $address, $mobile, $password, $experience, $salary, $idsaloon, $work, $idstaff);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    	}
			// }
		}
			public function deleteSaloon($id_saloon) {
		    if($this->isAccountSalonExistByID($id_saloon)){
		    $stmt = $this->con->prepare("DELETE FROM saloon WHERE id_saloon = ?");
		    $stmt->bind_param("i", $id_saloon);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    	}
			}else{
				return 3;
			}
		}
		public function deleteadminsalon($id_adminSaloons) {
		    
		    $stmt = $this->con->prepare("DELETE FROM adminsaloons WHERE id_adminSaloons = ?");
		    $stmt->bind_param("i", $id_adminSaloons);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    	}
			
		}
		public function deleteWorkStaff($idstaff) {
		    if($this->isWorkStaffExistByID($idstaff)){
		    $stmt = $this->con->prepare("DELETE FROM workstaff WHERE idstaff = ?");
		    $stmt->bind_param("i", $idstaff);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    	}
			}else{
				return 3;
			}
		}

		public function deleteRequestByID($id_order) {
		    if($this->isRequestExistByID($id_order)){
		    $stmt = $this->con->prepare("DELETE FROM request WHERE id_order = ?");
		    $stmt->bind_param("i", $id_order);
		    if ($stmt->execute()) {
		        return 1;
		    } else {
		        return 2;
		    	}
			}else{
				return 3;
			}
		}
		
		public function userLogin($email, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id_admin FROM users WHERE email = ? AND password = ?");
			$stmt->bind_param("ss",$email,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function superadminlogin($email, $password){
			
			$stmt = $this->con->prepare("SELECT account FROM superadmin WHERE account = ? AND password = ?");
			$stmt->bind_param("ss",$email,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function getidadminbyemailusername($email){
			$stmt = $this->con->prepare("SELECT id_user  FROM users WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		public function salonLogin($email, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id_saloon FROM saloon WHERE email = ? AND password = ?");
			$stmt->bind_param("ss",$email,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function getAllListAdmin() {
			$stmt = $this->con->prepare("SELECT * FROM adminsaloons ");
			// $stmt->bind_param("i", $idAdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
			$stmt->execute();
			return $stmt->get_result();
			}
			public function getAllListContact() {
				$stmt = $this->con->prepare("SELECT * FROM contact ");
				// $stmt->bind_param("i", $idAdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
				$stmt->execute();
				return $stmt->get_result();
				}
				public function updateposition() {
					$stmt = $this->con->prepare("UPDATE workstaff SET x=?, y=? WHERE id=?");
					// $stmt->bind_param("i", $idAdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					return $stmt->get_result();
					}

		public function getUserByUsername($email){
			$stmt = $this->con->prepare("SELECT * FROM adminsaloons WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		public function getSaloonByIdAdminSaloons($idAdminSaloons) {
    		$stmt = $this->con->prepare("SELECT * FROM saloon WHERE id_AdminSaloons = ?");
    		$stmt->bind_param("i", $idAdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}

			public function getSaloonByIdsalon($id) {
    		$stmt = $this->con->prepare("SELECT * FROM saloon WHERE id_saloon  = ?");
    		$stmt->bind_param("i", $id); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}
		public function getAllListSaloon() {
    		$stmt = $this->con->prepare("SELECT * FROM saloon ");
    		// $stmt->bind_param("i", $idAdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}
		public function getInfoSaloon($id) {
    		$stmt = $this->con->prepare("SELECT * FROM saloon WHERE id_saloon=?");
    		$stmt->bind_param("i", $id); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}
public function getInfoUser($id) {
    		$stmt = $this->con->prepare("SELECT * FROM users WHERE id_user=?");
    		$stmt->bind_param("i", $id); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}

		public function getSaloonByIdAdminSaloons2($id_AdminSaloons) {
    		$stmt = $this->con->prepare("SELECT * FROM saloon WHERE id_AdminSaloons = ?");
    		$stmt->bind_param("i", $id_AdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}

		
		public function getListWorkStaffByIDSaloon() {
    		$stmt = $this->con->prepare("SELECT * FROM workstaff ");
    		// $stmt->bind_param("s", $idsaloon); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}

		public function getInfoadmin() {
    		$stmt = $this->con->prepare("SELECT * FROM adminsaloons");
    		// $stmt->bind_param("i", $id); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    		$stmt->execute();
    		return $stmt->get_result();
			}

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT id_adminSaloons FROM adminsaloons WHERE username = ? OR email = ?");
			$stmt->bind_param("ss", $username, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		private function isAccountSalonExist($mobileNo, $email){
			$stmt = $this->con->prepare("SELECT id_saloon FROM saloon WHERE mobileNo = ? OR email = ?");
			$stmt->bind_param("ss", $mobileNo, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		private function isAccountSalonExistByID($id_saloon){
			$stmt = $this->con->prepare("SELECT id_saloon FROM saloon WHERE id_saloon = ?");
			$stmt->bind_param("s", $id_saloon);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		private function isWorkStaffExist($mobile, $email){
			$stmt = $this->con->prepare("SELECT idstaff FROM workstaff WHERE mobile = ? OR email = ?");
			$stmt->bind_param("ss", $mobile, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		private function isWorkStaffExistByID($idstaff){
			$stmt = $this->con->prepare("SELECT idstaff FROM workstaff WHERE idstaff = ?");
			$stmt->bind_param("s", $idstaff);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		private function isRequestExistByID($id_order){
			$stmt = $this->con->prepare("SELECT id_order FROM request WHERE id_order = ?");
			$stmt->bind_param("s", $id_order);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		private function isOldPassword($pass,$id_adminSaloons){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id_adminSaloons FROM adminsaloons WHERE password = ? AND id_adminSaloons = ?");
			$stmt->bind_param("si", $password, $id_adminSaloons);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows < 0; 
		}
		

		// them them


		public function createservice($nameservice,$type,$price,$id_saloon){
			if($this->isserviceexist($nameservice)){
				return 0; 
			}else{
				
				$stmt = $this->con->prepare("INSERT INTO services (`idservice`, `nameservice`, `type`, `price`, `id_salon`) VALUES (NULL,?,?,?,?);");
				$stmt->bind_param("ssss",$nameservice,$type,$price,$id_saloon);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}


		public function createcontact($name){
			
				
				$stmt = $this->con->prepare("INSERT INTO contact (`id`, `name`) VALUES (NULL,?);");
				$stmt->bind_param("s",$name);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				
			}
		}

		public function createusersalon($name,$email,$address,$id_admin,$mobile,$pass){
			if($this->checkuserexsit($mobile,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id_user`, `fullname`, `email`, `address`, `id_admin`, `mobile`,`password`) VALUES (NULL, ?, ?, ?, ?, ?,?);");
				$stmt->bind_param("sssiis",$name,$email,$address,$id_admin,$mobile,$password);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function createrequest($name,$email,$address,$mobile,$order_method,$note,$servicelist,$timebegin,$timefinish,$date,$staffcuthairlist,$idsalon,$status,$payment,$amount){
			
				
				$stmt = $this->con->prepare("INSERT INTO `request` (`id_order`, `name`, `email`, `addres`, `mobile`, `order_method`, `note`, `serviceslist`, `time_begin`, `time_finish`, `date`, `staffcuthairList`, `id_saloon`, `status`, `payment`, `amount`) VALUES (NULL, ?,? ,? , ?, ?,? , ?, ?, ?,? ,? ,? , ?,?, ?);");
				$stmt->bind_param("sssssssssssisss",$name,$email,$address,$mobile,$order_method,$note,$servicelist,$timebegin,$timefinish,$date,$staffcuthairlist,$idsalon,$status,$payment,$amount);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			
		}


		public function getrequestbydate($date){
			$stmt = $this->con->prepare("SELECT * FROM `request` WHERE `date` LIKE ?");
			$stmt->bind_param("s",$date);
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}


				return $request;
			
		}
		public function getrequestbyemail($email){
			$stmt = $this->con->prepare("SELECT * FROM `request` WHERE `email` LIKE ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}


				return $request;
			
		}
		public function getrequestbyidsalon($idsalon){
			$stmt = $this->con->prepare("SELECT * FROM `request` WHERE `id_saloon` LIKE ?");
			$stmt->bind_param("s",$idsalon);
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}


				return $request;
			
		}

		public function getrequestall($idsalon){
			$stmt = $this->con->prepare("SELECT * FROM request");
			$stmt->execute();
			$result=$stmt->get_result();
			$request = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$request[] = $row; // thêm dịch vụ vào mảng
				}
				return $request;
			
		}
		public function checkuserexsit($phone,$email){
			$stmt = $this->con->prepare("SELECT * FROM users WHERE mobile = ? OR email = ?;");
			$stmt->bind_param("ss",$phone,$email);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0;
	
			
		}
		public function getSalonbyusername($email){
			$stmt = $this->con->prepare("SELECT * FROM saloon WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}


			public function getservicebysalonid($idsalon) {
				$stmt = $this->con->prepare("SELECT * FROM services WHERE id_salon = ?");
				$stmt->bind_param("i", $idsalon); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
				$stmt->execute();
				$result = $stmt->get_result();
				$services = array(); // khởi tạo mảng rỗng để lưu trữ các dịch vụ
				while ($row = $result->fetch_assoc()) {
					$services[] = $row; // thêm dịch vụ vào mảng
				}


				return $services;
				}

        function getuserbyidadmin($idAdminSaloons){
$stmt=$this->con->prepare("SELECT * FROM `users` WHERE `id_admin` = ?");
$stmt->bind_param("i",$idAdminSaloons);
$stmt->execute();
$result=$stmt->get_result();
while ($row = $result->fetch_assoc()) {
	$users[] = $row; // thêm dịch vụ vào mảng
}
return $users;

		}

		function getstaffbyidsalon($idsalon){
			$stmt=$this->con->prepare("SELECT * FROM `workstaff` WHERE `idsaloon` = ?");
			$stmt->bind_param("i",$idsalon);
			$stmt->execute();
			$result=$stmt->get_result();
			while ($row = $result->fetch_assoc()) {
				$users[] = $row; // thêm dịch vụ vào mảng
			}
			return $users;
			
					}
				public function updateservice($idservice,$nameservice,$price,$type){
					$stmt = $this->con->prepare("UPDATE `services` SET `nameservice` = ?,`price`=?,`type`=? WHERE `services`.`idservice` = ?;");
					$stmt->bind_param("sssi", $nameservice,$price,$type,$idservice); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					return $stmt->affected_rows;
				}

				public function updateuser($iduser,$name,$email,$address,$idadmin,$mobile){
					$stmt = $this->con->prepare("UPDATE `users` SET `fullname` = ?,`email`=?,`address`=?,`id_admin`=?,`mobile`=? WHERE `users`.`id_user` = ?;");
					$stmt->bind_param("sssisi",$name,$email,$address,$idadmin,$mobile,$iduser); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					
					return $stmt->affected_rows;
				}
				public function updaterequest($idrequest,$status){
					$stmt = $this->con->prepare("UPDATE `request` SET `status` = ? WHERE `request`.`id_order` = ?;");
					$stmt->bind_param("ss", $status,$idrequest); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					return $stmt->affected_rows;
				}
				public function updaterequest1($idrequest,$status, $note){
					$stmt = $this->con->prepare("UPDATE `request` SET `status` = ?, `note`=? WHERE `request`.`id_order` = ?;");
					$stmt->bind_param("sss", $status,$note,$idrequest); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					return $stmt->affected_rows;
				}
			public function getidsaloonOfRequest($id) {
    		$stmt = $this->con->prepare("SELECT id_saloon FROM request WHERE id_order = ?");
    		 $stmt->bind_param("i", $id); 
    		$stmt->execute();
    		$result = $stmt->get_result();
            $row = $result->fetch_assoc();
            return $row['id_saloon'];
			}
	
				public function deleteservice($idservice){
					$stmt = $this->con->prepare("DELETE FROM services WHERE `services`.`idservice` = ?");
					$stmt->bind_param("i", $idservice); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					$stmt->execute();
					return $stmt->affected_rows;
				}
	
			public function setworkinghour($workingHr,$idsalon) {
				$stmt = $this->con->prepare("UPDATE `saloon` SET `workingHr` = ? WHERE `saloon`.`id_saloon` = ?");
				$stmt->bind_param("si", $workingHr,$idsalon); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
				$stmt->execute();
				return $stmt->affected_rows;
				}

		// public function getSaloonByIdAdminSaloons2($id_AdminSaloons) {
    	// 	$stmt = $this->con->prepare("SELECT * FROM saloon WHERE id_AdminSaloons = ?");
    	// 	$stmt->bind_param("i", $id_AdminSaloons); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
    	// 	$stmt->execute();
    	// 	return $stmt->get_result();
		// 	}

		private function isserviceexist($nameservice){
			$stmt = $this->con->prepare("SELECT idservice FROM services WHERE  nameservice =?");
			$stmt->bind_param("s", $nameservice);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function getnameadmin(){
			$stmt = $this->con->prepare("SELECT id_adminSaloons,firstname,lastName FROM adminsaloons ");
			
			$stmt->execute(); 
			return $stmt->get_result();
			
		}
		private function checkpasswordaccount($iduser,$pass){

			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT * FROM users WHERE  id_user=? AND password=?");
			$stmt->bind_param("is", $iduser,$password);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function changepassworduser($iduser,$pass,$newpass){
			if($this->checkpasswordaccount($iduser,$pass))
			{
				$password = md5($newpass);
				$stmt = $this->con->prepare("UPDATE users SET password = ? WHERE id_user = ?");
					$stmt->bind_param("ss",$password, $iduser); // Sử dụng "i" để đại diện cho kiểu dữ liệu integer
					if($stmt->execute())
					{
                      return 0;
					}
					else{
						return 1;
					}
					;
					
			}
			else {
return 2;
			}

		}


	}
?>