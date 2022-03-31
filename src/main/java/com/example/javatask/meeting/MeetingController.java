package com.example.javatask.meeting;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) { this.meetingService = meetingService; }

    @PostMapping
    public void addMeeting(@RequestBody Meeting meeting) { meetingService.createMeeting(meeting); }

    @GetMapping
    public List<Meeting> getMeetings() { return meetingService.selectAllMeetings(); }

    @DeleteMapping(path= "{meetingId}/{userId}")
    public void deleteMeeting(@PathVariable("meetingId") UUID meetingId, @PathVariable("userId") UUID userId) {
        meetingService.deleteMeeting(meetingId, userId);
    }


    @PostMapping(path="filter")
        public List<Meeting> filterMeetings(@RequestBody MeetingFilter meetingFilter) { return meetingService.filterMeetings(meetingFilter); }
}
