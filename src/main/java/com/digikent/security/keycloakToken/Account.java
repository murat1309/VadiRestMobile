package com.digikent.security.keycloakToken;

import java.util.List;

/**
 * Created by yunusoncel on 9.1.2016.
 */
public class Account {

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [roles = "+roles+"]";
    }

}
