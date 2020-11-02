package com.example.ttd.Async;

public class CartItemSchemaAsync {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final int mPrice;

    public CartItemSchemaAsync(String id, String title, String description, int price) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mPrice = price;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getPrice() {
        return mPrice;
    }
}
