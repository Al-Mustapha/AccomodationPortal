package com.example.AccomodationPortal.Authorization;

import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.AccomodationPortal.Authorization.UserPermissions.*;

public enum UserRole {

    STUDENT(Sets.newHashSet(CREATE_STUDENT, EDIT_STUDENT)),
    ADMIN(Sets.newHashSet(CREATE_STUDENT, EDIT_STUDENT,
            DELETE_STUDENT, ADD_ROOM, EDIT_ROOM, DELETE_ROOM));
    private final Set<UserPermissions> permissionsSet;
    UserRole(Set<UserPermissions> permissionsSet) {
        this.permissionsSet = permissionsSet;
    }

    public List<SimpleGrantedAuthority> getPermissionsSet(){
        List<SimpleGrantedAuthority> permissions = permissionsSet.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
