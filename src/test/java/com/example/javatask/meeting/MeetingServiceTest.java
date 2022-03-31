package com.example.javatask.meeting;

import com.example.javatask.attendee.Attendee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;
    private MeetingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MeetingService(meetingRepository);
    }

    @Test
    void CanCreateMeetingAndAddItToList() {
        //given
        UUID responsiblePersonId = UUID.fromString("04b6e01e-862e-4ae6-bc1d-8918d1233207");
        LocalDateTime start = LocalDateTime.parse("2022-03-01T09:00:00");
        LocalDateTime end = LocalDateTime.parse("2022-03-01T10:00:00");
        List<Attendee> attendees = new ArrayList<>();
        Meeting meeting = new Meeting(null, "Test meeting", responsiblePersonId, "Testing meeting creation", Category.SHORT, Type.IN_PERSON, start, end, attendees);

        //when
        underTest.createMeeting(meeting);
        //then
//        ArgumentCaptor<Meeting> meetingArgumentCaptor = ArgumentCaptor.forClass(Meeting.class);

//        verify(meetingRepository).save(meetingArgumentCaptor.capture());
    }

    @Test
    void CanSelectAllMeetings() {
        //when
        underTest.selectAllMeetings();
        //then
        verify(meetingRepository).getMeetings();
    }

    @Test
    @Disabled
    void CanDeleteMeeting() {
        //given

        //when

        //then
    }

    @Test
    @Disabled
    void CanFilterMeetings() {
        //given

        //when

        //then
    }
}