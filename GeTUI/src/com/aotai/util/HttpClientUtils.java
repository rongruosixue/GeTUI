package com.aotai.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public static String httpGet(String url) {

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		String result = "";

		try {
			client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setSocketTimeout(60000)
					.build();
			httpGet.setConfig(requestConfig);
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
