package com.lion.component.loadingView;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lion.R;
import com.lion.component.LoadingHelper;

/**
 * 第一次加载
 * 
 * @author zhangbp
 */

public class FirstTimeLoadingView extends LoadingView implements OnClickListener {

	private View loadView, errorView, no_data;
	private TextView noContent;

	public FirstTimeLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
		loadView = mLayoutInflater.inflate(R.layout.fullscreen_loading_indicator, this, false);
		errorView = mLayoutInflater.inflate(R.layout.network_error, this, false);
		no_data = mLayoutInflater.inflate(R.layout.no_data, this, false);
		noContent = (TextView) no_data.findViewById(R.id.no_conten);
		View btn = errorView.findViewById(R.id.error_retrybtn);
		View setting_network_btn = errorView.findViewById(R.id.setting_network_btn);

		if (btn != null) {
			btn.setOnClickListener(this);
		}
		if (setting_network_btn != null) {
			setting_network_btn.setOnClickListener(this);
		}
		addView(errorView);
		addView(no_data);
		addView(loadView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.error_retrybtn) {
			reLoadPage();
		} else if (id == R.id.setting_network_btn) {
			getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}
	}

	public void setNoContent(String tipsText) {
		if (noContent != null) {
			noContent.setText(tipsText);
		}

	}

	/**
	 * 加载失败
	 */
	private void loadFailure() {
		hideAllView();
		errorView.setVisibility(VISIBLE);
	}

	/**
	 * 开始加载
	 */
	private void loadStart() {
		hideAllView();
		loadView.setVisibility(VISIBLE);
	}

	/**
	 * 没有内容
	 */
	private void noContent() {
		hideAllView();
		no_data.setVisibility(VISIBLE);
	}

	/**
	 * 隐藏所有的view
	 * 
	 * @author zhangbp
	 */

	private void hideAllView() {
		loadView.setVisibility(INVISIBLE);
		errorView.setVisibility(INVISIBLE);
		no_data.setVisibility(INVISIBLE);
	}

	@Override
	public void statusChange(int loadStatus, int totalCount, int loadType) {
		// TODO Auto-generated method stub
		if (totalCount == 0) {
			if (loadStatus == LoadingHelper.LOAD_STATUS_ING) {
				loadStart();
			} else if (loadStatus == LoadingHelper.LOAD_STATUS_COMPLETE || loadStatus == LoadingHelper.LOAD_STATUS_SUCCEED) {
				noContent();
			} else if (loadStatus == LoadingHelper.LOAD_STATUS_FAILURE) {
				loadFailure();
			}
		} else {
			hideAllView();
		}
	}

}
