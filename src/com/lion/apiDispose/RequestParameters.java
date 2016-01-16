package com.lion.apiDispose;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.easyframework.json.EasyJson;
import com.easyframework.util.EasyTools;

/**
 * request参数
 * 
 * @author zhangbp
 * 
 */

public class RequestParameters {

	public String api_key;
	public String api_secret;
	public String userToken;
	public String userId;

	private HashMap<String, TreeMap<String, String>> parametersMap = new HashMap<String, TreeMap<String, String>>();
	private String cacheKey = "";

	public RequestParameters(String api_key, String api_secret, String userToken, String userId) {
		this.api_key = api_key;
		this.api_secret = api_secret;
		this.userToken = userToken;
		this.userId = userId;
	}

	/**
	 * 用apiName返回 map
	 * 
	 * @param apiName
	 * @return
	 */

	public TreeMap<String, String> getParameterByApiName(String apiName) {
		TreeMap<String, String> items = parametersMap.get(apiName);
		if (items == null) {
			items = new TreeMap<String, String>();
			parametersMap.put(apiName, items);
		}
		return items;
	}

	/**
	 * 返回参数
	 * 
	 * @param apiName
	 * @return
	 */

	public HashMap<String, TreeMap<String, String>> getParameter() {
		return this.parametersMap;
	}

	/**
	 * 添加Parameter
	 * 
	 * @param apiName
	 * @return
	 */

	public void putParameter(String apiName, TreeMap<String, String> parameter) {
		if (parameter != null) {
			parametersMap.put(apiName, parameter);
		}
	}

	/**
	 * 
	 * @param apiName
	 * @return
	 */

	public void removeParameterByApiName(String apiName) {
		parametersMap.remove(apiName);
	}

	/**
	 * 返回最终的参数
	 * 
	 * @return
	 */

	public String getFinalParameter() {
		StringBuffer cacheKeySb = new StringBuffer();
		for (Entry<String, TreeMap<String, String>> itemMap : parametersMap.entrySet()) {
			TreeMap<String, String> item = itemMap.getValue();
			item.put("apiKey", api_key);
			item.put("authorization_token", userToken);
			item.put("current_user_id", userId);
			item.remove("token");
			String token = sign(item, itemMap.getKey());
			item.put("token", token);
			cacheKeySb.append(token);
		}
		cacheKey = cacheKeySb.length() == 32 ? cacheKeySb.toString() : EasyTools.md5(cacheKeySb.toString());
		return EasyJson.toJson(parametersMap);
	}

	/**
	 * 签名
	 * 
	 * @param maps
	 * @return
	 */

	public String sign(TreeMap<String, String> maps, String apiCode) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> itemMap : maps.entrySet()) {
			String value = itemMap.getValue();
			if (value != null && !value.equals("")) {
				sb.append(itemMap.getKey()).append("=").append(itemMap.getValue()).append("&");
			}
		}
		sb.append(apiCode + "&");
		sb.append(api_secret);
		return EasyTools.md5(sb.toString());
	}

	/**
	 * cacheKey
	 * 
	 * @return
	 * @author zhangbp
	 */

	public String getCacheKey() {
		return this.cacheKey;
	}

}
