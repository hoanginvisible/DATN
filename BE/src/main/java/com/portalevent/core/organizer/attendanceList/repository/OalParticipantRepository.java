package com.portalevent.core.organizer.attendanceList.repository;

import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository
public interface OalParticipantRepository extends ParticipantRepository {

    @Query(value = """
            SELECT a.id, 
                ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS stt, 
                a.email, 
                a.class_name, 
                a.attendance_time, 
                a.rate, 
                a.feedback 
            FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, countQuery = """
            SELECT COUNT(*) 
            FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Page<OalAttendanceResponse> getAllAttendance(Pageable pageable, @Param("req") OalFindAttendanceRequest req);

    @Query(value = """
            SELECT COUNT(1) FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Integer countAllSearch(@Param("req") OalFindAttendanceRequest req);

}
