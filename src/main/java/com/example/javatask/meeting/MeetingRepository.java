package com.example.javatask.meeting;

import com.example.javatask.exception.ApiRequestException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class MeetingRepository {

    private static List<Meeting> DB = new ArrayList<>();

    public List<Meeting> getMeetings() { return DB; }

    public Meeting selectMeetingById(UUID id) {
        return DB.stream()
                .filter(meeting -> meeting.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Meeting ID not found"));
    }

    public void writeToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("meetings.json").toFile(), DB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Meeting> readFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return Arrays.asList(mapper.readValue(Paths.get("meetings.json").toFile(), Meeting[].class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new ApiRequestException("Could not read file");
    }
}
