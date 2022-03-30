package com.example.javatask.meeting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetingFilter {
    private Filter filter;
    private String param1;
    private String param2;
}
