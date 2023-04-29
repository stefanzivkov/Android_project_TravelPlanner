package com.example.travelplanner.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishlistItemDao {

    @Query("SELECT * FROM wishlistitem")
    List<WishlistItem> getWishList();

    @Insert
    void insertWishListItem(WishlistItem... wishlistItems);

    @Delete
    void deleteWishListItem(WishlistItem wishlistItemw);

}
