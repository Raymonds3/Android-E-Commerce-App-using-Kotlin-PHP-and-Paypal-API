<?php
$con=new mysqli ("localhost","root","","salesweb");
$st_check=$con->prepare ("select id,name,price,qty,mobile FROM temp_order INNER JOIN items on items.id=temp_order.itemid WHERE mobile=?");
$st_check->bind_param ("s", $_GET["mobile"]);
 $st_check->execute();
$rs=$st_check->get_result();
$arr=array();
while ($row=$rs->fetch_assoc()) {
  array_push($arr, $row);
}

echo json_encode($arr);


 ?>
