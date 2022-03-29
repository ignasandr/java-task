package com.example.javatask.meeting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeetingDao {

    int createMeeting(UUID id, Meeting meeting);

    default int createMeeting(Meeting meeting) {
        UUID id = UUID.randomUUID();
        return createMeeting(id, meeting);
    }

    List<Meeting> selectAllMeetings();

    Optional<Meeting> selectMeetingById(UUID id);

    int deleteMeeting(UUID meetingId, UUID userId);

    Optional<MeetingAttendee> selectAttendeeById(List<MeetingAttendee> attendees, UUID id);

    int addPerson(UUID meetingId, UUID userId);

    int removePerson(UUID meetingId, UUID userId);

    List<Meeting> filterMeetings(MeetingFilter meetingFilter);
}
