package com.ifinanceweb.web.test.demo.action;


import org.springframework.beans.factory.annotation.Autowired;

import com.ifinanceweb.mvc.CRUDActionSupport;
import com.ifinanceweb.web.test.strips.service.StripService;

public class DemoAction extends CRUDActionSupport{
	    private double numberOne;      
	    private double numberTwo;      
	    private double result; 
	    
	    @Autowired
	    private transient StripService stripService;
	     
	     
	    public double getNumberOne() { return numberOne; }      
	    public void setNumberOne(double numberOne) { this.numberOne = numberOne; }      
	     
	    public double getNumberTwo() { return numberTwo; }      
	    public void setNumberTwo(double numberTwo) { this.numberTwo = numberTwo; }      
	     
	    public double getResult() { return result; }      
	    public void setResult(double result) { this.result = result; }      
	     
	    public String addition() {      
	        result = getNumberOne() + getNumberTwo();
	        
	        stripService.quert();
			this.setTargetUrl("stuctsMvc");
			return this.RESULT_JSP; 
	    }      
	}    
	 