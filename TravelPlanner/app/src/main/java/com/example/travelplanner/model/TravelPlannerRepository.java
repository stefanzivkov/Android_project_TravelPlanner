package com.example.travelplanner.model;

import android.content.Context;
import android.util.Log;

import com.example.travelplanner.database.AppDatabase;

import java.util.List;

public class TravelPlannerRepository {

    private DestinationDao destinationDao;
    private WishlistItemDao wishlistItemDao;
    private List<Destination> destinationList;
    private List<WishlistItem> wishlistItemList;
    private AppDatabase database;

    public TravelPlannerRepository(Context context){
        database = AppDatabase.getDatabase(context);
        destinationDao = database.destinationDao();
        wishlistItemDao = database.wishlistItemDao();
        destinationList = destinationDao.getAllDestinations();
        wishlistItemList = wishlistItemDao.getWishList();
    }

    public List<Destination> getAllDestinations(){
        return database.destinationDao().getAllDestinations();
    }

    public List<WishlistItem> getWishList(){
        return wishlistItemList;
    }

    public void insertDestination(Destination destination){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            destinationDao.insertDestination(destination);
        });
    }

    public void insertWishListItem(WishlistItem wishlistItem){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            wishlistItemDao.insertWishListItem(wishlistItem);
        });
    }

    public void deleteDestination(Destination destination){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            destinationDao.deleteDestination(destination);
        });
    }

    public void deleteWishListItem(WishlistItem wishlistItem){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            wishlistItemDao.deleteWishListItem(wishlistItem);
        });
    }

    public Destination getDestination(int id){
        Destination destination = destinationDao.getDestination(id);
        Log.i("destinationnnnnnnnnnnnnnnnnnnnnnnn", destination==null?"yes":"no");
        return destination;
    }
}
