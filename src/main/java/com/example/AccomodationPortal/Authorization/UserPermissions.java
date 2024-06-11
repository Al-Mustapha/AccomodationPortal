package com.example.AccomodationPortal.Authorization;

public enum UserPermissions {
    CREATE_STUDENT("student: create"),
    EDIT_STUDENT("student: edit"),
    DELETE_STUDENT("student: delete"),
    ADD_ROOM("room: add"),
    EDIT_ROOM("room: edit"),
    DELETE_ROOM("room: delete");
    private final String permissions;
    UserPermissions(String permissions) {
        this.permissions = permissions;
    }
}
