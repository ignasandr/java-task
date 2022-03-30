package com.example.javatask.meeting;

import com.example.javatask.attendee.Attendee;
import com.example.javatask.exception.ApiRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) { this.meetingRepository = meetingRepository; }

    private static List<Meeting> DB = new ArrayList<>();

    public UUID createMeeting(Meeting meeting) {
        List<Attendee> attendees = new ArrayList<>();
        UUID id = UUID.randomUUID();
        attendees.add(new Attendee(meeting.getResponsiblePerson(), ZonedDateTime.now(ZoneId.of("Z"))));
        DB.add(new Meeting(id, meeting.getName(), meeting.getResponsiblePerson(),
                meeting.getDescription(), meeting.getCategory(), meeting.getType(),
                meeting.getStartDate(), meeting.getEndDate(), attendees));
        writeToFile();
        return id;
    }

    public List<Meeting> selectAllMeetings() {
        DB = readFromFile();
        return DB; }

    public Meeting selectMeetingById(UUID id) {
        return DB.stream()
                .filter(meeting -> meeting.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Meeting ID not found"));
    }

    public void deleteMeeting(UUID meetingId, UUID userId) {
        Meeting meeting = selectMeetingById(meetingId);
        if (!meeting.getResponsiblePerson().equals(userId) ) {
            throw new ApiRequestException("Access denied");
        }
        DB.remove(meeting);
        writeToFile();
    }



    public List<Meeting> filterMeetings(MeetingFilter meetingFilter) {
        if(meetingFilter.getParam1() == null) {
            throw new ApiRequestException("No parameters specified");
        }
        switch(meetingFilter.getFilter()) {
            case DESCRIPTION:
                return filterBy(meetingFilter, (meeting -> meeting.getDescription().toLowerCase().contains(meetingFilter.getParam1().toLowerCase())));
            case RESPONSIBLE_PERSON:
                UUID id = UUID.fromString(meetingFilter.getParam1());
                return  DB.stream()
                        .filter(meeting -> meeting.getResponsiblePerson().equals(id))
                        .collect(Collectors.toList());
            case CATEGORY:
                return filterBy(meetingFilter, (meeting -> meeting.getCategory().name().equals(meetingFilter.getParam1())));
            case TYPE:
                return filterBy(meetingFilter, (meeting -> meeting.getType().name().equals(meetingFilter.getParam1())));
            case DATE:
                LocalDateTime start = LocalDateTime.parse(meetingFilter.getParam1());
                if (meetingFilter.getParam2() != null) {
                    LocalDateTime end = LocalDateTime.parse(meetingFilter.getParam2());
                    return DB.stream()
                            .filter(meeting -> meeting.getStartDate().compareTo(start) > 0)
                            .filter(meeting -> meeting.getStartDate().compareTo(end) < 0)
                            .collect(Collectors.toList());
                } else {
                    return DB.stream()
                            .filter(meeting -> meeting.getStartDate().compareTo(start) > 0)
                            .collect(Collectors.toList());
                }
            case ATTENDEES:
                return filterBy(meetingFilter, (meeting -> meeting.getAttendees().size() <= Integer.parseInt(meetingFilter.getParam1())));
            default:
                throw new ApiRequestException("Filter not specified");
        }
    }

    private List<Meeting> filterBy(MeetingFilter meetingFilter, Predicate<? extends Meeting> predicate) {
        return DB.stream()
                .filter((Predicate<? super Meeting>) predicate)
                .collect(Collectors.toList());
    }

    public void writeToFile() {
        meetingRepository.writeToFile(DB);
    }

    public List<Meeting> readFromFile() {
        List<Meeting> meetings = new ArrayList<>();
        meetings = meetingRepository.readFromFile();
        return meetings;
    }
}
