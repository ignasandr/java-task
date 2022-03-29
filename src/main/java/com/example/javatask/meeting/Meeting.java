package com.example.javatask.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


enum Category {
    CODE_MONKEY, HUB, SHORT, TEAM_BUILDING
}

enum Type {
    LIVE, IN_PERSON
}

public class Meeting {
    private UUID id;
    private String name;
    private UUID responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<MeetingAttendee> attendees;

    public Meeting(@JsonProperty("id") UUID id, @JsonProperty("name") String name,
                   @JsonProperty("responsible") UUID responsiblePerson,
                   @JsonProperty("description") String description, @JsonProperty("category") Category category, @JsonProperty("type") Type type,
                   @JsonProperty("start") LocalDateTime startDate, @JsonProperty("end") LocalDateTime endDate, List<MeetingAttendee> attendees) {
        this.id = id;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendees = attendees;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(UUID responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<MeetingAttendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<MeetingAttendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responsiblePerson=" + responsiblePerson +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", attendees=" + attendees +
                '}';
    }
}
