package com.example.javatask.meeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;
    @InjectMocks
    private MeetingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MeetingService(meetingRepository);
    }

    @Test
    @Disabled
    void CanCreateMeeting() {
        Meeting meeting = new Meeting();

        UUID id = underTest.createMeeting(meeting);

        assertEquals(UUID.fromString("789a0481-2ee3-4d4c-930e-c94aac5f7ad1"), id);
    }

    @Test
    void CanSelectAllMeetings() {
        underTest.selectAllMeetings();
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