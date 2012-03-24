<?php

$begin = $_POST['begin'];
$end = $_POST['end'];
$type = $_POST['type'];

if($type=='append'){
	$result = "已经把'".$begin."'节点，添加到'".$end."'中!";
}else if($type=='above'){
	$result = "已经把'".$begin."'节点，移动到'".$end."'上!";
}else if($type=='below'){
	$result = "已经把'".$begin."'节点，移动到'".$end."'下!";
}

echo $result;

?>