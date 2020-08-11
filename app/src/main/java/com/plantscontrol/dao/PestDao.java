package com.plantscontrol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plantscontrol.entity.Pest;

@Dao
public interface PestDao {

    @Insert
    Long insert(Pest pest);

    @Update
    void update(Pest pest);

    @Delete
    void delete(Pest pest);

    @Query("SELECT * FROM ")
    Pest findById(Long id);

}
