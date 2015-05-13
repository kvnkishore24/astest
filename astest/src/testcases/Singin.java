package testcases;

import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import utils.Base;

public class Singin {

	CloseableHttpClient client;
	CloseableHttpResponse response;
	Base base = new Base();
	public static String USER_TOKEN;
	int STATUS_CODE = 0;

	@Test(priority = 0)
	public void login() throws IOException, ParseException {

		String sourceurl = base.API_URL + "/user/login";
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(sourceurl);
		request.setHeader(HttpHeaders.AUTHORIZATION, base.authorizationHeader(base.USER_NAME, base.PASS_WORD));

		response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		// HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(response.getEntity());
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println(statuscode);

		if (statuscode == 200) {
			System.out.println(responseString);

			try {
				JSONParser parser = new JSONParser();
				// JSONObject jsonObject = (JSONObject)
				// parser.parse(responseString);

				Object resultObject = parser.parse(responseString);

				if (resultObject instanceof JSONArray) {
					JSONArray array = (JSONArray) resultObject;
					for (Object object : array) {
						JSONObject obj = (JSONObject) object;
						System.out.println(obj.get("apiVersion"));
						System.out.println(obj.get("responseId"));
					}

				} else if (resultObject instanceof JSONObject) {
					JSONObject obj = (JSONObject) resultObject;
					System.out.println(obj.get("apiVersion"));
					System.out.println(obj.get("responseId"));

					Object dataobject = obj.get("data");
					if (dataobject instanceof JSONObject) {
						JSONObject obj1 = (JSONObject) dataobject;
						Object items = obj1.get("items");
						if (items instanceof JSONArray) {
							JSONArray itemsarray = (JSONArray) items;
							for (Object itemObject : itemsarray) {
								JSONObject obj3 = (JSONObject) itemObject;

								System.out.println(obj3.get("id"));
								USER_TOKEN = obj3.get("token").toString();
								System.out.println(USER_TOKEN);
							}
						}
					}
				}

			} catch (Exception e) {
			}
		}
	}

	@Test(priority = 1)
	public void getStocks() {
		System.out.println("-----------------------------------------------------------------");
		String sourceurl = base.API_URL + "/mystocks/get";
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(sourceurl);
		request.setHeader("X-Auth-Token", USER_TOKEN);
		try {
			response = client.execute(request);
			STATUS_CODE = response.getStatusLine().getStatusCode();
			String responseEntity = EntityUtils.toString(response.getEntity());
			System.out.println(responseEntity);

			if (STATUS_CODE == 200) {
				System.out.println("STATUS_CODE =" + STATUS_CODE);

				JSONParser parser = new JSONParser();
				try {
					Object resultObject = parser.parse(responseEntity);
					if (resultObject instanceof JSONObject) {
						JSONObject mainObj = (JSONObject) resultObject;
						String apiVersion = mainObj.get("apiVersion").toString();
						String resposeId = mainObj.get("responseId").toString();
						System.out.println(apiVersion);
						System.out.println(resposeId);
						Object dataobject = mainObj.get("data");
						if (dataobject instanceof JSONObject) {
							JSONObject dataJSON = (JSONObject) dataobject;
							Object items = dataJSON.get("items");
							if (items instanceof JSONArray) {
								JSONArray itemsArray = (JSONArray) items;
								for (Object itemObject : itemsArray) {
									JSONObject obj3 = (JSONObject) itemObject;
									System.out.println("-----------------------------------------------------------------");
									System.out.println("Number of Stocks ::" + obj3.size());
									System.out.println("-----------------------------------------------------------------");
									String Stocks = obj3.get("id").toString();

									System.out.println(Stocks);
									System.out.println(obj3.get("name").toString());
									System.out.println(obj3.get("ticker").toString());
									System.out.println(obj3.get("sector").toString());
								}
							}
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println(STATUS_CODE + "test cased failed");
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------");
	}
	/*
	 * @Test(priority = 1) public void getStockId() { String sourceurl =
	 * baseapi.API_URL + "/mystocks/get"; client =
	 * HttpClientBuilder.create().build(); HttpGet request = new
	 * HttpGet(sourceurl); request.setHeader("X-Auth-Token", USER_TOKEN); try {
	 * response = client.execute(request); STATUS_CODE =
	 * response.getStatusLine().getStatusCode(); String responsebody =
	 * EntityUtils.toString(response.getEntity());
	 * 
	 * if (STATUS_CODE == 200) { ObjectMapper mapper = new ObjectMapper();
	 * JsonNode node = mapper.readTree(sourceurl);
	 * System.out.println("ContainerNode" + node.isContainerNode());
	 * Iterator<JsonNode> itemlist = node.path("items").iterator(); while
	 * (itemlist.hasNext()) {
	 * System.out.println(itemlist.next().path("id").textValue()); } }
	 * 
	 * System.out.println("--------------------------");
	 * System.out.println(responsebody);
	 * System.out.println("--------------------------");
	 * 
	 * } catch (ClientProtocolException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */
	/*
	 * @Test() public void getAlbumId() throws MalformedURLException,
	 * IOException { String url =
	 * "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=10"
	 * ;
	 * 
	 * String genreJson = IOUtils.toString(new URL(url));
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * JsonNode node = mapper.readTree(genreJson);
	 * 
	 * Iterator<JsonNode> albums = node.path("dataset").iterator(); while
	 * (albums.hasNext()) {
	 * System.out.println(albums.next().path("album_id").textValue()); } }
	 */

}
