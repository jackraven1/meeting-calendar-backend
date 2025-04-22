package se.lexicon.meetingcalendarbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.meetingcalendarbackend.dto.MeetingRequest;
import se.lexicon.meetingcalendarbackend.dto.MeetingResponse;
import se.lexicon.meetingcalendarbackend.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")

@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired // Optional in newer Spring versions
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }
    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponse> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(
            @Valid @RequestBody MeetingRequest meetingRequest) {
        // In a real app, get organizer from authentication context
        return ResponseEntity.ok(meetingService.createMeeting(meetingRequest, "currentUser"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingResponse> updateMeeting(
            @PathVariable Long id,
            @Valid @RequestBody MeetingRequest meetingRequest) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, meetingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}
