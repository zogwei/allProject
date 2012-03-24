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

public class SpecEndpointTest {

	private static final Log logger = LogFactory
	.getLog(SpecEndpointTest.class);
	private HttpClient httpClient;
	
	@Before
	public void setUp() throws Exception {
		httpClient = new DefaultHttpClient();
	}
	
	
	/**
	 *根据tenantId
	 **/
	@Test
	public void testGetSpecsBy(){
		String request = FileOperator.readToRequest(PathUtil.getAbsolutePath(getClass()) + "specListRequest.xml");
		HttpPost post = new HttpPost(UriGetter.SPEC_LIST);
		try{
			StringEntity entity = new StringEntity(request);
			entity.setContentEncoding("UTF-8");
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			String response = TestAssisant.getString(httpResponse.getEntity().getContent());
			FileOperator
					.writeFromResponse(PathUtil.getAbsolutePath(getClass()) + "specListResponse.xml", response);
		}catch(IOException e){
			logger.error(e);
		}
	}
}
