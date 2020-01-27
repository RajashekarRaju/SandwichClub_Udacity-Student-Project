package com.developersbreach.sandwichclub.list;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Sandwich implements Parcelable {

    private String mSandwichName;
    private String mSandwichImage;
    private List<String> mSandwichAlsoKnownAs;
    private String mSandwichPlaceOfOrigin;
    private String mSandwichDescription;
    private List<String> mSandwichIngredients;

    public Sandwich(String sandwichName, String sandwichImage, List<String> sandwichAlsoKnownAs,
                    String sandwichPlaceOfOrigin, String sandwichDescription, List<String> sandwichIngredients) {
        this.mSandwichName = sandwichName;
        this.mSandwichImage = sandwichImage;
        this.mSandwichAlsoKnownAs = sandwichAlsoKnownAs;
        this.mSandwichPlaceOfOrigin = sandwichPlaceOfOrigin;
        this.mSandwichDescription = sandwichDescription;
        this.mSandwichIngredients = sandwichIngredients;
    }

    private Sandwich(Parcel in) {
        mSandwichName = in.readString();
        if (in.readByte() == 0) {
            mSandwichImage = null;
        } else {
            mSandwichImage = in.readString();
        }
        mSandwichAlsoKnownAs = in.createStringArrayList();
        mSandwichPlaceOfOrigin = in.readString();
        mSandwichDescription = in.readString();
        mSandwichIngredients = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSandwichName);
        if (mSandwichImage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(mSandwichImage);
        }
        dest.writeStringList(mSandwichAlsoKnownAs);
        dest.writeString(mSandwichPlaceOfOrigin);
        dest.writeString(mSandwichDescription);
        dest.writeStringList(mSandwichIngredients);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sandwich> CREATOR = new Creator<Sandwich>() {
        @Override
        public Sandwich createFromParcel(Parcel in) {
            return new Sandwich(in);
        }

        @Override
        public Sandwich[] newArray(int size) {
            return new Sandwich[size];
        }
    };

    public String getSandwichName() {
        return mSandwichName;
    }

    public String getSandwichImage() {
        return mSandwichImage;
    }

    public List<String> getSandwichAlsoKnownAs() {
        return mSandwichAlsoKnownAs;
    }

    public String getSandwichPlaceOfOrigin() {
        return mSandwichPlaceOfOrigin;
    }

    public String getSandwichDescription() {
        return mSandwichDescription;
    }

    public List<String> getSandwichIngredients() { return mSandwichIngredients; }
}
