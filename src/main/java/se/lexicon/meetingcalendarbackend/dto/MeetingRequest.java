package se.lexicon.meetingcalendarbackend.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    private List<@NotBlank(message = "Participant cannot be blank") String> participants;

    @NotBlank(message = "Meeting room is required")
    private String meetingRoom;
}
