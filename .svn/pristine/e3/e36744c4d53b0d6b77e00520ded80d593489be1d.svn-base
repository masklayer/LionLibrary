package com.lion.component.viewPager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * MarkerViewpager，自动监听OnPageChangeListener并通知Fragment滑动结束
 * 
 * @author zhangbp
 * 
 */
public class LionViewpager extends ViewPager {

	private OnPageChangeListener listener;

	public LionViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		super.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onPageSelected(arg0);
				}
				slideEnd(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onPageScrolled(arg0, arg1, arg2);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onPageScrollStateChanged(arg0);
				}
			}
		});
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		// TODO Auto-generated method stub
		this.listener = listener;
	}

	@Override
	public void setCurrentItem(int item) {
		// TODO Auto-generated method stub
		super.setCurrentItem(item);
		slideEnd(item);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		// TODO Auto-generated method stub
		super.setCurrentItem(item, smoothScroll);
		slideEnd(item);
	}

	/**
	 * 滑动结束
	 * 
	 * @param arg0
	 *            当前的position
	 * @author zhangbp
	 */
	public void slideEnd(final int arg0) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				PagerAdapter mPagerAdapter = getAdapter();
				if (mPagerAdapter instanceof FragmentPagerAdapter) {
					Fragment mFragment = (Fragment) ((FragmentPagerAdapter) mPagerAdapter).instantiateItem(LionViewpager.this, arg0);
					if (mFragment instanceof ViewpagerSlideListener) {
						((ViewpagerSlideListener) mFragment).slideEnd();
					}
				}
			}
		}, 200);
	}

	/**
	 * fragment监听Viewpager滑动结束
	 * 
	 * @author zhangbp
	 * 
	 */
	public interface ViewpagerSlideListener {
		void slideEnd();
	}

}
