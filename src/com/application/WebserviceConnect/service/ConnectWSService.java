package com.application.WebserviceConnect.service;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;

import com.application.WebserviceConnect.util.PropertyReader;
import com.application.WebserviceConnect.util.Utility;

import static com.application.WebserviceConnect.constant.Constant.*;

public class ConnectWSService {

	private String connect_url;
	TrustManager[] trustAllCerts = null;
	private static final String USER_AGENT = "Mozilla/5.0";

	public ConnectWSService(String url) {
		this.connect_url = url;
		trustAllCerts = new TrustManager[] {
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
		            return new X509Certificate[0];
		        } 
		        public void checkClientTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		            } 
		        public void checkServerTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
			}
		};
	}

	public String connectWs() {
		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		    
		    //CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
		    CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet httpGet = new HttpGet(connect_url);
		    httpGet.addHeader("User-Agent", USER_AGENT);
		    
		    CloseableHttpResponse response = client.execute(httpGet);
		    setLogintoServer("reponse of wsreq:: "+response.getStatusLine());
		    
		    return 	httpEntityReader(response.getEntity());
		    
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private String httpEntityReader(HttpEntity entity) throws UnsupportedOperationException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				entity.getContent()));

		String responseEntity;
		StringBuffer response = new StringBuffer();

		while ((responseEntity = reader.readLine()) != null) {
			response.append(responseEntity);
		}
		reader.close();
		return response.toString();
	}
	
	public static void main(String[] args) {
		ConnectWSService conWs = new ConnectWSService("https://iamstk14-eval-prod.apigee.net/MockData/mockdata");
		String response = conWs.connectWs();
		System.out.println(response);
	}
	
	private void setLogintoServer(String message) {
		try {
			CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
			String api_key_token = PropertyReader.getInstance().readProperty("application.syslog.loggly.apikey");
			String api_domain_name = PropertyReader.getInstance().readProperty("application.syslog.loggly.domainname");
			HttpPost logpost = new HttpPost(Utility.createUrl(LOOGLY_API_URL,new Object[] {api_key_token}));
			
			logpost.addHeader("content-type", "application/x-www-form-urlencoded");
			List<NameValuePair> list = new ArrayList<>();
			list.add(new BasicNameValuePair("logresponse", message));	
			logpost.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
			
			client.start();
			HttpResponse response = client.execute(logpost, null).get();
			System.out.println(response.getStatusLine());
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
