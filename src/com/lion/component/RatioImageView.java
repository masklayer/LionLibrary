package com.lion.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * ImageView等比调整大小
 * @author zhangbp
 *
 */
public class RatioImageView extends ImageView {
	private float ratio = 0.0f;
	public RatioImageView(Context paramContext) {
		super(paramContext);
	}

	public RatioImageView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		ratio = paramAttributeSet.getAttributeFloatValue(null, "ratio", 0.0f);
	}

	public void setRatio(float ratio){
		this.ratio = ratio;
	}
	
	public RatioImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		super.onMeasure(paramInt1, paramInt2);
		
		int w = View.MeasureSpec.getSize(paramInt1);
		int j = View.MeasureSpec.getMode(paramInt2);
		if (w > 0 && ratio > 0) {
			int h = (int) (w / ratio);
			paramInt2 = View.MeasureSpec.makeMeasureSpec(h, j);
			setMeasuredDimension(w,paramInt2);
		}
	}
	
}
