package com.lion.component;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载助手
 * 
 * @author zhangbp
 */

public abstract class LoadingHelper {

	public static final int LOAD_TYPE_NORMAL = 1;
	public static final int LOAD_TYPE_PULLREFRESH = 2;
	public static int LOAD_STATUS_ING = 1;
	public static int LOAD_STATUS_SUCCEED = 2;
	public static int LOAD_STATUS_FAILURE = 3;
	public static int LOAD_STATUS_COMPLETE = 4;
	public int lastLoadStatus,lastTotalCount,lastLoadType;
	public List<LoadStatusListener> loadingListeners = new ArrayList<LoadStatusListener>();

	/**
	 * 加载数据
	 */
	public abstract void onLoadPage(int loadType);

	/**
	 * 加载状态监听接口
	 * 
	 * @author zhangbp
	 * 
	 */

	/**
	 * 添加加载的监听器
	 * 
	 * @param mLoadingListener
	 * @author zhangbp
	 */
	public synchronized void addLoadStatusListener(LoadStatusListener mLoadStatusListener) {
		synchronized (this) {
			if (mLoadStatusListener != null && !loadingListeners.contains(mLoadStatusListener))
				loadingListeners.add(mLoadStatusListener);
		}
	}

	/**
	 * 移除加载的监听器
	 * 
	 * @param mLoadingListener
	 * @author zhangbp
	 */

	public synchronized void removeLoadStatusListener(LoadStatusListener mLoadingListener) {
		loadingListeners.remove(mLoadingListener);
	}

	/**
	 * 　通知监听状态发生变化
	 * 
	 * @param type
	 * @author zhangbp
	 */

	public void notifyStatusChange(int loadStatus, int totalCount,int loadType) {
		int size = 0;
		LoadStatusListener[] arrays = null;
		lastLoadStatus = loadStatus;
		lastTotalCount = totalCount;
		lastLoadType = loadType;
		synchronized (this) {
			size = loadingListeners.size();
			arrays = new LoadStatusListener[size];
			loadingListeners.toArray(arrays);
		}
		if (arrays != null) {
			for (LoadStatusListener observer : arrays) {
				observer.statusChange(loadStatus, totalCount,loadType);
			}
		}
	}

	/**
	 * 返回最后一次的状态
	 * 
	 * @return
	 * @author zhangbp
	 */

	public int getlastLoadStatus() {
		return this.lastLoadStatus;
	}
	
	/**
	 * 返回最后一次加载的总数
	 * @return
	 * @author zhangbp
	 */

	public int getlastTotalCount() {
		return this.lastTotalCount;
	}
	
	/**
	 * 返回最后一次的加载类型
	 * 
	 * @return
	 * @author zhangbp
	 */

	public int getlastLoadType() {
		return this.lastLoadStatus;
	}

	public interface LoadStatusListener {
		public void statusChange(int loadStatus, int totalCount,int loadType);
	}
}
