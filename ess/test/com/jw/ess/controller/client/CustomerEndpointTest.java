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
public class CustomerEndpointTest {
	
	private static final Log logger = LogFactory
	.getLog(CustomerEndpointTest.class);
	private HttpClient httpClient;
	@Before
	public void setUp() throws Exception {
		httpClient = new DefaultHttpClient();
	}

	@Test
	public void testAddCustomer() {
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "customerAddRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.CUSTOMER_ADD);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse(PathUtil.getAbsolutePath(getClass()) + "customerAddResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@Test
	public void testModifyCustomer() {
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "customerModifyRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.CUSTOMER_MODIFY);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse(PathUtil.getAbsolutePath(getClass()) + "customerModifyResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@Test
	public void testGetCustomerById() {
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "customerOneRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.CUSTOMER_ONE);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse(PathUtil.getAbsolutePath(getClass()) + "customerOneResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**根据employeId*/
	@Test
	public void testGetCustomersBy() {
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "customerListRequest.xml");
		logger.debug(request);
		HttpPost post = new HttpPost(UriGetter.CUSTOMER_LIST);
		try {
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse("D://customerListResponse.xml", response);
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
