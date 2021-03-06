package com.lion.apiDispose;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.easyframework.modules.EasyActionImpl;
import com.easyframework.net.EasyRequestVolley;
import com.easyframework.util.EasyTools;
import com.google.gson.reflect.TypeToken;
import com.lion.component.LoadingHelper;
import com.lion.component.vo.ResultVo;

/**
 * api接口处理
 * 
 * @author zhangbp
 * 
 */

public class LionApi extends EasyActionImpl {

	/**
	 * EasyRequestVolley
	 * 
	 * @param method
	 * @param url
	 * @return
	 */

	public static EasyRequestVolley getEasyRequestVolley(Context appContext, int method, String url) {
		return getEasyRequestVolley(appContext, method, url, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);

	}

	/**
	 * EasyRequestVolley
	 * 
	 * @param method
	 * @param url
	 * @return
	 */

	public static EasyRequestVolley getEasyRequestVolley(Context appContext, int method, String url, int timeoutMs, int mMaxNumRetries) {
		EasyRequestVolley mEasyRequestVolley = new EasyRequestVolley(method, url);
		mEasyRequestVolley.addHeader("X-Client-Event", getClientDataJSONObject(appContext).toString());
		mEasyRequestVolley.setRetryPolicy(new DefaultRetryPolicy(timeoutMs, mMaxNumRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		return mEasyRequestVolley;
	}

	/**
	 * 手机基本信息
	 * 
	 * @param context
	 * @return
	 */
	public static JSONObject getClientDataJSONObject(Context context) {

		TelephonyManager tm = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displaysMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(displaysMetrics);
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		JSONObject clientData = new JSONObject();
		try {

			clientData.put("os_version", EasyTools.getOsVersion(context));
			clientData.put("platform", "android");
			clientData.put("language", Locale.getDefault().getLanguage());

			clientData.put("imei", tm.getDeviceId() == null ? 0 : tm.getDeviceId());//
			clientData.put("manufacturer", Build.MANUFACTURER);
			clientData.put("module_name", Build.PRODUCT);
			clientData.put("model_name", Build.MODEL);
			clientData.put("device_name", EasyTools.getDeviceName());

			clientData.put("resolution", displaysMetrics.widthPixels + "x" + displaysMetrics.heightPixels);
			clientData.put("is_mobiledevice", true);
			clientData.put("phone_type", tm.getPhoneType());//
			clientData.put("imsi", tm.getSubscriberId());
			clientData.put("network", EasyTools.getNetworkTypeWIFI2G3G(context));
			clientData.put("version_name", EasyTools.getVersion(context));
			clientData.put("version_code", EasyTools.getVersionCode(context));
			clientData.put("package_name", EasyTools.getPackageName(context));
			clientData.put("cell", EasyTools.getCellInfo(context));

			clientData.put("wifi_mac", wifiManager.getConnectionInfo().getMacAddress());
			clientData.put("have_bt", adapter == null ? false : true);
			clientData.put("have_wifi", EasyTools.isWiFiActive(context));
			clientData.put("have_gps", locationManager == null ? false : true);
			clientData.put("have_gravity", EasyTools.isHaveGravity(context));
			clientData.put("channel_name", EasyTools.getMetaData(context, "channel_name"));
		} catch (JSONException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return clientData;
	}

	/**
	 * 返回CommonLoadingHelper,针对单页数据加载或数据提交
	 * 
	 * @param mContext
	 * @param mGuildInfo
	 * @return
	 */

	public static CommonLoadingHelper getCommonLoadingHelper(Context mContext, Type mType, String... apiName) {
		CommonLoadingHelper mCommonLoadingHelper = new CommonLoadingHelper(mContext, mType, apiName);
		return mCommonLoadingHelper;
	}

	/**
	 * 返回CommonLoadingHelper,针对单页数据加载或数据提交
	 * 
	 * @param mContext
	 * @param mGuildInfo
	 * @return
	 */

	public static CommonLoadingHelper getCommonLoadingHelper(Context mContext, Type mType, boolean isShowProgRessDialog, String progRessDialogStr, String... apiName) {
		CommonLoadingHelper mCommonLoadingHelper = new CommonLoadingHelper(mContext, mType, isShowProgRessDialog, progRessDialogStr, apiName);
		return mCommonLoadingHelper;
	}

	/**
	 * 返回CommonListLoadingHelper,针对列表加载
	 * 
	 * @param mContext
	 * @param mGuildInfo
	 * @return
	 */

	public static CommonListLoadingHelper getCommonListLoadingHelper(Context mContext, Type mType, String listApiName) {
		return new CommonListLoadingHelper(mContext, mType, listApiName);
	}

	/**
	 * 返回CommonListLoadingHelper,针对列表加载
	 * 
	 * @param mContext
	 * @param mGuildInfo
	 * @return
	 */

	public static CommonListLoadingHelper getCommonListLoadingHelper(Context mContext, Type mType, String listApiName, String... otherAipName) {
		return new CommonListLoadingHelper(mContext, mType, listApiName, otherAipName);
	}

	/**
	 * 提交数据并处理结果
	 * 
	 * @param mContext
	 * @param apiName
	 * @param mRequestParameters
	 * @param isShowProgRessDialog
	 * @param progRessDialogStr
	 * @param mLoadingListening
	 */

	public static void submitData(Context mContext, String apiName, RequestParameters mRequestParameters, boolean isShowProgRessDialog, String progRessDialogStr, LoadHelperListening mLoadingListening) {
		submitData(mContext, apiName, mRequestParameters, isShowProgRessDialog, progRessDialogStr, mLoadingListening, new TypeToken<Map<String, ResultVo<?>>>() {
		}.getType());
	}

	/**
	 * 提交数据并处理结果
	 * 
	 * @param mContext
	 * @param apiName
	 * @param mRequestParameters
	 * @param isShowProgRessDialog
	 * @param progRessDialogStr
	 * @param mLoadingListening
	 */

	public static void submitData(Context mContext, String apiName, RequestParameters mRequestParameters, boolean isShowProgRessDialog, String progRessDialogStr, LoadHelperListening mLoadingListening, Type mType) {
		CommonLoadingHelper mCommonLoadingHelper = getCommonLoadingHelper(mContext, mType, isShowProgRessDialog, progRessDialogStr, apiName);
		mCommonLoadingHelper.setRequestParameters(mRequestParameters);
		mCommonLoadingHelper.setLoadHelperListening(mLoadingListening);
		mCommonLoadingHelper.onLoadPage(LoadingHelper.LOAD_TYPE_NORMAL);
	}
	

	/**
	 * 提交数据并处理结果
	 * 
	 * @param mContext
	 * @param apiName
	 * @param mRequestParameters
	 * @param isShowProgRessDialog
	 * @param progRessDialogStr
	 * @param mLoadingListening
	 */

	public static void submitData(Context mContext, String apiName, RequestParameters mRequestParameters, boolean isShowProgRessDialog, String progRessDialogStr, LoadHelperListening mLoadingListening, Type mType,CommonLoadingHelper mCommonLoadingHelper) {
		mCommonLoadingHelper = mCommonLoadingHelper == null ? getCommonLoadingHelper(mContext, mType, isShowProgRessDialog, progRessDialogStr, apiName) : mCommonLoadingHelper;
		mCommonLoadingHelper.setRequestParameters(mRequestParameters);
		mCommonLoadingHelper.setLoadHelperListening(mLoadingListening);
		mCommonLoadingHelper.onLoadPage(LoadingHelper.LOAD_TYPE_NORMAL);
	}
	
}
