package com.lion.component;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * 同用adapter
 * 
 * @author zhangbp
 * 
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	public ArrayList<T> mListData = new ArrayList<T>();

	public CommonAdapter() {

	}

	public void appendAllData(ArrayList<? extends T> list) {
		if (list != null) {
			mListData.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 添加单条数据
	 * 
	 * @author zhangbp
	 */
	public void appendData(T itemData) {
		if (itemData != null) {
			mListData.add(itemData);
			notifyDataSetChanged();
		}
	}

	/**
	 * 移除单条数据
	 * 
	 * @author zhangbp
	 */
	public void removeData(int index) {
		mListData.remove(index);
		notifyDataSetChanged();
	}

	/**
	 * 移除单条数据
	 * 
	 * @author zhangbp
	 */
	public void removeData(T itemData) {
		mListData.remove(itemData);
		notifyDataSetChanged();
	}

	/**
	 * 移除多条数据
	 * 
	 * @author zhangbp
	 */
	public void removeAllData(ArrayList<? extends T> list) {
		mListData.removeAll(list);
		notifyDataSetChanged();
	}

	/**
	 * 清除所有数据
	 * 
	 * @author zhangbp
	 */
	public void clearData() {
		mListData.clear();
		notifyDataSetChanged();
	}

	/**
	 * 读取所有的数据
	 * 
	 * @author zhangbp
	 */
	public List<T> getAllData() {
		return mListData;
	}

	/**
	 * 读取所有的数据
	 * 
	 * @author zhangbp
	 */
	@SuppressWarnings("unchecked")
	public void replaceAllData(ArrayList<? extends T> list) {
		mListData = (ArrayList<T>) list;
		notifyDataSetChanged();
	}

}