package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedEventDetailApprovedResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedEventMajorResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedEventObjectResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedEventOverlapOrganizer;
import com.portalevent.entity.Event;
import com.portalevent.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedEventDetailRepository extends EventRepository {

    @Query(value = """
            SELECT
            e.id AS id,
            e.name AS name,
            e.start_time AS startTime,
            e.end_time AS endTime,
            c.name AS categoryName,
            e.description AS description,
            e.approver_id AS approverId,
            e.reason AS reason,
            e.expected_participants AS expectedParticipant,
            e.status AS status,
            e.background_path AS background,
            e.banner_path AS banner,
            e.standee_path AS standee,
            e.event_type AS event_type,
            e.is_hire_design_banner AS isHireDesignBanner,
            e.is_hire_design_bg AS isHireDesignBackground,
            e.is_hire_design_standee AS isHireDesignStandee,
            e.number_participants AS numberParticipants,
            e.is_wait_approval_periodically AS isWaitApprovalPeriodically,
            p1.participants,
            e.is_not_enough_time_approval,
            ROUND(
                   (SELECT COUNT(*)
                    FROM participant
                    WHERE event_id = e.id AND rate IN (4, 5)) /
                   (SELECT COUNT(*)
                    FROM participant
                    WHERE event_id = e.id) * 100, 1
                     ) AS avgRate
             FROM event e
                      LEFT JOIN
                  (SELECT event_id, COUNT(*) AS participants
                   FROM participant
                   GROUP BY event_id) p1
                  ON e.id = p1.event_id
                      LEFT JOIN category c
                                ON c.id = e.category_id
             WHERE e.id = :id
             GROUP BY e.id """, nativeQuery = true)
    Optional<AedEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
            SELECT a.id, a.major_id, a.event_id FROM event_major a WHERE a.event_id = :id         
            """, nativeQuery = true)
    List<AedEventMajorResponse> getAllEventMajorByIdEvent(@Param("id") String id);

    @Query(value = """
            SELECT a.id, a.object_id, a.event_id FROM event_object a WHERE a.event_id = :id         
            """, nativeQuery = true)
    List<AedEventObjectResponse> getAllEventObjectByIdEvent(@Param("id") String id);

    @Query(value = "select distinct * from event as e where e.id = :id and e.status in :status", nativeQuery = true)
    Optional<Event> findByIdAndStatusIn(@Param("id") String id, @Param("status") List<Short> status);

    @Query(value = """
               select c.name from event e join category c on e.category_id = c.id
               where e.id = :idEvent
            """, nativeQuery = true)
    String getCategoryByIdEvent(@Param("idEvent") String idEvent);

    @Query(value = """
			SELECT GROUP_CONCAT(e.name SEPARATOR ', ') AS name  FROM event e WHERE e.start_time < :endTime AND e.end_time > :startTime AND e.status = 4 AND e.id <> :id
            """, nativeQuery = true)
    String getNameEventsInTime(@Param("id") String id, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Query(value = """
            SELECT new com.portalevent.core.approver.eventdetail.model.response.AedEventOverlapOrganizer(e.id, e.name, e.startTime, e.endTime) 
            FROM Event e
            JOIN EventOrganizer o ON e.id = o.eventId
            WHERE o.organizerId = :organizerId
            and e.startTime < :endTime and e.endTime > :startTime AND e.status = 4 OR e.status = 3
            """)
    List<AedEventOverlapOrganizer> getEventOverlapOrganizer(@Param("organizerId") String id,
                                                            @Param("startTime") Long startTime,
                                                            @Param("endTime") Long endTime);

}
