package com.cup.sample.jmx;


import java.util.ArrayList;
import java.util.List;
public class Router {  
        List ipList=new ArrayList();   
        public void addIP(String ip){          
                System.out.println("addIP:"+ip);       
                ipList.add(ip);        
        }      
        public void deleteIP(String ip){       
                System.out.println("deleteIP:"+ip);            
                ipList.remove(ip);     
        }      
        public  String[] getIPS(){     
                if (ipList.isEmpty()) return new String[0];            
                String[] x =(String[])ipList.toArray(new String[ipList.size()]);       
                for(int i =0; i<x.length;i++){      
                        System.out.println(x[i]);              
                }      
                return x;      
        }
}

