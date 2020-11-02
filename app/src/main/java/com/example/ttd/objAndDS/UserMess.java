package com.example.ttd.objAndDS;

import java.util.List;

public class UserMess {
    private final String mUserId;
    private final String mFullName;
    private final String mImageUrl;

    public UserMess(String userId, String fullName, String imageUrl) {
        mUserId = userId;
        mFullName = fullName;
        mImageUrl = imageUrl;
    }

    public void logOut() {
        // real implementation here
    }

    public void connectWith(UserObject otherUser) {
        // real implementation here
    }

    public List<UserObject> getConnectedUsers() {
        // real implementation here
        return null;
    }

    public void disconnectFromAll() {
        // real implementation here
    }

    public String getUserId() {
        return mUserId;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
