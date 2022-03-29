package com.example.javatask.meeting;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MeetingAttendee {
    private UUID id;
    private ZonedDateTime timestamp;

    public MeetingAttendee(UUID id, ZonedDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}
