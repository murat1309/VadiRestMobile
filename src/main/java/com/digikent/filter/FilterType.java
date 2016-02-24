package com.digikent.filter;

/**
 * Created by Onur on 20.12.2015.
 */
public enum FilterType {
    // filterName and parameter may be set from file
    CUSTOMER("GLOBAL_FILTER", "customerId");

    private String filterName;
    private String parameter;

    FilterType(String filterName, String parameter) {
        this.filterName = filterName;
        this.parameter = parameter;
    }

    public String getFilterName() {
        return filterName;
    }

    public String getParameter() {
        return parameter;
    }

}
