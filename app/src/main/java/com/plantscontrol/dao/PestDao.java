package com.plantscontrol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plantscontrol.entity.Pest;

import java.util.List;

@Dao
public interface PestDao {

    @Insert
    Long insert(Pest pest);

    @Update
    void update(Pest pest);

    @Delete
    void delete(Pest pest);

    @Query("SELECT * FROM pest p WHERE p.id = :id")
    Pest findById(Long id);

    @Query("SELECT * FROM pest")
    List<Pest> findAll();

}
