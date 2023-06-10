package data;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Load {
		
	/**
	 * 와이파이 총 개수 얻기
	 * @return 총 와이파이 데이터 수
	 */
	public static int getCount() {
		int totalCount = 0;
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		try {
			urlBuilder.append("/" + URLEncoder.encode("46666a7056726b6635387362627062","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );
			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		URL url;
		try {
			url = new URL(urlBuilder.toString());
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				String line;
				while ((line = rd.readLine()) != null) {
					String json = line;
					
					Map<String, Object> map1 = new Gson().fromJson(json, Map.class);
					Map<String, Object> map2 = (Map<String, Object>) map1.get("TbPublicWifiInfo");
					
					totalCount = (int) Double.parseDouble(String.valueOf(map2.get("list_total_count")));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return totalCount;
	}
		
	
	/**
	 * 와이파이 데이터 불러와 DB에 저장
	 * @throws IOException
	 */
	public void run() throws IOException {
		int totalCount = Load.getCount();
		Service svc = new Service();
		
		for (int i = 0; i <= totalCount; i += 1000) {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
			urlBuilder.append("/" + URLEncoder.encode("46666a7056726b6635387362627062","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );
			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));

			String start = Integer.toString(i + 1);
			String end = Integer.toString(i + 1000);
			
			urlBuilder.append("/" + URLEncoder.encode(start,"UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(end,"UTF-8"));
		
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
		
			String line;
			while ((line = rd.readLine()) != null) {
				String json = line;
				Map<String, Object> map1 = new Gson().fromJson(json, Map.class);
				Map<String, Object> map2 = (Map<String, Object>) map1.get("TbPublicWifiInfo");
				List<Map<String, String>> list = (ArrayList<Map<String, String>>) map2.get("row");
				
				try {
					svc.load(list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rd.close();
			conn.disconnect();
		}
	}
}
