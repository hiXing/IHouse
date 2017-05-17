package com.jucai.wuliu.net;

import org.json.JSONObject;

public interface MainServerListener {
	public void requestFailure(String s);

	public void requestSuccess(JSONObject s);
}
