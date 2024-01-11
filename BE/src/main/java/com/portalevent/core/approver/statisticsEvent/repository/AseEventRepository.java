package com.portalevent.core.approver.statisticsEvent.repository;


import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInMajor;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseLecturerInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseListOrganizer;
import com.portalevent.core.approver.statisticsEvent.model.response.AseParticipantInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseTopEventResponse;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseEventRepository extends EventRepository {
    @Query(value = """
                SELECT COUNT(e.id) AS sumEvent FROM event AS e
                WHERE e.semester_id = :idSemester
                AND (e.event_type = 0 || event_type = 2)
            """, nativeQuery = true)
    Integer getSumEventBySemester(@Param("idSemester") String idSemester);

    @Query(value = """
                SELECT e.status, COUNT(e.id) AS quantity FROM event AS e
                WHERE e.semester_id = :idSemester
                  AND (e.event_type = 0 || event_type = 2)
                GROUP BY e.status;
            """, nativeQuery = true)
    List<AseEventInSemesterResponse> getEventBySemester(@Param("idSemester") String idSemester);

    @Query(value = """
            SELECT e.id, e.name, e.number_participants FROM event AS e
            WHERE e.semester_id = :idSemester
              AND e.status = 5
              AND (e.event_type = 0 || event_type = 2)
            ORDER BY e.number_participants DESC
            LIMIT 3
            """, nativeQuery = true)
    List<AseTopEventResponse> getTopEvent(@Param("idSemester") String idSemester);

    @Query(value = """
            SELECT eo.organizer_id AS organizer_id, COUNT(e.id) AS quantity_event FROM event AS e
            JOIN event_organizer AS eo ON eo.event_id = e.id
            WHERE e.semester_id = :idSemester
            AND (e.event_type = 0 || event_type = 2)
            GROUP BY eo.organizer_id
            ORDER BY quantity_event DESC     
            """, nativeQuery = true)
    List<AseListOrganizer> getListOrganizer(@Param("idSemester") String id);

    @Query(value = """
                SELECT m.id ,m.code, m.name, COUNT(m.id) AS quantity FROM event AS e
                JOIN event_major AS em ON em.event_id = e.id
                JOIN major AS m ON m.id = em.major_id
                WHERE e.semester_id = :idSemester
                AND (event_type = 0 || event_type = 2)
                AND status = 5
                GROUP BY m.id, m.code, m.name
            """, nativeQuery = true)
    List<AseEventInMajor> getEventInMajorByIdSemester(@Param("idSemester") String id);

    @Query(value = """
                SELECT e.name ,expected_participants AS expectedParticipants,
                       COUNT(p.id) AS numberParticipantsEnrolled,
                       number_participants AS numberParticipants
                FROM event AS e
                LEFT JOIN participant AS p ON p.event_id = e.id
                WHERE e.status = 5
                AND e.event_type = 0
                AND e.semester_id = :idSemester
                GROUP BY e.name, expected_participants, number_participants
            """, nativeQuery = true)
    List<AseParticipantInEvent> getListParticipantInEvent(@Param("idSemester") String id);

    @Query(value = """
            SELECT e.name , COUNT(p.id) AS numberParticipantsEnrolled,
                   number_participants AS numberParticipants
            FROM event AS e
            LEFT JOIN participant AS p ON p.event_id = e.id
            WHERE e.status = 5
            AND e.event_type = 1
            AND e.semester_id = :idSemester
            GROUP BY e.name, number_participants
            """, nativeQuery = true)
    List<AseLecturerInEvent> getListLecturerInEvent(@Param("idSemester") String id);

    @Query(value = """
                SELECT e.name ,expected_participants AS expectedParticipants,
                       COUNT(p.id) AS numberParticipantsEnrolled,
                       number_participants AS numberParticipants
                FROM event AS e
                LEFT JOIN participant AS p ON p.event_id = e.id
                WHERE e.status = 5
                AND (event_type = 0 || event_type = 2)
                AND e.semester_id = :idSemester
                AND e.category_id = :idCategory
                GROUP BY e.name, expected_participants, number_participants
            """, nativeQuery = true)
    List<AseParticipantInEvent> getListParticipantInEventByCategory(@Param("idSemester") String id, @Param("idCategory") String idCategory);
}
