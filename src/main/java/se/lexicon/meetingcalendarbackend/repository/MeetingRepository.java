package se.lexicon.meetingcalendarbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.meetingcalendarbackend.model.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Meeting> findByMeetingRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            String meetingRoom, LocalDateTime end, LocalDateTime start);
}
