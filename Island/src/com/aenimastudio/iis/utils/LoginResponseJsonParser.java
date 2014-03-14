package com.aenimastudio.iis.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.aenimastudio.iis.entities.LoginResponse;

public class LoginResponseJsonParser extends AbstractJsonParser<LoginResponse> {

	@Override
	protected LoginResponse createObject(JSONObject jsonItem) throws JSONException {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setMessage(jsonItem.getString("error"));
		loginResponse.setUserId(jsonItem.getInt("userId"));
		return loginResponse;
	}
}
