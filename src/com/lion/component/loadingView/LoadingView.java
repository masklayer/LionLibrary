package com.lion.component.loadingView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lion.component.LoadingHelper.LoadStatusListener;

/**
 * 正在加载的view
 * 
 * @author zhangbp
 */

public abstract class LoadingView extends FrameLayout implements LoadStatusListener {

	private ReLoadListener mReLoadListener;

	public LoadingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setReLoadListener(ReLoadListener mReLoadListener) {
		this.mReLoadListener = mReLoadListener;
	}

	public void reLoadPage() {
		// TODO Auto-generated method stub
		if (mReLoadListener != null) {
			mReLoadListener.reLoad();
		}
	}

	public interface ReLoadListener {
		public void reLoad();
	}

}
