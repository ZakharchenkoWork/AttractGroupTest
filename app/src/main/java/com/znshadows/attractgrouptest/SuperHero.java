package com.znshadows.attractgrouptest;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kostya on 04.05.2016.
 */
public class SuperHero{
    //This array is here, because of bitmap which is not serializable and cannot be passed from one
    //Activity to another, with intent extras.
    private static ArrayList<SuperHero> allHeroes = new ArrayList<SuperHero>();

    private int itemId; //: "10056",
    private String name;//: "IronMan",
    private Bitmap image;//: "http://s8.hostingkartinok.com/uploads/images/2016/03/b70762d52599ffc44dc7539bf57baa1c.jpg",
    private String description;//: "heavy armor",x
    private long time;//: "1457018867393"

    public static void addHeroToList(SuperHero hero) {
        allHeroes.add(hero);
    }

    public SuperHero(int itemId, String name, String description, long time) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.time = time;

    }

    public static ArrayList<SuperHero> getAllHeroes() {
        return allHeroes;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }
    /**
     * To get date and time in required format
     */
    public String getConvertedTime() {
        SimpleDateFormat destFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
        Date date = new Date(time);

        return destFormat.format(date);
    }

    public void setTime(long time) {
        this.time = time;
    }


}
