package edu.sjsu.android.groupproject12.list;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Location implements Parcelable {
    private final String name;
    private final boolean visited;

    public Location(String name, boolean visited) {
        this.name = name;
        this.visited = visited;
    }

    protected Location(Parcel in) {
        this.name = in.readString();
        this.visited = in.readBoolean();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) { return new Location(in); }

        @Override
        public Location[] newArray(int size) { return new Location[size]; }
    };

    public String getName() {
        return this.name;
    }

    public boolean getVisited() {
        return this.visited;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeBoolean(this.visited);
    }
}
