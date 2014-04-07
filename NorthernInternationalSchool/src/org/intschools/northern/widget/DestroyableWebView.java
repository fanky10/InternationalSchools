package org.intschools.northern.widget;

import android.content.Context;
import android.util.AttributeSet;

public class DestroyableWebView extends VideoEnabledWebView {
	public DestroyableWebView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void destroy() {
		getSettings().setBuiltInZoomControls(true);
	}
}
