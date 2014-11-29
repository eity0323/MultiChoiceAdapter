package com.kale.multichoiceadaptertest;

import java.util.Arrays;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.base.OnSelectedStateChangeListener;
import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceBaseAdapter;

public class BaseAdapterNormalTestActivity extends Activity{

	private MultiBaseAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_normal_layout);
		
		String[] data = {"android","ios","wp","c++",
				 "java","c#","javascript","vb",
				 "delphi","PB","ASP","SQL"};
		
		final LinearLayout settingLL = (LinearLayout)findViewById(R.id.setting_linearLayout);
		settingLL.setVisibility(View.GONE);
		
		ListView listView = (ListView)findViewById(R.id.normal_listView);

		/**
		 * 给listview顶部添加2个额外视图，设置顶部视图不可点击
		 */
		listView.addHeaderView(createHeaderView(), null, false);
		listView.addHeaderView(createHeaderView(), null, false);
		//实例化适配器
		adapter = new MultiBaseAdapter(savedInstanceState, data);
		//添加视图
		adapter.setAdapterView(listView);
		//设置不显示actionMode
		adapter.showActionMode(false);
		//点击事件
		adapter.setOnItemClickListener(new MyItemClick(adapter));
		//监听选中的状态
		adapter.setOnSelectedStateChangeListener(new OnSelectedStateChangeListener() {
			/**
			 * checkedItemCount = 已经选中的item数目
			 */
			@Override
			public void onSelectedStateChanged(int checkedItemCount) {
				if (checkedItemCount != 0) {
					settingLL.setVisibility(View.VISIBLE);
				}
				else {
					settingLL.setVisibility(View.GONE);
				}
			}
		});
	}
	
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        adapter.save(outState);
    }
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_BACK ) {  
        	if (adapter.getCheckedItemCount() > 0) {
        		cancleAll(adapter);
        		return true;
			}				
        } 
        return super.onKeyDown(keyCode, event);
	}  
	
    private View createHeaderView() {
        return LayoutInflater.from(this).inflate(R.layout.view_header, null);
    }
	
	private class MultiBaseAdapter extends MultiChoiceBaseAdapter{

		private String[] mData;
		
		/**
		 * 构造函数
		 * @param savedInstanceState
		 */
		public MultiBaseAdapter(Bundle savedInstanceState,String[] data) {
			super(savedInstanceState);
			// TODO 自动生成的构造函数存根
			mData = data;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			return true;
		}
		

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return false;
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
			return mData.length;
		}

		/**
		 * 通过position来得到相应的item，这里返回object对象
		 */
		@Override
		public Object getItem(int position) {
			// TODO 自动生成的方法存根
			return mData[position];
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
			((TextView)group.findViewById(R.id.item_textView)).setText(mData[position]);
			((CheckBox)group.findViewById(android.R.id.checkbox)).setVisibility(View.VISIBLE);
			return group;
		}
		
		
	}
	
	
	public void buttonListener(View v) {
		switch (v.getId()) {
		case R.id.selectAll_button:
			selectAll(adapter);
			break;
		case R.id.cancle_button:
			cancleAll(adapter);
			break;
		case R.id.delete_button:
			delectItems(adapter);
			break;
		case R.id.share_button:
			Toast.makeText(getApplicationContext(), "分享"+Arrays.toString(getSelectedItems(adapter)), 1).show();
			cancleAll(adapter);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 全选
	 * @param adapter
	 */
	private void selectAll(MultiChoiceBaseAdapter  adapter) {
        for (int i = 0; i < adapter.getCount(); ++i) {
        	adapter.setItemChecked(i, true);
        }
    }
	
	/**
	 * 取消所有选择效果
	 * @param adapter
	 */
	private void cancleAll(MultiChoiceBaseAdapter  adapter) {
        for (int i = 0; i < adapter.getCount(); ++i) {
        	adapter.setItemChecked(i, false);
        }
	}
	
	/**
	 * 得到已经选中的items
	 * @param adapter
	 * @return
	 */
	private String[] getSelectedItems(MultiChoiceBaseAdapter adapter) {
		//得到选中的items
        Set<Long> selection = adapter.getCheckedItems();
        String[] items = new String[selection.size()];
        int i = 0;
        for (long position : selection) {
            items[i++] = (String)adapter.getItem((int)position);
        }
        return items;
	}
	
	/**
	 * 删除已经选中的items
	 * @param adapter
	 */
	private void delectItems(MultiChoiceBaseAdapter  adapter) {
        //通过判断名字来remove掉这些items
        // TODO:删除某些元素，因为这里的数据源是String[]所以没有链表那样好删除，就没去实现。
		//实际中：推荐用链表来动态删除元素，在删除时需要注意的是最好以唯一的id，如position来进行删除
		Toast.makeText(getApplicationContext(), "未实现，请自行实现", 0).show();
        cancleAll(adapter);
    }
	
	/**
	 * @author:Jack Tony
	 * @tips  :点击事件的监听器
	 * @date  :2014-10-20
	 */
	private class MyItemClick implements OnItemClickListener{

		private MultiChoiceBaseAdapter mAdapter;
		
		public MyItemClick(MultiChoiceBaseAdapter adapter) {
			mAdapter = adapter;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			//因为在顶部添加了2个视图，所以这里的位置要下移两位
			
			Toast.makeText(getApplicationContext(), "点击了: " + mAdapter.getItem(position - 2), Toast.LENGTH_SHORT).show();
		}
		
	}
}
