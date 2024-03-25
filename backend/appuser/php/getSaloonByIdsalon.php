
<?php 
require_once '../dbconect/DbOperations.php';

$response = array(); 
$listSalon = array();
/**
 * summary
 */
class Saloon {
    public $id_saloon;
    public $name;
    public $address;
    public $mobileNo;
    public $workingHr;
    public $area;
    public $email;
    public $password;
    public $image;
    public $id_AdminSaloons;
    
    // Constructor
    public function __construct($id_saloon, $name, $address, $mobileNo, $workingHr, $area, $email, $password, $image, $id_AdminSaloons) {
        $this->id_saloon = $id_saloon;
        $this->name = $name;
        $this->address = $address;
        $this->mobileNo = $mobileNo;
        $this->workingHr = $workingHr;
        $this->area = $area;
        $this->email = $email;
        $this->password = $password;
        $this->image = $image;
        $this->id_AdminSaloons = $id_AdminSaloons;
    }
}

// if($_SERVER['REQUEST_METHOD']=='GET'){
	// if(
	// 	isset($_POST['name']) and 
	// 		isset($_POST['email']) and 
	// 			isset($_POST['password']))
	// 	{
		//operate the data further 
        if($_SERVER['REQUEST_METHOD']=='POST'){

            if(
                isset($_POST['idsalon'])){
                    $db = new DbOperations(); 

		$response = $db->getSaloonByIdsalon(($_POST['idsalon']));

		while ($row = mysqli_fetch_assoc($response) ) {
		    array_push($listSalon, new Saloon($row['id_saloon'],$row['name'],$row['address'],$row['mobileNo'],$row['workingHr'],$row['area'],$row['email'],$row['password'],$row['image'],$row['id_AdminSaloons']));
		}
                }
        }

		
		
// }
echo json_encode($listSalon);
?>