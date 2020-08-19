package com.plantscontrol.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plantscontrol.entity.PlantPest;
import com.plantscontrol.entity.dto.PlantPestDTO;

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

    @Query("SELECT pp.id, p1.popularName as plantPopularName, p2.popularName as pestPopularName " +
            "FROM plantPest pp INNER JOIN plant p1 ON p1.id = pp.plantId " +
            "INNER JOIN pest p2 ON p2.id = pp.pestId")
    List<PlantPestDTO> findAllPlantAndPest();

}
