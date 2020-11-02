package com.example.ttd.Async;

public class CartItemAsync {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final int mPrice;

    public CartItemAsync(String id, String title, String description, int price) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemAsync cartItem = (CartItemAsync) o;

        if (mPrice != cartItem.mPrice) return false;
        if (!mId.equals(cartItem.mId)) return false;
        if (!mTitle.equals(cartItem.mTitle)) return false;
        return mDescription.equals(cartItem.mDescription);
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mTitle.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mPrice;
        return result;
    }
}
