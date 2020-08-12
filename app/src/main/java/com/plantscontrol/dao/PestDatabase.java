package com.plantscontrol.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.plantscontrol.entity.Pest;

import java.util.concurrent.Executors;

@Database(entities = {Pest.class}, version = 1)
public abstract class PestDatabase extends RoomDatabase {

    public abstract PestDao pestDao();

    private static PestDatabase instance;

    public static PestDatabase getDatabase(final Context context) {
        if (instance == null) {

            synchronized (PestDatabase.class) {
                if (instance == null) {
                    Builder builder =  Room.databaseBuilder(context,
                            PestDatabase.class,
                            "pests.db");

                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            /*Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    carregaTiposIniciais(context);
                                    carregaTiposContatosIniciais(context);
                                }
                            });*/
                        }
                    });

                    instance = (PestDatabase) builder.build();
                }
            }
        }

        return instance;
    }

}
