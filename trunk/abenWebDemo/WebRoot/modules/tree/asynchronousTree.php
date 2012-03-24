<?php

  $treeid = $_GET['id'];
  getTree($treeid);
  
  function getTree($treeid){
      $jsonStr="";
      if($treeid==""){
        $jsonStr.="[";
        $jsonStr.="{";
        $jsonStr.="'text':'卡卡西班','id':'01','leaf':false";
        $jsonStr.="},";
        $jsonStr.="{";
        $jsonStr.="'text':'凯班','id':'02','leaf':false";
        $jsonStr.="}";
        $jsonStr.="]";
        echo   $jsonStr;
      }  else if($treeid=="01"){
        $jsonStr.="[";
        $jsonStr.="{";
        $jsonStr.="'text':'鸣人','id':'0101','leaf':true";
        $jsonStr.="},";
        $jsonStr.="{";
        $jsonStr.="'text':'佐助','id':'0102','leaf':true";
        $jsonStr.="},";
        $jsonStr.="{";
        $jsonStr.="'text':'小樱','id':'0103','leaf':true";
        $jsonStr.="}";
        $jsonStr.="]";
        echo   $jsonStr;
      }	else if($treeid=="02"){
        $jsonStr.="[";
        $jsonStr.="{";
        $jsonStr.="'text':'宁次','id':'0201','leaf':true";
        $jsonStr.="},";
		$jsonStr.="{";
        $jsonStr.="'text':'天天','id':'0202','leaf':true";
        $jsonStr.="},";
        $jsonStr.="{";
        $jsonStr.="'text':'小李','id':'0203','leaf':true";
        $jsonStr.="}";
        $jsonStr.="]";
        echo   $jsonStr;
      }
  }

?>
