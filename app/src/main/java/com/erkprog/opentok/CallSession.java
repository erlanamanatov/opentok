package com.erkprog.opentok;

/**
 * Created by erlan on 29.06.2017.
 */

public class CallSession {
    private String creatorId;
    private String addresseeId;

    public CallSession(String creatorId, String addresseeId) {
        this.creatorId = creatorId;
        this.addresseeId = addresseeId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(String addresseeId) {
        this.addresseeId = addresseeId;
    }
}
