package com.portalevent.core.participant.home.repository;

import com.portalevent.core.participant.home.model.response.PhDetailEventResponse;
import com.portalevent.core.participant.home.model.response.PhEventCommingUpResponse;
import com.portalevent.core.participant.home.model.response.PhEventScheduleResponse;
import com.portalevent.repository.EventRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface PhHomeEventRepository extends EventRepository {

    @Query(value = """
            SELECT id, name, start_time, end_time FROM event 
            WHERE status IN (4,5) AND event_type = :eventType
            """, nativeQuery = true)
    List<PhEventScheduleResponse> getEventApproved(@Param("eventType") Integer eventType);

    @Query("""
            SELECT NEW com.portalevent.core.participant.home.model.response.PhDetailEventResponse
            (a.id, a.name, a.startTime, a.endTime, a.isAttendance, a.isOpenRegistration, b.name, a.eventType, a.backgroundPath, a.bannerPath, a.description,
            c.id, c.name, c.formality, c.path, e.id, e.name, g.id, g.name, i.id, i.name, i.path, a.status, h.organizerId)
            FROM Event a
            LEFT JOIN Category b ON a.categoryId = b.id
            LEFT JOIN EventLocation c ON a.id = c.eventId
            LEFT JOIN EventMajor  d ON d.eventId = a.id
            LEFT JOIN Major e ON e.id = d.majorId
            LEFT JOIN EventObject f ON f.eventId = a.id
            LEFT JOIN Object g ON g.id = f.objectId
            LEFT JOIN EventOrganizer h ON a.id = h.eventId
            LEFT JOIN Resource i ON i.eventId = a.id
            WHERE a.id = :id AND (a.status = 4 OR a.status = 5)
            """)
    List<PhDetailEventResponse> detail(@Param("id") String id);

    @Query(value = """
            SELECT a.id, a.name, start_time, end_time, a.banner_path, GROUP_CONCAT(b.organizer_id SEPARATOR ',') AS organizer_account
            FROM event a
            LEFT JOIN event_organizer b ON a.id = b.event_id      
            WHERE a.status = 4
            AND a.start_time > :currentTime
            GROUP BY a.id, a.name, start_time, end_time, a.banner_path
            ORDER BY a.start_time LIMIT 7
            """, nativeQuery = true)
    List<PhEventCommingUpResponse.EventQueryResponse> getEventComingUp(@Param("currentTime") Long currentTime);

}
