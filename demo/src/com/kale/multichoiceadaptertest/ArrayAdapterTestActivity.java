package com.kale.multichoiceadaptertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceArrayAdapter;


/**
 * 如果是4.0以上的版本可以直接用activity，不用actionbarActivity，导入的时候导入
 * import android.view.ActionMode;
 * import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceArrayAdapter;
 * 把
 * import android.support.v7.view.ActionMode;
 * import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceArrayAdapter;
 * 删掉
 */

/**
 * @author:Jack Tony
 * @tips  :ActionMode和ArrayAdapter配合实现多选
 * @date  :2014-10-21
 */
public class ArrayAdapterTestActivity extends Activity{
	
	TestAdapter actionModeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_actionmode_layout);
		 
		
		String[] data = {"android","ios","wp","c++",
						 "java","c#","javascript","vb",
						 "delphi","PB","ASP","SQL"};
		
		ListView actionModelistView = (ListView)findViewById(R.id.actionMode_listView);
		
		actionModeAdapter = new TestAdapter(savedInstanceState, this,R.layout.item, R.id.item_textView, data);
		actionModeAdapter.setAdapterView(actionModelistView);
		actionModeAdapter.setOnItemClickListener(new MyItemClick(actionModeAdapter));
		
		//listView.setAdapter(adapter);//不这么用啦
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		actionModeAdapter.save(outState);
	}

	private class TestAdapter extends MultiChoiceArrayAdapter<String>{
		
		String tag = getClass().getSimpleName().toString();
		
		/**
		 * @param savedInstanceState
		 * @param context 
		 * @param resource：Item的布局文件，即 R.layout.xxx
		 * @param textViewResourceId：显示文字的textview的id，即R.id.xxxx
		 * @param objects：一个String数组
		 */
		public TestAdapter(Bundle savedInstanceState, Context context,
				int resource, int textViewResourceId, String[] objects) {
			//为了避免错误，这里将String数组转为ArrayList对象
			this(savedInstanceState,context,resource,textViewResourceId,
					new ArrayList<String>(Arrays.asList(objects)));
		}
		
		/**
		 * @param savedInstanceState
		 * @param context
		 * @param resource：Item的布局文件，即 R.layout.xxx
		 * @param textViewResourceId：显示文字的textview的id，即R.id.xxxx
		 * @param items：一个list<String>对象
		 */
		public TestAdapter(Bundle savedInstanceState, Context context,
				int resource, int textViewResourceId, List<String> items) {
			super(savedInstanceState, context, resource, textViewResourceId, items);
		}
		
		/**
		 * 传入actionMode对象，进行设置图标和操作
		 * 注意要返回true！
		 */
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			Log.i(tag, "onCreateActionMode");
			MenuInflater inflater = mode.getMenuInflater();
		    inflater.inflate(R.menu.my_action_mode, menu);
		    //这里必须要写true，否则会报空指针！！！
			return true;
		}
			
		
		/**
		 * 进行AndroidMode上图标操作的设置
		 */
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        if (item.getItemId() == R.id.menu_share) {
	            Toast.makeText(getContext(), "分享了" + getCheckedItemCount()+"个item", Toast.LENGTH_SHORT).show();
	            return true;
	        }
	        if (item.getItemId() == R.id.menu_discard) {
	        	//丢弃选中的items
	            discardSelectedItems();
	            return true;
	        }
	        return false;
	    }

		@Override
		public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
			Log.i(tag, "onPrepareActionMode");
			return false;
		}
		
		/**
		 * （非必须）
		 * 设置点击、选中效果，非必须。如果你在item的layout设置了 android:background那么下面就不用进行设置背景了
		 * 个人推荐在item的layout中设置背景色，example：android:background="@drawable/custom_list_item_background"
		 */
		/*@Override
	    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
			//Log.i(tag, "getViewImpl");
	        View view = super.getViewImpl(position, convertView, parent);
	        view.setBackgroundResource(R.drawable.custom_list_item_background);
	        return view;
	    }*/
		
		/**
		 * 从适配器中移除某些item
		 */
		private void discardSelectedItems() {
			//得到选中的items
	        Set<Long> selection = getCheckedItems();
	        String[] items = new String[selection.size()];
	        int i = 0;
	        for (long position : selection) {
	            items[i++] = getItem((int)position);
	        }
	        
	        //通过判断名字来remove掉这些items
	        for (String item : items) {
	        	/**
	        	 * 这里用remove时要注意传入适配器的不能是String[] items对象。否则会报错
	        	 * 这里我已经在构造函数中进行了处理，传入String数组也不会出错了~
	        	 */
	            remove(item);
	        }
	        
	        finishActionMode();
	    }	
		
	}
	
	/**
	 * @author:Jack Tony
	 * @tips  :点击事件的监听器
	 * @date  :2014-10-20
	 */
	private class MyItemClick implements OnItemClickListener{

		private TestAdapter mAdapter;
		
		public MyItemClick(TestAdapter adapter) {
			mAdapter = adapter;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Toast.makeText(getApplicationContext(), "点击了: " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
		}
		
	}
	

}

