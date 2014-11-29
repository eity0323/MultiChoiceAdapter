package com.kale.multichoiceadaptertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceBaseAdapter;

/**
 * 4.0上的用activity
 * 并且导入
 * import android.view.ActionMode;
 * import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceBaseAdapter;
 * 兼容2.0导入ActionbarActivity
 * 并且导入
 * import android.support.v7.view.ActionMode;
 * import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceBaseAdapter;
 */

/**
 * 
 * @author:Jack Tony
 * @tips  :
 * @date  :2014-10-21
 */
public class BaseAdapterTestActivity extends Activity{
	
	private ArrayList<String> items ;
	
	private MultiBaseAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_actionmode_layout);
		
		
		String[] data = {"android","ios","wp","c++",
				 "java","c#","javascript","vb",
				 "delphi","PB","ASP","SQL"};
		items = new  ArrayList<String>(Arrays.asList(data));
		
		ListView list = (ListView)findViewById(R.id.actionMode_listView);
		adapter = new MultiBaseAdapter(savedInstanceState, items);
		adapter.setAdapterView(list);
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Toast.makeText(getApplicationContext(), "点击了"+items.get(position), 0).show();
			}
		});

	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        adapter.save(outState);
    }
	
	private class MultiBaseAdapter extends MultiChoiceBaseAdapter{

		private List<String> mData;
		
		/**
		 * 构造函数
		 * @param savedInstanceState
		 */
		public MultiBaseAdapter(Bundle savedInstanceState,List<String> data) {
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
	        	removeSelectedItems();
	            return true;
	        }
			return false;
		}
	    
	    /**
	     * 删除已经选中的items 
	     */
	    private void removeSelectedItems() {
            // http://stackoverflow.com/a/4950905/244576
            List<Long> positions = new ArrayList<Long>(getCheckedItems());
            Collections.sort(positions, Collections.reverseOrder());
            for (long position : positions) {
                mData.remove((int)position);
            }
            finishActionMode();
	    }

		@Override
		public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
			// TODO 自动生成的方法存根
			return false;
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
	            int layout = R.layout.item;
	            LayoutInflater inflater = LayoutInflater.from(getContext());
	            convertView = inflater.inflate(layout, parent, false);
	        }
			ViewGroup group = (ViewGroup)convertView;
			((TextView)group.findViewById(R.id.item_textView)).setText(mData.get(position));
			((CheckBox)group.findViewById(android.R.id.checkbox)).setVisibility(View.VISIBLE);
			return group;
		}
		
	}

}
