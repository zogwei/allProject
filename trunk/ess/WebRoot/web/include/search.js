var searchReq=createAjaxObj();
var formname="";	

function createAjaxObj()
{
	var httprequest=false;
	if(window.XMLHttpRequest)
	{
		httprequest=new XMLHttpRequest();
		if(httprequest.overrideMimeType)
			httprequest.overrideMimeType('text/xml');
	}
	else if (window.ActiveXObject)
	{
		//IE
		try
		{
			httprequest=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				httprequest=new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e)
			{
			}
		}
	}
	return httprequest
}

function search(f0,t,f1,f2,id,w,o)
{	
	    formname=f0;
		url="include/search.asp?t="+t+"&f1="+f1+"&f2="+f2+"&id="+id+"&w="+w+"&o="+o+"&m=" +  new Date().getTime();		
		searchReq.open("get",url);
		searchReq.onreadystatechange=handleSearchSuggest;
		searchReq.send(null);		
	
}

function handleSearchSuggest()
{

	if(searchReq.readyState==3)
	{							
				switch(formname)
				{
					case "YY_area":
					{
						 document.getElementById("YY_area").options[0]=new Option("请选择地区","0");
						 document.getElementById("YY_area").options.selected=true;
						 document.getElementById("YY_area").length=1;
					}
					
					case "YY_province":
					{
						 document.getElementById("YY_province").options[0]=new Option("请选择省份","0");
						 document.getElementById("YY_province").options.selected=true;
						 document.getElementById("YY_province").length=1;
					}
					case "YY_city":
					{
						 document.getElementById("YY_city").options[0]=new Option("请选择城市","0");
						 document.getElementById("YY_city").options.selected=true;
						 document.getElementById("YY_city").length=1;
					}

				}
				switch(formname)
				{
					case "YY_category":
					{
						 document.getElementById("YY_category").options[0]=new Option("请选择行业","0");
						 document.getElementById("YY_category").options.selected=true;
						 document.getElementById("YY_category").length=1;
					}
					case "YY_class":
					{
						 document.getElementById("YY_class").options[0]=new Option("请选择行","0");
						 document.getElementById("YY_class").options.selected=true;
						 document.getElementById("YY_class").length=1;
					}
					case "YY_board":
					{
						 document.getElementById("YY_board").options[0]=new Option("请选择行","0");
						 document.getElementById("YY_board").options.selected=true;
						 document.getElementById("YY_board").length=1;
					}
				}
				switch (formname)
				{
					case "YY_area":name="地区";break;
					case "YY_province":name="省份";break;
					case "YY_city":name="城市";break;
					case "YY_category":name="行业";break;
					case "YY_class":name="行业";break;
					case "YY_board":name="行业";break;
				}
				xmldoc=searchReq.responseXML;	
				var message_nodes=xmldoc.getElementsByTagName("message");
				var n_messages=message_nodes.length;				
				if (n_messages<=0)
				{	
					 document.getElementById(formname).options[0]=new Option("请选择"+name,"0");
					 document.getElementById(formname).options.selected=true;
					 document.getElementById(formname).length=1;
				}
			    else
				{ 	
					document.getElementById(formname).options[0]=new Option("请选择"+name,"0");				
					for (i=0;i<n_messages;i++ )
					{  						
						var cid=message_nodes[i].getElementsByTagName("cid")[0].firstChild.data;
						var name=message_nodes[i].getElementsByTagName("name")[0].firstChild.data; 
						document.getElementById(formname).options[i+1]=new Option(name,cid); 
					}					
				    document.getElementById(formname).options.selected=true;
					document.getElementById(formname).length=i+1;

				}
				
	}
	else
	{
		//alert('网络连接失败');
	}
}
