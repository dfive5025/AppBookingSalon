
<?php 

require_once '../dbconect/DbOperations.php';

$response = array(); 
$listSalon = array();
/**
 * summary
 */
class User{
    public $iduser;
    public $name;
    public $address;
    public $mobile;
   
    
    public $email;
    public $password;
    
    public $idadmin;
    
    // Constructor
    public function __construct($iduser, $name, $address, $mobile,  $email, $password,  $idadmin) {
        $this->iduser = $iduser;
        $this->name = $name;
        $this->address = $address;
        $this->mobile = $mobile;
       
     
        $this->email = $email;
        $this->password = $password;
       
        $this->idadmin = $idadmin;
    }
}

// if($_SERVER['REQUEST_METHOD']=='GET'){
	// if(
	// 	isset($_POST['name']) and 
	// 		isset($_POST['email']) and 
	// 			isset($_POST['password']))
	// 	{
		//operate the data further 

		$db = new DbOperations(); 

		$response = $db->getInfoUser($_POST['iduser']);

		while ($row = mysqli_fetch_assoc($response) ) {
		    array_push($listSalon, new User($row['id_user'],$row['fullname'],$row['address'],$row['mobile'],$row['email'],$row['password'],$row['id_admin']));
		}
		
// }
echo json_encode($listSalon);
?>