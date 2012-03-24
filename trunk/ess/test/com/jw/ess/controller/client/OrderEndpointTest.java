package com.jw.ess.controller.client;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import com.jw.ess.util.FileOperator;
import com.jw.ess.util.PathUtil;
import com.jw.ess.util.TestAssisant;
import com.jw.ess.util.UriGetter;

public class OrderEndpointTest 
{
	private static final Log logger = LogFactory.getLog(EmployeeEndpointTest.class);

	private HttpClient httpClient;
	
	@Before
	public void setUp() throws Exception 
	{
		httpClient = new DefaultHttpClient();
	}

	@Test
	public void testOrderAdd() 
	{
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "orderAddRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.ORDER_ADD);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse( "D://orderAddResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	@Test
	public void testOrderList() 
	{
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "orderListRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.ORDER_LIST);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse( "D://orderListResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	@Test
	public void testGetOrderById() {
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "customerOneRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.ORDER_ONE);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse("D://orderOneResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	

	@Test
	public void testOrderConfirm()
	{
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "orderConfirmRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.ORDER_CONFIRM);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse("D://orderConfirmResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	

	@Test
	public void testCancalOrder(){
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "orderCancelRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.ORDER_CANCEL);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse("D://orderCancalResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
		
		
	}
	

}
