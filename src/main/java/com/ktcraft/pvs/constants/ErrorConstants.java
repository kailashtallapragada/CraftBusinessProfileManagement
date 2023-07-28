package com.ktcraft.pvs.constants;

public enum ErrorConstants {

    InvalidInput("The input provided is not valid"),
    DuplicateProfile("A profile already exists for this organization."),
    DuplicateProfileUpdateRequest("A profile update request already exists for this organization."),
    ProfileNotFound("A profile does not exist for this organization."),
    ProfileVerificationNotAccepted("Your profile verification request could not be accepted. Please try again later"),
    ProfileUpdateRequestNotFound("A profile update request does not exist for this organization."),
    ServiceUnavailable("The requested service is currently unavailable"),
    InvalidAuthToken("Invalid authentication token.");

    private String message;

    ErrorConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
