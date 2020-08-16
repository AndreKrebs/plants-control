package com.plantscontrol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plantscontrol.entity.PlantPest;

import java.util.List;

@Dao
public interface PlantPestDao {

    @Insert
    Long insert(PlantPest plantPest);

    @Update
    void update(PlantPest plantPest);

    @Delete
    void delete(PlantPest plantPest);

    @Query("SELECT * FROM plantPest pp WHERE pp.id = :id")
    PlantPest findById(Long id);

    @Query("SELECT * FROM plantPest")
    List<PlantPest> findAll();

}
