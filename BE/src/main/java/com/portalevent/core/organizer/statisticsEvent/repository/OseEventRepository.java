package com.portalevent.core.organizer.statisticsEvent.repository;

import com.portalevent.core.organizer.statisticsEvent.model.response.OseEventInSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseTopEventResponse;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OseEventRepository extends EventRepository {
    @Query(value = """
            SELECT e.id, e.name, e.number_participants, eo.event_role FROM event AS e
             JOIN event_organizer AS eo ON eo.event_id = e.id
            WHERE e.semester_id = :idSemester
              AND (e.event_type = 0 || e.event_type = 2)
              AND e.status = 5
              AND eo.organizer_id = :idOrganizer
            ORDER BY e.number_participants DESC
            LIMIT 3
            """, nativeQuery = true)
    List<OseTopEventResponse> getTopEvent(@Param("idSemester") String idSemester, @Param("idOrganizer") String idOrganizer);

    @Query(value = """
                SELECT e.status, COUNT(e.id) AS quantity FROM event AS e
                JOIN event_organizer AS eo ON eo.event_id = e.id
                WHERE eo.organizer_id = :idOrganizer
                AND (e.event_type = 0 || e.event_type = 2)
                AND e.semester_id = :idSemester
                GROUP BY e.status
            """, nativeQuery = true)
    List<OseEventInSemesterResponse> getEventBySemesterAndOrganizer(@Param("idOrganizer") String idOrganizer, @Param("idSemester") String idSemester);

    @Query(value = """
                SELECT COUNT(e.id) AS sumEvent FROM event AS e
                JOIN event_organizer AS eo ON eo.event_id = e.id
                WHERE eo.organizer_id = :idOrganizer
                AND (e.event_type = 0 || e.event_type = 2)
                AND e.semester_id = :idSemester
            """, nativeQuery = true)
    Integer getSumEventBySemesterAndOrganizer(@Param("idOrganizer") String idOrganizer, @Param("idSemester") String idSemester);
}
