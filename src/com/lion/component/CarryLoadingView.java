package com.lion.component;

import android.content.Context;
import android.util.AttributeSet;

import com.lion.component.loadingView.FirstTimeLoadingView;

/**
 * 带load的view,包含LoadingHelper
 * 
 * @author zhangbp
 */

public class CarryLoadingView extends FirstTimeLoadingView {

	private LoadingHelper mLoadingHelper;

	public CarryLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置ListLoadingHelper
	 * 
	 * @param mLoadingHelper
	 */

	public void setLoadingHelper(LoadingHelper mLoadingHelper) {
		if (mLoadingHelper != null) {
			this.mLoadingHelper = mLoadingHelper;
			mLoadingHelper.addLoadStatusListener(this);
			setReLoadListener(new ReLoadListener() {

				@Override
				public void reLoad() {
					// TODO Auto-generated method stub
					if (CarryLoadingView.this.mLoadingHelper != null) {
						CarryLoadingView.this.mLoadingHelper.onLoadPage(LoadingHelper.LOAD_TYPE_NORMAL);
					}
				}
			});
			mLoadingHelper.notifyStatusChange(mLoadingHelper.getlastLoadStatus(), mLoadingHelper.getlastTotalCount(),mLoadingHelper.getlastLoadType());
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		if (mLoadingHelper != null) {
			mLoadingHelper.removeLoadStatusListener(this);
		}
		super.onDetachedFromWindow();
	}
}
