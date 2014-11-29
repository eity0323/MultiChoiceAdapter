package com.kale.multichoiceadaptertest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		/*startActivity(new Intent(MainActivity.this,GridViewTestActvity.class));
		finish();*/
	}
	
	public void buttonListener (View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.actionMode_arrayAdapter_button:
			intent.setClass(getApplicationContext(), ArrayAdapterTestActivity.class);
			break;
		case R.id.normal_arrayAdapter_button:
			intent.setClass(getApplicationContext(), ArrayAdapterNormalTestActivity.class);
			break;
		case R.id.actionMode_baseAdapter_button:
			intent.setClass(getApplicationContext(), BaseAdapterTestActivity.class);
			break;
			case R.id.normal_baseAdapter_button:
			intent.setClass(getApplicationContext(), BaseAdapterNormalTestActivity.class);
			break;
			case R.id.actionMode_gridAdapter_button:
			intent.setClass(getApplicationContext(), GridViewTestActvity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}
	

}
