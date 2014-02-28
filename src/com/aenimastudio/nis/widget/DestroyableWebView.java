package com.aenimastudio.nis.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class DestroyableWebView extends WebView {
	public DestroyableWebView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public void destroy(){
		getSettings().setBuiltInZoomControls(true);
	}
}
