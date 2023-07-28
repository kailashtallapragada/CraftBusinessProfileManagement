package com.ktcraft.pvs.service.profile.vo.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProfileValidationStatus {
    IN_PROGRESS("in_progress"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    COMPLETED("completed");

    private final String profileValidationStatus;

    private ProfileValidationStatus(final String profilevalidationStatus) {
        this.profileValidationStatus = profilevalidationStatus;
    }

    @JsonValue
    public String getProfileValidationStatus() {
        return this.profileValidationStatus;
    }
}
