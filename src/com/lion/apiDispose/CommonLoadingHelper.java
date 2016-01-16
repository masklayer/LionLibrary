package com.lion.apiDispose;

import java.lang.reflect.Type;
import java.util.Map;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.lion.component.LoadingHelper;
import com.lion.component.vo.ResultVo;

/**
 * 非列表加载,数据提交,支持一次多个接口
 * 
 * @author zhangbp
 */

public class CommonLoadingHelper extends LoadingHelper {
	private String[] apiName;
	public RequestParameters mRequestParameters;
	private Context mContext;
	private Type mType;
	private boolean isShowProgRessDialog = false;
	private String progRessDialogStr;
	private String finalRequestApiCode;
	private LoadHelperListening mLoadHelperListening;

	public CommonLoadingHelper(Context mContext, Type mType, String... apiName) {
		this.mContext = mContext;
		// TODO Auto-generated constructor stub
		this.apiName = apiName;
		this.mType = mType;
	}

	public CommonLoadingHelper(Context mContext, Type mType, boolean isShowProgRessDialog, String progRessDialogStr, String... apiName) {
		this.mContext = mContext;
		// TODO Auto-generated constructor stub
		this.apiName = apiName;
		this.mType = mType;
		this.isShowProgRessDialog = isShowProgRessDialog;
		this.progRessDialogStr = progRessDialogStr;
	}

	/**
	 * 设置请求的参数
	 * 
	 * @param mRequestParameters
	 * @return
	 */

	public CommonLoadingHelper setRequestParameters(RequestParameters mRequestParameters) {
		this.mRequestParameters = mRequestParameters;
		return this;
	}

	@Override
	public void onLoadPage(final int loadType) {
		// TODO Auto-generated method stub
		onLoadPage(loadType, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);
	}

	public void onLoadPage(final int loadType, int timeoutMs, int mMaxNumRetries) {

		// TODO Auto-generated method stub
		if (mRequestParameters == null) {
			throw new NullPointerException("RequestParameters is null");
		}

		notifyStatusChange(LOAD_STATUS_ING, 0, loadType);
		String requestParam = mRequestParameters.getFinalParameter();
		String httpUrl = getRequestUrl();
		LionApi.getEasyRequestVolley(mContext.getApplicationContext(),Method.POST, httpUrl, timeoutMs, mMaxNumRetries).addParameters("data", requestParam).executeAsync(mContext, new RequestListener(mContext, mType, isShowProgRessDialog, progRessDialogStr) {
			@Override
			public void onComplete(String httpUrl, Object data) {
				// TODO Auto-generated method stub
				super.onComplete(httpUrl, data);
				CommonLoadingHelper.this.isPermission(isPermission(data, apiName));
				if (apiName.length == 1) { // 单个api
					if (data != null) {
						@SuppressWarnings("unchecked")
						Map<String, ResultVo<?>> list = (Map<String, ResultVo<?>>) data;

						ResultVo<?> mBaseVo = list.get(apiName[0]);

						if (mBaseVo.isSuccess) {
							notifyStatusChange(LOAD_STATUS_SUCCEED, mBaseVo.count, loadType);
						} else {
							notifyStatusChange(LOAD_STATUS_FAILURE, 0, loadType);
						}
						data = mBaseVo;
					} else {
						notifyStatusChange(LOAD_STATUS_FAILURE, 0, loadType);
					}
				}
				if (mLoadHelperListening != null) {
					mLoadHelperListening.loadComplete(data, loadType);
				}
			}
		});
	}

	/**
	 * 是否有访问权限
	 * 
	 * @return
	 */
	public void isPermission(boolean isPermission) {

	}

	/**
	 * 设置加载完成的监听器
	 * 
	 * @param mLoadHelperListening
	 */

	public void setLoadHelperListening(LoadHelperListening mLoadHelperListening) {
		this.mLoadHelperListening = mLoadHelperListening;
	}

	/**
	 * 返回请求的Url
	 * 
	 * @return
	 * @author zhangbp
	 */
	private String getRequestUrl() {
		if (finalRequestApiCode == null || finalRequestApiCode.equals("")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < apiName.length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(apiName[i]);
			}
			finalRequestApiCode = sb.toString();
		}
		return String.format(ApiList.ApiUrl, finalRequestApiCode, mRequestParameters.getCacheKey());
	}

}
