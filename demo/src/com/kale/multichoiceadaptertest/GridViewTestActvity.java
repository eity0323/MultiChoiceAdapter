package com.kale.multichoiceadaptertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.base.OnSelectedStateChangeListener;
import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceBaseAdapter;


public class GridViewTestActvity extends ActionBarActivity{

	MultiBaseAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_layout);
		
		Integer[] pics = {R.drawable.aig,R.drawable.bank_of_america,R.drawable.chrysler,
				R.drawable.empire_state,R.drawable.nyt,R.drawable.one_wtc};

		List<Integer> items = new ArrayList<>(Arrays.asList(pics));
		
		GridView gridView = (GridView)findViewById(R.id.gridLayout);
		
		adapter = new MultiBaseAdapter(savedInstanceState, items);
		adapter.setAdapterView(gridView);
		//adapter.showActionMode(false);//开启后变成普通模式，就不需要实现actionmode了
		adapter.setOnSelectedStateChangeListener(new OnSelectedStateChangeListener() {
			
			@Override
			public void onSelectedStateChanged(int checkedItemCount) {
				// TODO 自动生成的方法存根
				Toast.makeText(getApplicationContext(), "选中了"+checkedItemCount+"个", 0).show();
			}
		});
		
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        adapter.save(outState);
    }
	
	private class MultiBaseAdapter extends MultiChoiceBaseAdapter{

		private List<Integer> mData;
		
		/**
		 * 构造函数
		 * @param savedInstanceState
		 */
		public MultiBaseAdapter(Bundle savedInstanceState,List<Integer> data) {
			super(savedInstanceState);
			// TODO 自动生成的构造函数存根
			mData = data;
		}

		/**
		 * 创建ActionMode对象，必须返回true！！！
		 */
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			 MenuInflater inflater = mode.getMenuInflater();
		     inflater.inflate(R.menu.my_action_mode, menu);
			return true;
		}
		
		/**
		 * item点击时处理的事件，从ActionMode.callback中继承过来的
		 */
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			if (item.getItemId() == R.id.menu_share) {
	            Toast.makeText(getContext(), "分享了" + getCheckedItemCount() + "item", Toast.LENGTH_SHORT).show();
	            return true;
	        }
	        if (item.getItemId() == R.id.menu_discard) {
	        	//TODO:删除选中的元素
	        	Toast.makeText(getContext(), "未实现，请自行实现", 0).show();
	            return true;
	        }
			return false;
		}
		
		

		@Override
		public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
			// TODO 自动生成的方法存根
			return false;
		}
		
		/**
		 * 系统在item选中状态被改变的时候会调用getActionModeTitle()来得到要显示的标题字样
		 * 这里得到被选中的items数目，可以用来改变actionmode上的标题字样。
		 * 下面想返回字符串就是标题了
		 */
		@Override
		public String getActionModeTitle(int selected) {
			return String.format("自定义标题：%d / %d", selected, getCount());
		}

		/**
		 * 看适配器中有多少元素需要加载
		 */
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mData.size();
		}

		/**
		 * 通过position来得到相应的item，这里返回object对象
		 */
		@Override
		public Object getItem(int position) {
			// TODO 自动生成的方法存根
			return mData.get(position);
		}

		/**
		 * 通过position得到id
		 */
		@Override
		public long getItemId(int position) {
			// TODO 自动生成的方法存根
			return position;
		}

		/**
		 * 返回item的view对象
		 */
		@Override
		protected View getViewImpl(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
	            int layout = R.layout.item_gridview;
	            LayoutInflater inflater = LayoutInflater.from(getContext());
	            convertView = inflater.inflate(layout, parent, false);
	        }
			ViewGroup group = (ViewGroup)convertView;
			((ImageView)group.findViewById(R.id.item_imageView)).setImageResource((mData.get(position)));
			return group;
		}
		
	}
}
