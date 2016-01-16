package com.lion.component;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.easyframework.util.EasyTools;
import com.easyframework.widget.EasyAutoScrollViewPager;
import com.easyframework.widget.EasyBannerAdapter;
import com.lion.R;
import com.lion.component.vo.AdVo;

/**
 * banner
 * 
 * @author zhangbp
 * 
 */
public class LionBanner extends RelativeLayout {

	private EasyAutoScrollViewPager mEasyBanner;
	private ImageView[] dotImageViews;
	private Drawable dot, dot_pressed;
	private MyEasyBannerAdapter myEasyBannerAdapter;
	private int currentPosition;
	private OnClickListener l;
	private LinearLayout mIndicateView;
	private float ratio = 0.0f;
	private int viewWidth, viewHeight;
  
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public LionBanner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		ratio = attrs.getAttributeFloatValue(null, "ratio", 0.0f);
		DisplayMetrics packageMetrics = getContext().getResources().getDisplayMetrics();
		viewWidth = packageMetrics.widthPixels - getPaddingLeft() - getPaddingRight();
		viewHeight = (int) (viewWidth / ratio);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		mEasyBanner = new EasyAutoScrollViewPager(getContext());
		mEasyBanner.setLayoutParams(new RelativeLayout.LayoutParams(viewWidth, viewHeight));
		addView(mEasyBanner);
	}
	
	/**
	 * 添加数据
	 * 
	 * @param datas
	 */

	public void setDatas(ArrayList<AdVo> datas) {
		if (datas != null && datas.size() > 0) {
			myEasyBannerAdapter = new MyEasyBannerAdapter();
			myEasyBannerAdapter.addData(datas);
			mEasyBanner.setAdapter(myEasyBannerAdapter);
			mEasyBanner.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					currentPosition = myEasyBannerAdapter.getFinalPosition(arg0);
					for (int i = 0; i < dotImageViews.length; i++) {
						dotImageViews[i].setImageDrawable(currentPosition == i ? dot_pressed : dot);
					}
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
			if (mIndicateView != null) {
				removeView(mIndicateView);
			}
			mIndicateView = getIndicateView(datas);
			addView(mIndicateView);

			int dataSize = myEasyBannerAdapter.getDataSize();
			mEasyBanner.setCurrentItem(30000 / dataSize * dataSize);
		}
		mEasyBanner.setInterval(5000);
		mEasyBanner.startAutoScroll(5000);
		
		disposeSwipeRefreshLayoutdd();
		
	}
	
	
	/**
	 * 处理 SwipeRefreshLayout 冲突
	 */
	
	private void disposeSwipeRefreshLayoutdd(){
		ViewParent parentView = getParent();
		while(!(parentView == null || parentView instanceof SwipeRefreshLayout)){
			parentView = parentView.getParent();
		}
		if(parentView instanceof SwipeRefreshLayout){
			final SwipeRefreshLayout mLayout = (SwipeRefreshLayout) parentView;
			mEasyBanner.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
			    	
			        mLayout.setEnabled(false);
			        switch (event.getAction()) {
			            case MotionEvent.ACTION_UP:
			                mLayout.setEnabled(true);
			                break;
			        }
			        return false;
			    }
			});
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		if (mEasyBanner != null) {
			mEasyBanner.stopAutoScroll();
		}
	}

	/**
	 * 返回itemView
	 * 
	 * @return
	 */
	private ImageView getImageView() {
		ImageView mImageView = new ImageView(getContext());
		mImageView.setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
		mImageView.setScaleType(ScaleType.FIT_XY);
		return mImageView;
	}

	/**
	 * 指示点
	 * 
	 * @param datas
	 * @return
	 */
	private LinearLayout getIndicateView(ArrayList<AdVo> datas) {
		dot = getResources().getDrawable(R.drawable.dot);
		dot_pressed = getResources().getDrawable(R.drawable.dot_pressed);
		LinearLayout mLinearLayout = new LinearLayout(getContext());
		dotImageViews = new ImageView[datas.size()];

		for (int i = 0, len = datas.size(); i < len; i++) {
			ImageView dotImageView = new ImageView(getContext());
			dotImageView.setImageDrawable(dot);
			mLinearLayout.addView(dotImageView);
			dotImageViews[i] = dotImageView;
		}
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mLinearLayout.setLayoutParams(rl);
		mLinearLayout.setPadding(0, 0, 0, EasyTools.dip2px(getContext(), 5));
		return mLinearLayout;
	}

	/**
	 * 当前的下标
	 * 
	 * @author zhangbp
	 * @return
	 */

	public int getCurrentPosition() {
		return this.currentPosition;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		this.l = l;
	}

	class MyEasyBannerAdapter extends EasyBannerAdapter<AdVo> {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getImageView();
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (LionBanner.this.l != null) {
							LionBanner.this.l.onClick(LionBanner.this);
						}
					}
				});
			}
			loadImage((ImageView) convertView, getItem(position).cover);
			return convertView;
		}
	}

	public void loadImage(ImageView mImageView, String url) {
		
	}

}
