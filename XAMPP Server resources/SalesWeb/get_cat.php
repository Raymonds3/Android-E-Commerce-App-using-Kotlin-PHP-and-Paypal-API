<?php

$con=new mysqli ("localhost","root","","salesweb");
$st_check=$con->prepare ("select distinct category from items");
 $st_check->execute();
 $rs=$st_check->get_result();
$arr=array();
while ($row=$rs->fetch_assoc()) {
  array_push($arr, $row);
}

echo json_encode($arr);

 ?>
