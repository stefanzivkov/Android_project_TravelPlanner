package com.example.travelplanner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Destination {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "datumOd")
    private String datumOd;

    @ColumnInfo(name = "datumDo")
    private String datumDo;

    @ColumnInfo(name = "description")
    private  String description;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDatumOd() {
        return datumOd;
    }

    public String getDatumDo() {
        return datumDo;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDatumOd(String datumOd) {
        this.datumOd = datumOd;
    }

    public void setDatumDo(String datumDo) {
        this.datumDo = datumDo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
