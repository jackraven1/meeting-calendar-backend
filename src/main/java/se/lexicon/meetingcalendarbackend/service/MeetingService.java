package se.lexicon.meetingcalendarbackend.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import se.lexicon.meetingcalendarbackend.dto.MeetingRequest;
import se.lexicon.meetingcalendarbackend.dto.MeetingResponse;
import se.lexicon.meetingcalendarbackend.exception.ConflictException;
import se.lexicon.meetingcalendarbackend.exception.ResourceNotFoundException;
import se.lexicon.meetingcalendarbackend.model.Meeting;
import se.lexicon.meetingcalendarbackend.repository.MeetingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final ModelMapper modelMapper;

    public List<MeetingResponse> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MeetingResponse getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));
        return mapToResponse(meeting);
    }

    public MeetingResponse createMeeting(MeetingRequest meetingRequest, String organizer) {

        if (hasTimeConflict(meetingRequest)) {
            throw new ConflictException("Meeting time conflicts with an existing meeting");
        }

        Meeting meeting = modelMapper.map(meetingRequest, Meeting.class);
        meeting.setOrganizer(organizer);
        Meeting savedMeeting = meetingRepository.save(meeting);
        return mapToResponse(savedMeeting);
    }

    public MeetingResponse updateMeeting(Long id, MeetingRequest meetingRequest) {
        Meeting existingMeeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));


        if (hasTimeConflict(meetingRequest, id)) {
            throw new ConflictException("Meeting time conflicts with an existing meeting");
        }

        modelMapper.map(meetingRequest, existingMeeting);
        Meeting updatedMeeting = meetingRepository.save(existingMeeting);
        return mapToResponse(updatedMeeting);
    }

    public void deleteMeeting(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meeting not found");
        }
        meetingRepository.deleteById(id);
    }

    private boolean hasTimeConflict(MeetingRequest meetingRequest) {
        return hasTimeConflict(meetingRequest, null);
    }

    private boolean hasTimeConflict(MeetingRequest meetingRequest, Long excludeMeetingId) {
        List<Meeting> conflictingMeetings = meetingRepository
                .findByMeetingRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        meetingRequest.getMeetingRoom(),
                        meetingRequest.getEndTime(),
                        meetingRequest.getStartTime());

        if (excludeMeetingId != null) {
            conflictingMeetings = conflictingMeetings.stream()
                    .filter(m -> !m.getId().equals(excludeMeetingId))
                    .toList();
        }

        return !conflictingMeetings.isEmpty();
    }

    private MeetingResponse mapToResponse(Meeting meeting) {
        return modelMapper.map(meeting, MeetingResponse.class);
    }
}