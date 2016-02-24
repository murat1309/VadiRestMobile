package com.digikent.security.keycloakToken;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunusoncel on 9.1.2016.
 */
public class ResourceAccess {

    private List<String> nameList = new ArrayList<String>();
    private List<Services> commmandClasses = new ArrayList<Services>();

    @JsonAnySetter
    public void setDynamicCommandClass(String name, Services cc) {
        nameList.add(name);
        commmandClasses.add(cc);
    }

    public List<Services> getCommmandClasses() {
        return commmandClasses;
    }

    public void setCommmandClasses(List<Services> commmandClasses) {
        this.commmandClasses = commmandClasses;
    }
}
