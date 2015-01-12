package cn.com.gudashi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import cn.com.gudashi.Config;
import cn.com.gudashi.android.SharedPrefsKey;
import cn.com.gudashi.domain.Stock;
import cn.com.gudashi.utils.HttpUtils;
import cn.com.gudashi.utils.UrlUtils;

public class StockService {
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(SharedPrefsKey.STOCK, Context.MODE_PRIVATE);
	}

	public static List<Stock> search(String keyword) throws Exception {
		String url = "http://suggest3.sinajs.cn/suggest/type=&name=cb";
		url += '&' + UrlUtils.buildParam("key", keyword);
		String resultString = HttpUtils.getForString(url);

		int start = resultString.indexOf("var cb=\"");
		if(start >= 0){
			start += "var cb=\"".length();
		}
		int end = resultString.lastIndexOf('"');
		if(start == end){
			throw new RuntimeException("无结果！");
		}else if(start < end){
			String[] array = resultString.substring(start, end).split(";");
			List<Stock> list = new ArrayList<Stock>(array.length);
			for(String s : array){
				String[] st = s.split(",");
				if(st.length == 6){
					Stock stock = new Stock(st[2], st[4]);
					list.add(stock);
				}
			}
			return list;
		}else{
			throw new RuntimeException("奇怪的结果：" + resultString);
		}
	}

	public static List<Stock> getMyStocks(Context context, String user){
		List<Stock> list = new ArrayList<Stock>();
		String str = getSharedPreferences(context).getString(user + ".my", null);
		if(str != null){
			try{
				JSONArray arr = new JSONArray(str);
				for(int i = 0; i < arr.length(); ++i){
					JSONObject obj = (JSONObject) arr.get(i);
					list.add(new Stock(obj.getString("code"), obj.getString("name")));
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return list;
	}

	public static String addStock(String user, Stock stock){
		try {
			String error = HttpUtils.postJsonForJson(Config.SERVICE_BASE + "/my/stock/add.json?userId=" + user, Arrays.asList(stock.getCode()), String.class);
			return error;
		} catch (Exception ex) {
			return ex.toString();
		}
	}

	public static void storeMyStocks(Context context, String user, List<Stock> stocks){
		try{
			JSONArray arr = new JSONArray();
			for(Stock stock : stocks){
				JSONObject stockJson = new JSONObject();
				stockJson.put("code", stock.getCode());
				stockJson.put("name", stock.getName());
				arr.put(stockJson);
			}
			String str = arr.toString();
	
			getSharedPreferences(context)
			.edit()
			.putString(user + ".my", str)
			.apply();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
