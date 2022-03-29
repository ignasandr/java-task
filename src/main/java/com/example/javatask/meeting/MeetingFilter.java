package com.example.javatask.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Filter {
    DESCRIPTION, RESPONSIBLE_PERSON, CATEGORY, TYPE, DATE, ATTENDEES
}
public class MeetingFilter {
    private Filter filter;
    private String param1;
    private String param2;

    public MeetingFilter(@JsonProperty("filter") Filter filter, @JsonProperty("param1") String param1, @JsonProperty("param2") String param2) {
        this.filter = filter;
        this.param1 = param1;
        this.param2 = param2;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
