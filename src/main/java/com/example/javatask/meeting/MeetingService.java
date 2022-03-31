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

    public UUID createMeeting(Meeting meeting) {
        List<Attendee> attendees = new ArrayList<>();
        UUID id = UUID.randomUUID();
        attendees.add(new Attendee(meeting.getResponsiblePerson(), ZonedDateTime.now(ZoneId.of("Z"))));
        meetingRepository.getMeetings().add(new Meeting(id, meeting.getName(), meeting.getResponsiblePerson(),
                meeting.getDescription(), meeting.getCategory(), meeting.getType(),
                meeting.getStartDate(), meeting.getEndDate(), attendees));
        meetingRepository.writeToFile();
        return id;
    }


    public void deleteMeeting(UUID meetingId, UUID userId) {
        Meeting meeting = meetingRepository.selectMeetingById(meetingId);
        if (!meeting.getResponsiblePerson().equals(userId) ) {
            throw new ApiRequestException("Access denied");
        }
        meetingRepository.getMeetings().remove(meeting);
        meetingRepository.writeToFile();
    }

    public List<Meeting> selectAllMeetings() {
        return meetingRepository.getMeetings();
    }

    public List<Meeting> filterMeetings(MeetingFilter meetingFilter) {
        if(meetingFilter.getParam1() == null) {
            throw new ApiRequestException("No parameters specified");
        }
        switch(meetingFilter.getFilter()) {
            case DESCRIPTION:
                return filterBy((meeting -> meeting.getDescription().toLowerCase().contains(meetingFilter.getParam1().toLowerCase())));
            case RESPONSIBLE_PERSON:
                UUID id = UUID.fromString(meetingFilter.getParam1());
                return filterBy((meeting -> meeting.getResponsiblePerson().equals(id)));
            case CATEGORY:
                return filterBy((meeting -> meeting.getCategory().name().equals(meetingFilter.getParam1())));
            case TYPE:
                return filterBy((meeting -> meeting.getType().name().equals(meetingFilter.getParam1())));
            case DATE:
                LocalDateTime start = LocalDateTime.parse(meetingFilter.getParam1());
                List<Meeting> meetingsUntilDate = filterBy((meeting -> meeting.getStartDate().compareTo(start) > 0));
                if (meetingFilter.getParam2() != null) {
                    LocalDateTime end = LocalDateTime.parse(meetingFilter.getParam2());
                    return meetingsUntilDate.stream()
                            .filter(meeting -> meeting.getStartDate().compareTo(end) < 0)
                            .collect(Collectors.toList());
                } else {
                    return meetingsUntilDate;
                }
            case ATTENDEES:
                return filterBy((meeting -> meeting.getAttendees().size() <= Integer.parseInt(meetingFilter.getParam1())));
            default:
                throw new ApiRequestException("Filter not specified");
        }
    }

    private List<Meeting> filterBy(Predicate<? extends Meeting> predicate) {
        return meetingRepository.getMeetings().stream()
                .filter((Predicate<? super Meeting>) predicate)
                .collect(Collectors.toList());
    }
}
