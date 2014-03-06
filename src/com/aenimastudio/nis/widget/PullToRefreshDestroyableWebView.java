package com.aenimastudio.nis.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.webkit.WebView;

import com.aenimastudio.nis.R;
import com.handmark.pulltorefresh.library.OverscrollHelper;

public class PullToRefreshDestroyableWebView extends com.handmark.pulltorefresh.library.PullToRefreshWebView {

	public PullToRefreshDestroyableWebView(Context context) {
		super(context);
	}

	public PullToRefreshDestroyableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshDestroyableWebView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshDestroyableWebView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);

	}

	@Override
	protected WebView createRefreshableView(Context context, AttributeSet attrs) {
		WebView webView;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			webView = new InternalWebViewSDK9(context, attrs);
		} else {
			webView = new DestroyableWebView(context, attrs);
		}

		webView.setId(R.id.webview);
		return webView;
	}


	@TargetApi(9)
	final class InternalWebViewSDK9 extends DestroyableWebView {

		// WebView doesn't always scroll back to it's edge so we add some
		// fuzziness
		static final int OVERSCROLL_FUZZY_THRESHOLD = 2;

		// WebView seems quite reluctant to overscroll so we use the scale
		// factor to scale it's value
		static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

		public InternalWebViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshDestroyableWebView.this, deltaX, scrollX, deltaY, scrollY,
					getScrollRange(), OVERSCROLL_FUZZY_THRESHOLD, OVERSCROLL_SCALE_FACTOR, isTouchEvent);

			return returnValue;
		}

		private int getScrollRange() {
			return (int) Math.max(0, FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale())
					- (getHeight() - getPaddingBottom() - getPaddingTop()));
		}
	}
}