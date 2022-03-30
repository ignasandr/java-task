package com.example.javatask.attendee;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/meeting/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) { this.attendeeService = attendeeService; }

    @PostMapping(path= "{meetingId}/{attendeeId}")
    public void addAttendee(@PathVariable("meetingId") UUID meetingId, @PathVariable("attendeeId") UUID attendeeId) {
        attendeeService.addAttendee(meetingId, attendeeId);
    }

    @DeleteMapping(path= "{meetingId}/{attendeeId}")
    public void removeAttendee(@PathVariable("meetingId") UUID meetingId, @PathVariable("attendeeId") UUID attendeeId) {
        attendeeService.removeAttendee(meetingId, attendeeId);
    }
}
