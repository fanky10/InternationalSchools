package org.intschools.islands.utils;

import org.intschools.islands.entities.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginResponseJsonParser extends AbstractJsonParser<LoginResponse> {

	@Override
	protected LoginResponse createObject(JSONObject jsonItem) throws JSONException {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setMessage(jsonItem.getString("error"));
		loginResponse.setUserId(jsonItem.getInt("userId"));
		return loginResponse;
	}
}
