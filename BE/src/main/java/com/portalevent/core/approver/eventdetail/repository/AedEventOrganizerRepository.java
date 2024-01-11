package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.entity.EventOrganizer;
import com.portalevent.repository.EventOrganizerRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedEventOrganizerRepository extends EventOrganizerRepository {
    @Query(value = """
                SELECT organizer_id FROM event_organizer
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<String> getIdOrganizerByIdEvent(@Param("idEvent") String id);

    List<EventOrganizer> findAllByEventId(String eventId);
}
