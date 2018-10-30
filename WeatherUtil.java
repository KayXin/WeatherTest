package WeatherTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * java调用中央气象局天气预报接口
 * 
 * @author XK
 * 
 */
public class WeatherUtil {
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url 发送请求的URL
     *            
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public Map<String, String> getWeatherData(Map<String,String> param){
//    	Map<String,Object> mapTypes = JSON.parseObject(sendGet(url));  
//    	JSONArray json = JSONArray.fromObject(mapTypes.get("HeWeather6"));//userStr是json字符串
//    	JSONObject jsonobject = JSONObject.fromObject(json.get(0));
    	
    	Map<String,String> map = new HashMap<String,String>();
    	
    	for (Map.Entry<String, String> entry : param.entrySet()) { 
//    		System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
    	  
    		//和风天气接口,key需要申请
    		String url = "https://free-api.heweather.com/s6/weather?location="+entry.getValue()+"&key=e13d97893ef041e58fc1735509a61961";
    		JsonObject jsonObject = (JsonObject) new JsonParser().parse(sendGet(url));
    		
    		JsonObject jsonObject_now = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject().getAsJsonObject("now");
    		
    		map.put("wea_"+entry.getKey(), jsonObject_now.get("cond_txt").getAsString());//天气
	    	map.put("hum_"+entry.getKey(), jsonObject_now.get("hum").getAsString()+"%");//湿度
	    	map.put("tem_"+entry.getKey(), jsonObject_now.get("tmp").getAsString());//温度
	    	map.put("win_"+entry.getKey(), jsonObject_now.get("wind_dir").getAsString() + "," +jsonObject_now.get("wind_sc").getAsString()+ "级");//风向
	    	
	    	JsonArray jsonArray_forecast = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject().getAsJsonArray("lifestyle");
	    	map.put("clothes_"+entry.getKey(), jsonArray_forecast.get(1).getAsJsonObject().get("brf").getAsString());//穿衣指数
	    	map.put("advice_"+entry.getKey(), jsonArray_forecast.get(1).getAsJsonObject().get("txt").getAsString());//穿衣建议
	    	map.put("uv_"+entry.getKey(), jsonArray_forecast.get(5).getAsJsonObject().get("brf").getAsString());//uv
	    	map.put("air_"+entry.getKey(), jsonArray_forecast.get(7).getAsJsonObject().get("brf").getAsString());//空气质量
	    	
	    	JsonArray jsonArray_daily = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject().getAsJsonArray("daily_forecast");
	    	map.put("tem_"+entry.getKey(), jsonArray_daily.get(0).getAsJsonObject().get("tmp_max").getAsString()+"~"+
	    			jsonArray_daily.get(0).getAsJsonObject().get("tmp_min").getAsString()+"℃");//温度
	    	
	    	//当前天气情况
//	  		String url_now = "https://free-api.heweather.com/s6/weather/now?location="+entry.getValue()+"&key=e13d97893ef041e58fc1735509a61961";
//	  		//生活指数
//	  		String url_forecast = "https://free-api.heweather.com/s6/weather/lifestyle?location="+entry.getValue()+"&key=e13d97893ef041e58fc1735509a61961";
	    	
//	  		//通过url获取数据,并解析response   json
//	    	JsonObject jsonObject_now = (JsonObject) new JsonParser().parse(sendGet(url_now));
//	    	JsonObject jsonObject_now1 = jsonObject_now.getAsJsonArray("HeWeather6").get(0).getAsJsonObject().getAsJsonObject("now");
//	    	
//	    	map.put("wea_"+entry.getKey(), jsonObject_now1.get("cond_txt").getAsString());//天气
//	    	map.put("hum_"+entry.getKey(), jsonObject_now1.get("hum").getAsString());//湿度
//	    	map.put("tem_"+entry.getKey(), jsonObject_now1.get("tmp").getAsString());//温度
//	    	map.put("win_"+entry.getKey(), jsonObject_now1.get("wind_dir").getAsString() + "," +jsonObject_now1.get("wind_sc").getAsString()+ "级");//风向
//	    	
//	    	//通过url获取数据,并解析response   json
//	    	JsonObject jsonObject_forecast = (JsonObject) new JsonParser().parse(sendGet(url_forecast));
//	    	JsonArray jsonArray_forecast = jsonObject_forecast.getAsJsonArray("HeWeather6").get(0).getAsJsonObject().getAsJsonArray("lifestyle");
//	    	
//	    	map.put("clothes_"+entry.getKey(), jsonArray_forecast.get(1).getAsJsonObject().get("brf").getAsString());//穿衣指数
//	    	map.put("advice_"+entry.getKey(), jsonArray_forecast.get(1).getAsJsonObject().get("txt").getAsString());//穿衣建议
//	    	map.put("uv_"+entry.getKey(), jsonArray_forecast.get(5).getAsJsonObject().get("brf").getAsString());//uv
//	    	map.put("air_"+entry.getKey(), jsonArray_forecast.get(7).getAsJsonObject().get("brf").getAsString());//空气质量
	    	
//	    	for (JsonElement jsonElement : jsonArray_forecast) {
//				System.out.println(jsonElement.getAsJsonObject().get("type").getAsString());
//				System.out.println(jsonElement.getAsJsonObject().get("brf").getAsString());
//				System.out.println(jsonElement.getAsJsonObject().get("txt").getAsString());
//			}
    	}
    	
//    	JsonObject jsonObject1 = (JsonObject) new JsonParser().parse(jsonObject_now);
//    	System.out.println(jsonObject1.getAsJsonObject("cloud"));
    	
    	return map;
    }
    
    public static void main(String[] args) {
    	//发送 GET 请求
    	Map<String,String> map = new HashMap<String,String>();
    	
    	map.put("dep", "天津");
    	map.put("arr", "长沙");
    	WeatherUtil a = new WeatherUtil();
    	System.out.println(a.getWeatherData(map));
	}
}