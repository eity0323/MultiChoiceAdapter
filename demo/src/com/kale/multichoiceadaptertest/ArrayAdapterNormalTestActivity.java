package com.kale.multichoiceadaptertest;

import java.util.Arrays;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.base.OnSelectedStateChangeListener;
import com.manuelpeinado.multichoiceadapter.compat.MulitChoiceNormalArrayAdapter;
import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceArrayAdapter;

/**
 * 如果不用到ActionMode对象的话可以放心大胆的继承activity，也不用导入ActionMode了。
 * 
 * 其实在不用ActionMode时导入
 * import com.manuelpeinado.multichoiceadapter.normal.MulitChoiceNormalArrayAdapter;
 * import com.manuelpeinado.multichoiceadapter.normal.MultiChoiceArrayAdapter;
 * 或者是
 * import com.manuelpeinado.multichoiceadapter.compat.MulitChoiceNormalArrayAdapter;
 * import com.manuelpeinado.multichoiceadapter.compat.MultiChoiceArrayAdapter;
 * 都一样的，只是为了以后的扩展，还是按需导入吧。
 * 4.0以上的导入normal包下面的，兼容2.0的导入compat包下的
 */

public class ArrayAdapterNormalTestActivity extends Activity{
	
	MulitChoiceNormalArrayAdapter<String> normalAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_normal_layout);
		
		final LinearLayout settingLL = (LinearLayout)findViewById(R.id.setting_linearLayout);
		settingLL.setVisibility(View.GONE);
		
		ListView normalListView = (ListView)findViewById(R.id.normal_listView);
		
		String[] data = {"android","ios","wp","c++",
				 "java","c#","javascript","vb",
				 "delphi","PB","ASP","SQL"};
		
		//ArrayList<String> items = new ArrayList<>(Arrays.asList(data));
				
		normalAdapter = new MulitChoiceNormalArrayAdapter<String>(savedInstanceState, 
				getApplicationContext(), R.layout.item, R.id.item_textView, data);
		
		normalAdapter.showActionMode(false);//设置为显示actionmode的普通模式
		normalAdapter.setAdapterView(normalListView);
		normalAdapter.setOnItemClickListener(new MyItemClick(normalAdapter));
		normalAdapter.setOnSelectedStateChangeListener(new OnSelectedStateChangeListener() {
			
			/**
			 * checkedItemCount = 已经选中的item数目
			 */
			@Override
			public void onSelectedStateChanged(int checkedItemCount) {
				if (checkedItemCount > 0) {
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
		normalAdapter.save(outState);
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_BACK ) {  
        	if (normalAdapter.getCheckedItemCount() > 0) {
        		cancleAll(normalAdapter);
        		return true;
			}				
        } 
        return super.onKeyDown(keyCode, event);
	}  

	public void buttonListener(View v) {
		switch (v.getId()) {
		case R.id.selectAll_button:
			selectAll(normalAdapter);
			break;
		case R.id.cancle_button:
			cancleAll(normalAdapter);
			break;
		case R.id.delete_button:
			delectItems(normalAdapter);
			break;
		case R.id.share_button:
			Toast.makeText(getApplicationContext(), "分享"+Arrays.toString(getSelectedItems(normalAdapter)), 1).show();
			cancleAll(normalAdapter);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 全选
	 * @param adapter
	 */
	private void selectAll(MultiChoiceArrayAdapter<String>  adapter) {
        for (int i = 0; i < adapter.getCount(); ++i) {
        	adapter.setItemChecked(i, true);
        }
    }
	
	/**
	 * 取消所有选择效果
	 * @param adapter
	 */
	private void cancleAll(MultiChoiceArrayAdapter<String>  adapter) {
        for (int i = 0; i < adapter.getCount(); ++i) {
        	adapter.setItemChecked(i, false);
        }
	}
	
	/**
	 * 得到已经选中的items
	 * @param adapter
	 * @return
	 */
	private String[] getSelectedItems(MultiChoiceArrayAdapter<String> adapter) {
		//得到选中的items
        Set<Long> selection = adapter.getCheckedItems();
        String[] items = new String[selection.size()];
        int i = 0;
        for (long position : selection) {
            items[i++] = adapter.getItem((int)position);
        }
        return items;
	}
	
	/**
	 * 删除已经选中的items
	 * @param adapter
	 */
	private void delectItems(MultiChoiceArrayAdapter<String>  adapter) {
        //通过判断名字来remove掉这些items
        for (String item : getSelectedItems(adapter)) {
        	/**
        	 * 这里用remove时要注意传入适配器的不能是String[] items对象。否则会报错
        	 * 这里我已经在构造函数中进行了处理，传入String数组也不会出错了~
        	 */
        	adapter.remove(item);
        }
        cancleAll(adapter);
    }
	
	/**
	 * @author:Jack Tony
	 * @tips  :点击事件的监听器
	 * @date  :2014-10-20
	 */
	private class MyItemClick implements OnItemClickListener{

		private MultiChoiceArrayAdapter<String> mAdapter;
		
		public MyItemClick(MultiChoiceArrayAdapter<String> adapter) {
			mAdapter = adapter;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Toast.makeText(getApplicationContext(), "点击了: " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
		}
		
	}
}
