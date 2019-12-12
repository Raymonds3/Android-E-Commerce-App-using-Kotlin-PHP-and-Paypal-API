<?php
$con=new mysqli ("localhost","root","","salesweb");
$st=$con->prepare ("select price,qty from items inner join bill_details on items.id=bill_details.itemid where bill_details.bill_no=?");
$st->bind_param ("i", $_GET["bill_no"]);
 $st->execute();
 $rs=$st->get_result();
 $total=0;
while ($row=$rs->fetch_assoc())
{
  $total=$total + ($row["price"]*$row["qty"]);

}

echo $total;



 ?>
