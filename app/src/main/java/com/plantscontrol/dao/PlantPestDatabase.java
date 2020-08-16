package com.plantscontrol.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.plantscontrol.entity.Pest;
import com.plantscontrol.entity.Plant;
import com.plantscontrol.entity.PlantPest;

@Database(entities = {Pest.class, Plant.class, PlantPest.class}, version = 1)
public abstract class PlantPestDatabase extends RoomDatabase {

    public abstract PestDao pestDao();
    public abstract PlantDao plantDao();
    public abstract PlantPestDao plantPestDao();

    private static PlantPestDatabase instance;

    public static PlantPestDatabase getDatabase(final Context context) {
        if (instance == null) {

            synchronized (PlantPestDatabase.class) {
                if (instance == null) {
                    Builder builder =  Room.databaseBuilder(context,
                            PlantPestDatabase.class,
                            "plantpest.db");

                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    });

                    instance = (PlantPestDatabase) builder.build();
                }
            }
        }

        return instance;
    }

}
