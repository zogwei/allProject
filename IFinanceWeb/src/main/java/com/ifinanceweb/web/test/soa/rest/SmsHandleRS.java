package com.ifinanceweb.web.test.soa.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Path("/jersey")
@Component("smsHandleRS")
public class SmsHandleRS {

    private final static Logger logger = LoggerFactory.getLogger(SmsHandleRS.class);
    public static final String response_success = "success";
    @Path("/rec")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String recSms(@QueryParam("SmsType") String SmsType) {
    	
        return response_success;
    }
    
}
