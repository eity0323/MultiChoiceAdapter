/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manuelpeinado.multichoiceadapter.normal;

import java.lang.reflect.Method;

import com.manuelpeinado.multichoiceadapter.base.MultiChoiceAdapterHelperBase;

import android.app.Activity;
import android.view.ActionMode;
import android.widget.BaseAdapter;

public class MultiChoiceAdapterHelper extends MultiChoiceAdapterHelperBase {
	
    private ActionMode actionMode;
    //是否显示actionmode，默认为显示
	private boolean isShowActionMode = true;
	//是否进入了可以多选的状态
    private boolean isMultiState;

    protected MultiChoiceAdapterHelper(BaseAdapter owner) {
        super(owner);
    }
    
    public void showActionMode(boolean show) {
    	isShowActionMode = show;
    }

    @Override
    protected void startActionMode() {
    	if (isShowActionMode) {
	        try {
	            Activity activity = (Activity) adapterView.getContext();
	            Method method = activity.getClass().getMethod("startActionMode", ActionMode.Callback.class);
	            actionMode = (ActionMode) method.invoke(activity, owner);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
    	}
    	isMultiState = true;
    }

    @Override
    protected void finishActionMode() {
        if (actionMode != null && isShowActionMode) {
            actionMode.finish();
        }
        isMultiState = false;
    	//当关闭actionmode时要更新下item的选择状态，即：关闭选中
    	owner.notifyDataSetChanged();
    }

    @Override
    protected void setActionModeTitle(String title) {
    	if (isShowActionMode) {
    		actionMode.setTitle(title);
    	}
    }

    @Override
    protected boolean isActionModeStarted() {
    	if (isShowActionMode) {
    		return actionMode != null;
		}
    	return isMultiState;
    }

    @Override
    protected void clearActionMode() {
    	if (isShowActionMode) {
    		actionMode = null;
		}
    	isMultiState = false;
    }

	@Override
	public void onSelectedStateChanged(int checkedItemCount) {
		// TODO 自动生成的方法存根
		
	}


}
