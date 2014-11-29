package com.manuelpeinado.multichoiceadapter.base;

/**
 * @author:Jack Tony
 * @tips  :监听适配器选中item状态的监听器
 * @date  :2014-10-20
 */
public interface OnSelectedStateChangeListener {

	 /**
	 * @param checkedItemCount 已经选中的item数目
	 */
	public void onSelectedStateChanged(int checkedItemCount) ;
}
