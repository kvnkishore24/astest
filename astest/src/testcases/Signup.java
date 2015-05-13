package testcases;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import utils.Base;

public class Signup {
	@Test
	public void Create() throws ClientProtocolException, IOException, ParseException {
		String baseURL = Base.API_URL + "/user/create";
		HttpClient hclient = new DefaultHttpClient();
	//	ArrayList<String> fieldarraylist = new ArrayList<String>();
		HttpPost postReq = new HttpPost(baseURL);

		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
		namevaluepairs.add(new BasicNameValuePair("first_name", "kishore"));
		namevaluepairs.add(new BasicNameValuePair("last_name", "kalapala"));
		namevaluepairs.add(new BasicNameValuePair("email", "kishorekalapala@digital-nirvana.com"));
		namevaluepairs.add(new BasicNameValuePair("password", "123456"));

		try {
			postReq.setEntity(new UrlEncodedFormEntity(namevaluepairs));
			System.out.println("executing request " + postReq.getRequestLine());

			HttpResponse response = hclient.execute(postReq);

			HttpEntity resEntity = response.getEntity();
			int STATUS = response.getStatusLine().getStatusCode();
			if (resEntity != null & STATUS == 200) {
				String responsebody = EntityUtils.toString(resEntity);
				System.out.println("HTTP RESPOSE:" + response.getStatusLine());
				System.out.println("RESPONSE LENGTH: " + resEntity.getContentLength());
				System.out.println("Chunked? " + resEntity.isChunked());
				System.out.println("-------------------------------------------");
				System.out.println(STATUS);
				System.out.println("JSON DATA:" + responsebody);
				System.out.println("-------------------------------------------");

				JSONParser parser = new JSONParser();
				Object resultobject = parser.parse(responsebody);
				if (resultobject instanceof JSONObject) {
					JSONObject obj = (JSONObject) resultobject;
					String version = obj.get("apiVersion").toString();
					String resId = obj.get("responseId").toString();

					System.out.println(version);
					System.out.println(resId);

					if (obj.get("error") != null) {
						Object errorobject = obj.get("error");
						JSONObject obj1 = (JSONObject) errorobject;
						String errorCode = obj1.get("code").toString();
						String errorMessage = obj1.get("message").toString();
						System.out.println(errorCode);
						System.out.println(errorMessage);

					} else {
						Object dataObject = obj.get("data");
						if (dataObject instanceof JSONObject) {
							JSONObject obj2 = (JSONObject) dataObject;
							Object items = obj2.get("items");
							if (items instanceof JSONArray) {
								JSONArray itemsArray = (JSONArray) items;
								for (Object itemObject : itemsArray) {
									JSONObject obj3 = (JSONObject) itemObject;
									System.out.println("-----------------------------------------------------------------");
									System.out.println("Number of Stocks ::" + obj3.size());
									System.out.println("-----------------------------------------------------------------");
									String id = obj3.get("id").toString();

									if (id != null) {
										System.out.println("User created");
										System.out.println(id);
										
									}
								}
							}
						}
					}

				}
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

	}
}
