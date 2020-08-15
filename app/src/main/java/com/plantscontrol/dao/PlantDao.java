package com.plantscontrol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plantscontrol.entity.Plant;

import java.util.List;

@Dao
public interface PlantDao {

    @Insert
    Long insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plant p WHERE p.id = :id")
    Plant findById(Long id);

    @Query("SELECT * FROM plant")
    List<Plant> findAll();

}
