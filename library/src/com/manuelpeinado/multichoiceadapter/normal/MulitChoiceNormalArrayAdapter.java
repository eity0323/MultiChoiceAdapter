package com.manuelpeinado.multichoiceadapter.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;


public class MulitChoiceNormalArrayAdapter<T> extends MultiChoiceArrayAdapter<T> {

	public MulitChoiceNormalArrayAdapter(Bundle savedInstanceState,
			Context context, int resource, int textViewResourceId) {
		super(savedInstanceState, context, resource, textViewResourceId);
		// TODO 自动生成的构造函数存根
	}
	
	public MulitChoiceNormalArrayAdapter(Bundle savedInstanceState,
			Context context, int resource, int textViewResourceId,
			List<T> objects) {
		super(savedInstanceState, context, resource, textViewResourceId, objects);
		// TODO 自动生成的构造函数存根
	}


	public MulitChoiceNormalArrayAdapter(Bundle savedInstanceState,
			Context context, int resource, int textViewResourceId, T[] objects) {
		this(savedInstanceState,context,resource,textViewResourceId,new ArrayList<T>(Arrays.asList(objects)));
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO 自动生成的方法存根
		return false;
	}

}
