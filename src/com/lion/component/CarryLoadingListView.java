package com.lion.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.widget.ListView;

import com.lion.component.loadingView.FirstTimeLoadingView;

/**
 * 带load的view,包含listView
 * 
 * @author zhangbp
 */

public class CarryLoadingListView extends FirstTimeLoadingView {

	private LoadingListView mLoadingListView;
	private ListLoadingHelper mListLoadingHelper;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private boolean ishasPull = false;

	public CarryLoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		ishasPull = attrs.getAttributeBooleanValue(null, "ishasPull", false);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		initView();
		super.onFinishInflate();
	}

	/**
	 * 初始化view
	 * 
	 * @author zhangbp
	 */
	private void initView() {
		if (ishasPull) {
			addPullListView();
		} else {
			addListView();
		}
	}

	/**
	 * 创建listView
	 * 
	 * @author zhangbp
	 */

	private LoadingListView createListView() {
		mLoadingListView = new LoadingListView(getContext());
		mLoadingListView.setCacheColorHint(0x00000000);
		mLoadingListView.setDivider(null);
		mLoadingListView.setBackgroundColor(0x00000000);
		mLoadingListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mLoadingListView.setVerticalScrollBarEnabled(false);
		return mLoadingListView;
	}

	/**
	 * 添加下拉的listView
	 * 
	 * @author zhangbp
	 */

	@SuppressLint("InlinedApi")
	private void addPullListView() {

		mSwipeRefreshLayout = new SwipeRefreshLayout(getContext());
		mLoadingListView = createListView();
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
		mSwipeRefreshLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mSwipeRefreshLayout);
		mSwipeRefreshLayout.addView(mLoadingListView);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mListLoadingHelper.onLoadPage(LoadingHelper.LOAD_TYPE_PULLREFRESH);
			}
		});
	}

	/**
	 * 添加listview
	 * 
	 * @author zhangbp
	 */
	private void addListView() {
		mLoadingListView = createListView();
		addView(mLoadingListView);
	}

	/**
	 * 返回listView
	 * 
	 * @return
	 */

	public ListView getListView() {
		return mLoadingListView;
	}

	/**
	 * 设置ListLoadingHelper
	 * 
	 * @param mListLoadingHelper
	 */

	public void setLoadingHelper(ListLoadingHelper mListLoadingHelper) {
		if (mListLoadingHelper != null) {
			this.mListLoadingHelper = mListLoadingHelper;
			mListLoadingHelper.addLoadStatusListener(this);
			mLoadingListView.setLoadingHelper(mListLoadingHelper);
			setReLoadListener(new ReLoadListener() {

				@Override
				public void reLoad() {
					// TODO Auto-generated method stub
					if (CarryLoadingListView.this.mListLoadingHelper != null) {
						CarryLoadingListView.this.mListLoadingHelper.onLoadPage(LoadingHelper.LOAD_TYPE_NORMAL);
					}
				}
			});
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		if (mListLoadingHelper != null) {
			mListLoadingHelper.removeLoadStatusListener(this);
		}
		super.onDetachedFromWindow();
	}

	@Override
	public void statusChange(int loadStatus, int totalCount,int loadType) {
		// TODO Auto-generated method stub
		super.statusChange(loadStatus, totalCount,loadType);
		if (mSwipeRefreshLayout != null  && loadType == LoadingHelper.LOAD_TYPE_PULLREFRESH) {
			mSwipeRefreshLayout.setRefreshing(loadStatus == LoadingHelper.LOAD_STATUS_ING ? true : false);
		}
	}
}
