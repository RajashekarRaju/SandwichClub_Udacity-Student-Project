package com.developersbreach.sandwichclub.list;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Sandwich implements Parcelable {

    // Name of the sandwich
    private final String mSandwichName;
    // Image of the sandwich
    private final String mSandwichImage;
    // List for alternate sandwich names
    private final List<String> mSandwichAlsoKnownAs;
    // Origin place of the sandwich
    private final String mSandwichPlaceOfOrigin;
    // Sandwich description
    private final String mSandwichDescription;
    // List for sandwich ingredients
    private final List<String> mSandwichIngredients;

    // Class constructor
    public Sandwich(String sandwichName, String sandwichImage, List<String> sandwichAlsoKnownAs,
                    String sandwichPlaceOfOrigin, String sandwichDescription, List<String> sandwichIngredients) {
        this.mSandwichName = sandwichName;
        this.mSandwichImage = sandwichImage;
        this.mSandwichAlsoKnownAs = sandwichAlsoKnownAs;
        this.mSandwichPlaceOfOrigin = sandwichPlaceOfOrigin;
        this.mSandwichDescription = sandwichDescription;
        this.mSandwichIngredients = sandwichIngredients;
    }

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

    public List<String> getSandwichIngredients() {
        return mSandwichIngredients;
    }

    private Sandwich(Parcel in) {
        mSandwichName = in.readString();
        mSandwichImage = in.readString();
        mSandwichAlsoKnownAs = in.createStringArrayList();
        mSandwichPlaceOfOrigin = in.readString();
        mSandwichDescription = in.readString();
        mSandwichIngredients = in.createStringArrayList();
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

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSandwichName);
        dest.writeString(mSandwichImage);
        dest.writeStringList(mSandwichAlsoKnownAs);
        dest.writeString(mSandwichPlaceOfOrigin);
        dest.writeString(mSandwichDescription);
        dest.writeStringList(mSandwichIngredients);
    }
}
