package com.example.createrzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.createrzone.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomViewModel customViewModel;
    RequestQueue requestQueue;
    ConnectionDetector connectionDetector;
    CustomAdapter adapter;
    private ActivityMainBinding binding;
    GridLayoutManager layoutManager;
    AlertDialogManager dialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        customViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        customViewModel.getRepository(requestQueue);
        connectionDetector = new ConnectionDetector(MainActivity.this);
        adapter = new CustomAdapter(MainActivity.this);
        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        customViewModel.getList().observe(MainActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> imageList) {
                adapter.setList(imageList);
                adapter.notifyDataSetChanged();
            }
        });

        dialogManager = AlertDialogManager.getInstance(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        MenuItem action_settings = (MenuItem) menu.findItem(R.id.action_settings);
        action_settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dialogManager.showDialog(MainActivity.this, "Choose Grid Columns");
                return true;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String _tag) {
                if (connectionDetector.isConnectingToInternet()) {
                    customViewModel.callAPI(_tag);
                } else {
                    Toast.makeText(MainActivity.this, "Ohh,  no internet here!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void changeColumn(int colNumber) {
        layoutManager = new GridLayoutManager(MainActivity.this, colNumber);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }
}