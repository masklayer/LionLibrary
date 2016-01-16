package com.lion.component.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lion.R;

/**
 * tab-bar
 * 
 * @author zhangbp
 */

public class TabBarView extends LinearLayout implements OnClickListener {

	private CallBack mcallBack;
	private TextView[] itemViews;

	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < itemViews.length; i++) {
			if (v == itemViews[i]) {
				selectItem(i);
				if (mcallBack != null) {
					mcallBack.call(((TextView) v).getText().toString(), i);
				}
			}
		}

	}

	/**
	 * 设置tabs
	 * 
	 * @param datas
	 */
	public void setData(CharSequence[] datas) {
		removeAllViews();
		if (datas != null) {
			itemViews = new TextView[datas.length];
			for (int i = 0, len = datas.length; i < len; i++) {
				addItemView(datas[i], i);
			}
			setVisibility(VISIBLE);
		} else {
			setVisibility(GONE);
		}
	}

	/**
	 * 添加itemView
	 * 
	 * @param name
	 * @param index
	 */
	private void addItemView(CharSequence name, int index) {
		TextView mTextView = new TextView(getContext());
		LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
		lp.weight = 1.0f;
		mTextView.setLayoutParams(lp);
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextSize(18);
		mTextView.setBackgroundResource(R.drawable.tab_bar_bg);
		mTextView.setText(name);
		mTextView.setTextColor(getResources().getColorStateList(R.drawable.tab_bar_color));
		mTextView.setOnClickListener(this);
		itemViews[index] = mTextView;
		addView(mTextView);
	}

	/**
	 * 选中一个tab
	 * 
	 * @param index
	 */

	public void selectItem(int index) {
		if (itemViews != null && itemViews.length > index) {

			for (int i = 0; i < itemViews.length; i++) {
				if (index == i) {
					itemViews[i].setSelected(true);
				} else {
					itemViews[i].setSelected(false);
				}
			}
		}
	}

	/**
	 * 设置callBack
	 * 
	 * @param mcallBack
	 */
	public void setCallBack(CallBack mcallBack) {
		this.mcallBack = mcallBack;
	}

	public interface CallBack {
		public void call(String name, int index);
	}

}
