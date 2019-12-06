package com.example.venadostest.vo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.Date;

public class PartidoResumen implements Comparable<PartidoResumen>, Serializable

{
    private boolean local;
    private String opponent;
    private String opponent_image;
    private java.util.Date Date;
    private String league;
    private String image;
    private String home_score;
    private String away_score;


    public PartidoResumen(){

    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getOpponent_image() {
        return opponent_image;
    }

    public void setOpponent_image(String opponent_image) {
        this.opponent_image = opponent_image;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(local ? 1 : 0);
        out.writeString();
    }

    public static final Parcelable.Creator<PartidoResumen> CREATOR
            = new Parcelable.Creator<PartidoResumen>() {
        public PartidoResumen createFromParcel(Parcel in) {
            return new PartidoResumen(in);
        }

        public PartidoResumen[] newArray(int size) {
            return new PartidoResumen[size];
        }
    };
    private PartidoResumen (Parcel in){
        mData =  in.readInt();
    }
*/

    @Override
    public int compareTo(PartidoResumen o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return getDate().compareTo(o.getDate());
    }
}
