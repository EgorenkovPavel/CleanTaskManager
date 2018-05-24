package com.epipasha.cleantaskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapter.ItemClickListener {

    private static final String TAG = "MAIN_TAG";
    private AppDatabase mDb;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TaskEntry task = new TaskEntry("Test", 1, new Date());
                AppExecutors.getInstance().discIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Adding task");
                        mDb.taskDao().insertTask(task);
                    }
                });

            }
        });

        RecyclerView rvList = findViewById(R.id.rvList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rvList.getContext(),
                layoutManager.getOrientation());
        rvList.addItemDecoration(mDividerItemDecoration);

        mAdapter = new ListAdapter(this);
        rvList.setAdapter(mAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());

        retrieveTasks();
    }

    private void retrieveTasks() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntry> taskEntries) {
                mAdapter.setTasks(taskEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }
}
