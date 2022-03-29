package com.example.javatask.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
public class MeetingService {

    private final MeetingDao meetingDao;

    @Autowired
    public MeetingService(@Qualifier("mockDao") MeetingDao meetingDao) { this.meetingDao = meetingDao; }

    public int createMeeting(Meeting meeting) { return meetingDao.createMeeting(meeting); }

    public List<Meeting> selectAllMeetings() { return meetingDao.selectAllMeetings(); }

    public int deleteMeeting(UUID meetingId, UUID userId) { return meetingDao.deleteMeeting(meetingId, userId); }

    public int addPerson(UUID meetingId, UUID userId) { return meetingDao.addPerson(meetingId, userId); }

    public int removePerson(UUID meetingId, UUID userId) { return meetingDao.removePerson(meetingId, userId); }

    public List<Meeting> filterMeetings(@RequestBody MeetingFilter meetingFilter) { return meetingDao.filterMeetings(meetingFilter); }
}
