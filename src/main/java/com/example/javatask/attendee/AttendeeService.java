package com.example.javatask.attendee;

import com.example.javatask.exception.ApiRequestException;
import com.example.javatask.meeting.Meeting;
import com.example.javatask.meeting.MeetingRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendeeService {

    private final MeetingRepository meetingRepository;

    public AttendeeService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public Optional<Attendee> selectAttendeeById(List<Attendee> attendees, UUID id) {
        return attendees.stream()
                .filter(attendee -> attendee.getId().equals(id))
                .findFirst();
    }

    public UUID addAttendee(UUID meetingId, UUID attendeeId) {
        Meeting meeting = meetingRepository.selectMeetingById(meetingId);
        List<Attendee> attendees = meeting.getAttendees();

        if (selectAttendeeById(attendees, attendeeId).isEmpty()) {
            attendees.add(new Attendee(attendeeId, ZonedDateTime.now(ZoneId.of("Z"))));
            meetingRepository.writeToFile();
            return attendeeId;
        } else {
            throw new ApiRequestException("User already attending");
        }
    }

    public void removeAttendee(UUID meetingId, UUID userId) {
        Meeting meeting = meetingRepository.selectMeetingById(meetingId);
        List<Attendee> attendees = meeting.getAttendees();

        if (selectAttendeeById(attendees, userId).isEmpty()) {
            throw new ApiRequestException("User ID not found");
        } else {
            if (meeting.getResponsiblePerson().equals(userId)) {
                throw new ApiRequestException("Can not remove user responsible for the meeting");
            } else {
                attendees.remove(selectAttendeeById(attendees, userId).get());
                meetingRepository.writeToFile();
            }
        }
    }
}
