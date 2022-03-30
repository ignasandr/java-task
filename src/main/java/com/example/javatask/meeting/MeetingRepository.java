package com.example.javatask.meeting;

import com.example.javatask.exception.ApiRequestException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
public class MeetingRepository {

    public void writeToFile(List<Meeting> meetings) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("meetings.json").toFile(), meetings);
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
