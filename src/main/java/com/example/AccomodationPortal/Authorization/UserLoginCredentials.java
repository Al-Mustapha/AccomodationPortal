package com.example.AccomodationPortal.Authorization;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserLoginCredentials {
    private String username;
    private String password;
}
