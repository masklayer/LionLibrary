package com.lion.component.loadingView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lion.R;
import com.lion.component.LoadingHelper;

/**
 * LoadingSmailView----------listviewfooter
 * 
 * @author zhangbp
 * 
 */
public class SmailLoadingView extends LoadingView {
	private LinearLayout loadBox;
	private TextView loadTextBtn;

	public SmailLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		loadBox = (LinearLayout) findViewById(R.id.loading_smail_box);
		loadTextBtn = (TextView) findViewById(R.id.loading_smail_reload_btn);
		loadTextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reLoadPage();
			}
		});
	}

	private void loadStart() {
		hideAllView();
		loadBox.setVisibility(VISIBLE);
	}

	private void loadComplete() {
		hideAllView();
		loadTextBtn.setVisibility(VISIBLE);
		loadTextBtn.setText(R.string.load_complete);
		loadTextBtn.setClickable(false);
	}

	private void loadFailure() {
		hideAllView();
		loadTextBtn.setText(R.string.loading_failure_reset);
		loadTextBtn.setClickable(true);
		loadTextBtn.setVisibility(VISIBLE);
	}

	private void hideAllView() {
		loadBox.setVisibility(INVISIBLE);
		loadTextBtn.setVisibility(INVISIBLE);
	}

	@Override
	public void statusChange(int loadStatus, int totalCount, int loadType) {
		// TODO Auto-generated method stub
		if (loadStatus == LoadingHelper.LOAD_STATUS_ING && loadType != LoadingHelper.LOAD_TYPE_PULLREFRESH || loadStatus == LoadingHelper.LOAD_STATUS_SUCCEED) {
			loadStart();
		} else if (loadStatus == LoadingHelper.LOAD_STATUS_COMPLETE) {
			loadComplete();
		} else if (loadStatus == LoadingHelper.LOAD_STATUS_FAILURE) {
			loadFailure();
		}
	}

}
