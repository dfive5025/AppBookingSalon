<?php 

require_once '../dbconect/DbOperations.php';

$response = array(); 
$listSalon = array();
/**
 * summary
 */
class Adminsalon {
    public $id;
    public $firstname;
    public $lastname;
 
    
    // Constructor
    public function __construct($id,$firstname,$lastname) {
        $this->id=$id;
        $this->firstname=$firstname;
        $this->lastname=$lastname;
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

		$response = $db->getnameadmin();

		while ($row = mysqli_fetch_assoc($response) ) {
		    array_push($listSalon, new Adminsalon($row['id_adminSaloons'],$row['firstname'],$row['lastName']));
		}
		
// }
echo json_encode($listSalon);
?>