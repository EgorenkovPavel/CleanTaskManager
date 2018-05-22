package com.epipasha.cleantaskmanager;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;
    private Date updateAt;

    public TaskEntry(int id, String description, int priority, Date updateAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Date getUpdateAt() {
        return updateAt;
    }
}
