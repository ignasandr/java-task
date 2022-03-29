package com.example.javatask.meeting;

import com.example.javatask.exception.ApiRequestException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository("mockDao")
public class MeetingMockDataAccessService implements MeetingDao {

    private static List<Meeting> DB = new ArrayList<>();

    @Override
    public int createMeeting(UUID id, Meeting meeting) {
        List<MeetingAttendee> attendees = new ArrayList<>();
        attendees.add(new MeetingAttendee(meeting.getResponsiblePerson(), ZonedDateTime.now(ZoneId.of("Z"))));
        DB.add(new Meeting(id, meeting.getName(), meeting.getResponsiblePerson(),
                meeting.getDescription(), meeting.getCategory(), meeting.getType(),
                meeting.getStartDate(), meeting.getEndDate(), attendees));
        return 1;
    }

    @Override
    public List<Meeting> selectAllMeetings() { return DB; }

    @Override
    public Optional<Meeting> selectMeetingById(UUID id) {
        return DB.stream()
                .filter(meeting -> meeting.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteMeeting(UUID meetingId, UUID userId) {
        Optional<Meeting> meetingMaybe = selectMeetingById(meetingId);
        if (meetingMaybe.isEmpty()) {
            throw new ApiRequestException("Meeting ID not found");
        } else if (!meetingMaybe.get().getResponsiblePerson().equals(userId) ) {
            throw new ApiRequestException("Access denied");
        }
        DB.remove(meetingMaybe.get());
        return 1;
    }

    @Override
    public Optional<MeetingAttendee> selectAttendeeById(List<MeetingAttendee> attendees, UUID id) {
        return attendees.stream()
                .filter(attendee -> attendee.getId().equals(id))
                .findFirst();
    }

    @Override
    public int addPerson(UUID meetingId, UUID userId) {
        Optional<Meeting> meetingMaybe = selectMeetingById(meetingId);
        if (meetingMaybe.isEmpty()) {
            throw new ApiRequestException("Meeting ID not found");
        }

        Meeting meeting = selectMeetingById(meetingId).get();
        List<MeetingAttendee> attendees = meeting.getAttendees();

        if (selectAttendeeById(attendees, userId).isEmpty()) {
            attendees.add(new MeetingAttendee(userId, ZonedDateTime.now(ZoneId.of("Z"))));
            return 1;
        } else {
            throw new ApiRequestException("User already attending");
        }
    }

    @Override
    public int removePerson(UUID meetingId, UUID userId) {
        Optional<Meeting> meetingMaybe = selectMeetingById(meetingId);
        if (meetingMaybe.isEmpty()) {
            throw new ApiRequestException("Meeting ID not found");
        }
        Meeting meeting = selectMeetingById(meetingId).get();
        List<MeetingAttendee> attendees = meeting.getAttendees();
        if (selectAttendeeById(attendees, userId).isEmpty()) {
            throw new ApiRequestException("User ID not found");
        } else {
            if(meeting.getResponsiblePerson().equals(userId)) {
                throw new ApiRequestException("Can not remove user responsible for the meeting");
            } else {
                attendees.remove(selectAttendeeById(attendees, userId).get());
                return 1;
            }
        }
    }

    @Override
    public List<Meeting> filterMeetings(MeetingFilter meetingFilter) {
        List<Meeting> result = new ArrayList<>();
        if(meetingFilter.getParam1() == null) {
            throw new ApiRequestException("No parameters specified");
        }
        switch(meetingFilter.getFilter()) {
            case DESCRIPTION:
                result = DB.stream()
                        .filter(meeting -> meeting.getDescription().toLowerCase()
                                .contains(meetingFilter.getParam1().toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case RESPONSIBLE_PERSON:
                UUID id = UUID.fromString(meetingFilter.getParam1());
                result = DB.stream()
                        .filter(meeting -> meeting.getResponsiblePerson().equals(id))
                        .collect(Collectors.toList());
                break;
            case CATEGORY:
                result = DB.stream()
                        .filter(meeting -> meeting.getCategory().equals(meetingFilter.getParam1()))
                        .collect(Collectors.toList());
                break;
            case TYPE:
                result = DB.stream()
                        .filter(meeting -> meeting.getType().equals(meetingFilter.getParam1()))
                        .collect(Collectors.toList());
                break;
            case DATE:
                LocalDateTime start = LocalDateTime.parse(meetingFilter.getParam1());
                if (meetingFilter.getParam2() != null) {
                    LocalDateTime end = LocalDateTime.parse(meetingFilter.getParam2());
                    result = DB.stream()
                            .filter(meeting -> meeting.getStartDate().compareTo(start) > 0)
                            .filter(meeting -> meeting.getStartDate().compareTo(end) < 0)
                            .collect(Collectors.toList());
                } else {
                    result = DB.stream()
                            .filter(meeting -> meeting.getStartDate().compareTo(start) > 0)
                            .collect(Collectors.toList());
                }
                break;
            case ATTENDEES:
                result = DB.stream()
                        .filter(meeting -> meeting.getAttendees().size() <= Integer.parseInt(meetingFilter.getParam1()))
                        .collect(Collectors.toList());
                break;
            default:
                break;
        }
        return result;
    }
}
