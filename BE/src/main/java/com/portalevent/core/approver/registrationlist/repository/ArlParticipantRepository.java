package com.portalevent.core.approver.registrationlist.repository;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.model.response.ArlEventParticipantResponse;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArlParticipantRepository extends ParticipantRepository {
    @Query(value = """
            SELECT a.id as id, 
            	ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS indexs,
            	a.email as email, 
                a.class_name as className, 
                a.question as question,
                a.lecturer_name as lecturerName,
                a.created_date AS createDate
            FROM participant a
            WHERE a.event_id = :#{#idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.lecturer} LIKE '' OR :#{#req.lecturer} IS NULL OR a.lecturer_name LIKE %:#{#req.lecturer}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            """, countQuery = """
            SELECT COUNT(*) FROM participant a
            WHERE a.event_id = :#{#idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.lecturer} LIKE '' OR :#{#req.lecturer} IS NULL OR a.lecturer_name LIKE %:#{#req.lecturer}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            """, nativeQuery = true)
    Page<ArlEventParticipantResponse> getAllParticipant(Pageable pageable, @Param("req") ArlEventParticipantRequest req, @Param("idEvent") String idEvent);
}
