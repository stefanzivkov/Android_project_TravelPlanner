package com.example.travelplanner.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DestinationDao {

    @Query("SELECT * FROM destination")
    List<Destination> getAllDestinations();

    @Insert
    void insertDestination(Destination... destinations);

    @Delete
    void deleteDestination(Destination destination);

    @Query("SELECT * FROM destination WHERE id = :idD")
    Destination getDestination(int idD);
}
