package ru.art241111.dish_recipes.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model of dishes.
 * @author Artem Geraimov.
 */
public class FullDish implements Parcelable {
    // Image resource
    private int imageDish;
    // Title of dish
    private String nameDish;
    // Description dish
    private String descriptionDish;
    // Dish recipe
    private String recipe;

    // Constructors.
    /**
     * Constructor with all elements
     * @param imageDish - directory image
     * @param nameDish - title of dish
     * @param descriptionDish - description of dish
     * @param recipe - dish recipe
     */
    public FullDish(int imageDish, String nameDish, String descriptionDish, String recipe) {
        this.imageDish = imageDish;
        this.nameDish = nameDish;
        this.descriptionDish = descriptionDish;
        this.recipe = recipe;
    }

    /**
     * Empty constructor
     */
    public FullDish() {}

    /**
     * Constructor to Parcel
     * @param in - parcel
     */
    protected FullDish(Parcel in) {
        imageDish = in.readInt();
        nameDish = in.readString();
        descriptionDish = in.readString();
        recipe = in.readString();
    }

    // Migrate to parcelable.
    public static final Creator<FullDish> CREATOR = new Creator<FullDish>() {
        @Override
        public FullDish createFromParcel(Parcel in) {
            return new FullDish(in);
        }

        @Override
        public FullDish[] newArray(int size) {
            return new FullDish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageDish);
        dest.writeString(nameDish);
        dest.writeString(descriptionDish);
        dest.writeString(recipe);
    }

    // Setters and getters.
    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getImageDish() {
        return imageDish;
    }

    public void setImageDish(int imageDish) {
        this.imageDish = imageDish;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public String getDescriptionDish() {
        return descriptionDish;
    }

    public void setDescriptionDish(String descriptionDish) {
        this.descriptionDish = descriptionDish;
    }


}
