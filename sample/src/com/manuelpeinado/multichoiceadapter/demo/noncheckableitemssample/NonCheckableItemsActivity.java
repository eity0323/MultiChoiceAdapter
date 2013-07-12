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
package com.manuelpeinado.multichoiceadapter.demo.noncheckableitemssample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;
import com.manuelpeinado.multichoiceadapter.demo.HomeActivity;
import com.manuelpeinado.multichoiceadapter.demo.R;

public class NonCheckableItemsActivity extends SherlockActivity implements OnItemClickListener {
    private MultiChoiceBaseAdapter adapter;
    private ArrayList<String> countries = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countries = loadCountries(this);
        rebuildList(savedInstanceState);
    }

    public static ArrayList<String> loadCountries(Context context) {
        try {
            ArrayList<String> countries = new ArrayList<String>();
            InputStream inputStream = context.getResources().openRawResource(R.raw.countries);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                countries.add(line);
            }
            reader.close();
            return countries;
        } catch (IOException e) {
            return null;
        }
    }

    private ListView getListView() {
        return (ListView) findViewById(android.R.id.list);
    }

    public void onItemClick(android.widget.AdapterView<?> adapterView, View view, int position, long id) {
        if (adapter.isItemCheckable(position)) {
            Toast.makeText(this, "Item click: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            Intent parentActivityIntent = new Intent(this, HomeActivity.class);
            parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            finish();
            return true;
        case R.id.menu_select_all:
            selectAll();
            return true;
        case R.id.menu_reset_list:
            rebuildList(null);
            return true;
        }
        return false;
    }

    private void selectAll() {
        for (int i = 0; i < adapter.getCount(); ++i) {
            adapter.setItemChecked(i, true);
        }
    }

    private void rebuildList(Bundle savedInstanceState) {
        adapter = new NonCheckableItemsAdapter(savedInstanceState, countries);
        adapter.setOnItemClickListener(this);
        adapter.setAdapterView(getListView());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        adapter.save(outState);
    }

}