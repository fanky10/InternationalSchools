package com.aenimastudio.nis.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class AbstractJsonParser<T> {

	public List<T> parse(String jsonString, String key) throws JSONException {
		List<T> resultList = new ArrayList<T>();
		JSONObject jsonObject = (JSONObject) new JSONTokener(jsonString)
				.nextValue();
		JSONArray jsonArray = jsonObject.getJSONArray(key);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonItem = jsonArray.getJSONObject(i);
			resultList.add(createObject(jsonItem));
		}
		return resultList;
	}
	
	public T parse(String jsonString) throws JSONException {
		JSONObject jsonObject = (JSONObject) new JSONTokener(jsonString).nextValue();
		return createObject(jsonObject);
	}
	
	protected abstract T createObject(JSONObject jsonItem) throws JSONException;
	

	protected String getString(JSONObject jsonItem, String key)
			throws JSONException {
		if (!jsonItem.has(key) || jsonItem.isNull(key)) {
			return null;
		} else {
			return jsonItem.getString(key);
		}
	}


}
