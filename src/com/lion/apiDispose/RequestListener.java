package com.lion.apiDispose;

import java.lang.reflect.Type;
import java.util.Map;

import android.content.Context;

import com.android.volley.VolleyError;
import com.easyframework.json.EasyJson;
import com.easyframework.net.EasyRequestVolleyListener;
import com.lion.R;
import com.lion.component.vo.ResultVo;

/**
 * 带dialog加载的RequestListener
 * 
 * @author zhangbp
 * 
 */

public class RequestListener extends EasyRequestVolleyListener<Object> {

	private Type mType;
	private boolean isShowLoading;
	private String loadTipsStr;
	private Context mContext;
	private LionProgRessDialog mMarketProgRessDialog;

	public RequestListener(Context mContext, Type mType) {
		this(mContext, mType, false, "");
	}

	public RequestListener(Context mContext, Type mType, boolean isShowLoading) {
		this(mContext, mType, isShowLoading, mContext.getString(R.string.toast_1));
	}

	public RequestListener(Context mContext, Type mType, boolean isShowLoading, String loadTipsStr) {
		this.mType = mType;
		this.isShowLoading = isShowLoading;
		this.loadTipsStr = loadTipsStr;
		this.mContext = mContext;
	}

	@Override
	public void onPre(String httpUrl) {
		// TODO Auto-generated method stub
		if (isShowLoading) {
			loadTipsStr = loadTipsStr != null && !loadTipsStr.equals("") ? loadTipsStr : mContext.getString(R.string.toast_1);
			mMarketProgRessDialog = new LionProgRessDialog(mContext, loadTipsStr);
			mMarketProgRessDialog.show();
		}
	}

	@Override
	public Object onProcess(String httpUrl, String data) {
		// TODO Auto-generated method stub
		try {
			if (data != null && !data.equals("")) {
				return EasyJson.toBean(data, mType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onProcess(httpUrl, data);
	}

	@Override
	public void onComplete(String httpUrl, Object data) {
		// TODO Auto-generated method stub

		if (mMarketProgRessDialog != null) {
			mMarketProgRessDialog.dismiss();
		}
	}

	/**
	 * 是否有访问权限
	 * 
	 * @return
	 */
	public boolean isPermission(Object data, String... apiName) {
		if (apiName != null) {
			if (data != null && data instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, ResultVo<?>> list = (Map<String, ResultVo<?>>) data;
				ResultVo<?> mBaseVo = list.get(apiName[0]);
				if (mBaseVo != null && mBaseVo.code != null && mBaseVo.code.equals("1010")) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		onComplete("", null);
	}
}
