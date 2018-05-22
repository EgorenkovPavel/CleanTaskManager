package com.epipasha.cleantaskmanager;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

public interface TaskDao {

    @Query("SELECT * FROM task")
    List<TaskEntry> loadAllTasks();

    @Insert
    void insertTask();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask();

    @Delete
    void deleteTask();

}
