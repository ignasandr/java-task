package com.example.javatask.meeting;

import com.example.javatask.attendee.Attendee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private UUID id;
    private String name;
    @JsonProperty(value = "responsible")
    private UUID responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    @JsonProperty(value = "start")
    private LocalDateTime startDate;
    @JsonProperty(value = "end")
    private LocalDateTime endDate;

    private List<Attendee> attendees;
}
