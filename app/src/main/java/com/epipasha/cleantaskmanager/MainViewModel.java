package com.epipasha.cleantaskmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MAIN_TAG";
    private LiveData<List<TaskEntry>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Loading tasks");
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        tasks = db.taskDao().loadAllTasks();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }
}
