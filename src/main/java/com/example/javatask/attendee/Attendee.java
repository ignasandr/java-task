package com.example.javatask.attendee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {
    private UUID id;
    private ZonedDateTime timestamp;
}
