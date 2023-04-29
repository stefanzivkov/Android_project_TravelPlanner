package com.example.travelplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.DestinationDao;
import com.example.travelplanner.model.WishlistItem;
import com.example.travelplanner.model.WishlistItemDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Destination.class, WishlistItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DestinationDao destinationDao();
    public abstract WishlistItemDao wishlistItemDao();
    private static AppDatabase INSTANCE;

    private static int NUMBER_OF_THREADS = 1;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "travelPlanner-database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
