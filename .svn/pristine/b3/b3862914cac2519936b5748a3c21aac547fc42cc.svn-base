package com.lion.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.easyframework.imageLoader.EasyPauseOnScrollListener;
import com.lion.R;
import com.lion.component.LoadingHelper.LoadStatusListener;
import com.lion.component.loadingView.LoadingView.ReLoadListener;
import com.lion.component.loadingView.SmailLoadingView;

/**
 * 控制加载的listView
 * 
 * @author zhangbp
 **/

public class LoadingListView extends ListView implements OnScrollListener, LoadStatusListener {

	public boolean isLoading = false; // 是否正在加载
	private boolean isScrollBottom = false; // 是否滚动到底部
	private ListLoadingHelper mListLoadingHelper;
	private SmailLoadingView mSmailLoadingView;
	private int lastLoadStatus;

	public LoadingListView(Context context) {
		super(context);
	}

	public LoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		setOnScrollListener(new EasyPauseOnScrollListener(false, true, this));
		super.setAdapter(adapter);
	}

	/**
	 * 返回OnScrollListener
	 * 
	 * @return
	 */
	public OnScrollListener getOnScrollListener() {
		return this;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && isScrollBottom && !isLoading && mSmailLoadingView != null) {
			loadPage();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		isScrollBottom = totalItemCount == firstVisibleItem + visibleItemCount;
	}

	/**
	 * 加载下一页
	 * 
	 * @author zhangbp
	 */
	private void loadPage() {
		if (mListLoadingHelper != null && lastLoadStatus != LoadingHelper.LOAD_STATUS_COMPLETE) {
			isLoading = true;
			mListLoadingHelper.onLoadPage(ListLoadingHelper.LOAD_TYPE_NORMAL);
		}
	}

	/**
	 * 设置LoadingAdapter
	 * 
	 * @param mListLoadingHelper
	 * @author zhangbp
	 */
	public void setLoadingHelper(ListLoadingHelper mListLoadingHelper) {
		this.mListLoadingHelper = mListLoadingHelper;
		if (mListLoadingHelper != null) {
			if (mSmailLoadingView == null) {
				addFooterView(createSmailLoadingView());
				mListLoadingHelper.addLoadStatusListener(mSmailLoadingView);
			}
			mListLoadingHelper.addLoadStatusListener(this);
			setAdapter(mListLoadingHelper.getAdapter());
		}
	}

	/**
	 * 创建footer-loading -view
	 * 
	 * @return
	 * @author zhangbp
	 */
	private View createSmailLoadingView() {
		mSmailLoadingView = (SmailLoadingView) LayoutInflater.from(getContext()).inflate(R.layout.loading_smail_lion, this, false);
		mSmailLoadingView.setReLoadListener(new ReLoadListener() {

			@Override
			public void reLoad() {
				// TODO Auto-generated method stub
				loadPage();
			}
		});

		return mSmailLoadingView;
	}

	@Override
	public void statusChange(int loadStatus, int totalCount,int loadType) {
		// TODO Auto-generated method stub
		lastLoadStatus = loadStatus;
		if (loadStatus != LoadingHelper.LOAD_STATUS_ING) {
			isLoading = false;
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		if (mListLoadingHelper != null) {
			mListLoadingHelper.removeLoadStatusListener(this);
			if (mSmailLoadingView != null) {
				mListLoadingHelper.removeLoadStatusListener(mSmailLoadingView);
			}
		}
		super.onDetachedFromWindow();
	}

}
