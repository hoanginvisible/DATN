package com.portalevent.core.approver.eventattendancelist.repository;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.repository.ParticipantRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AealEventAttendanceRepository extends ParticipantRepository {
    @Query(value = """
            SELECT a.id as id, 
               ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS indexs,
                a.email as email, 
                a.class_name as className, 
                a.attendance_time as attendanceTime, 
                a.rate as rate,
                a.feedback as feedback 
            FROM participant a
            WHERE a.event_id = :#{#idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, countQuery = """
            SELECT COUNT(*) FROM participant a
            WHERE a.event_id = :#{#idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Page<AealEventAttendanceListResponse> getPageAttendanceList(Pageable pageable, AealEventAttendanceSearchRequest req, @Param("idEvent") String idEvent);

    @Query(value = """
            SELECT  ROW_NUMBER() OVER (ORDER BY p.email) AS indexs,
                p.email as email,
                p.class_name as className,
                p.attendance_time as attendanceTime,
                p.rate as rate,
                p.feedback as feedback
           FROM participant AS p
           WHERE p.event_id = :idEvent
    """, nativeQuery = true)
    List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(@Param("idEvent") String id);

}
