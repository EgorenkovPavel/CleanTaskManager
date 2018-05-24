package com.epipasha.cleantaskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extraTaskId";

    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;

    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDescription = findViewById(R.id.etDescription);
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();

                final TaskEntry task = new TaskEntry(description, 1, new Date());
                AppExecutors.getInstance().discIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(mTaskId == DEFAULT_TASK_ID){
                            mDb.taskDao().insertTask(task);
                        }else{
                            task.setId(mTaskId);
                            mDb.taskDao().updateTask(task);
                        }
                        finish();
                    }
                });
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent i = getIntent();
        if(i != null && i.hasExtra(EXTRA_TASK_ID)){
            mTaskId = i.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
            AppExecutors.getInstance().discIO().execute(new Runnable() {
                @Override
                public void run() {
                    final LiveData<TaskEntry> task = mDb.taskDao().loadTaskById(mTaskId);
                    task.observe(DetailActivity.this, new Observer<TaskEntry>() {
                        @Override
                        public void onChanged(@Nullable TaskEntry taskEntry) {
                            populateUI(taskEntry);
                        }
                    });
                }
            });
        }

    }

    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        etDescription.setText(task.getDescription());
    }

}
