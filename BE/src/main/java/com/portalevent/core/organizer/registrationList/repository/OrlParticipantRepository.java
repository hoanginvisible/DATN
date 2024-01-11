package com.portalevent.core.organizer.registrationList.repository;

import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
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
public interface OrlParticipantRepository extends ParticipantRepository {

    @Query(value = """
            SELECT a.id as id, 
            	ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS indexs,
            	a.email as email, 
                a.class_name as className, 
                a.question as question,
                a.lecturer_name as lecturerName,
                a.created_date AS createDate
            FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.lecturer} LIKE '' OR :#{#req.lecturer} IS NULL OR a.lecturer_name LIKE %:#{#req.lecturer}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.question} LIKE '' OR :#{#req.question} IS NULL OR a.question LIKE %:#{#req.question}%)
            ORDER BY a.created_date DESC 
            """, countQuery = """
            SELECT COUNT(*) FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.lecturer} LIKE '' OR :#{#req.lecturer} IS NULL OR a.lecturer_name LIKE %:#{#req.lecturer}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.question} LIKE '' OR :#{#req.question} IS NULL OR a.question LIKE %:#{#req.question}%)
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Page<OrlQuestionResponse> getAllQuestion(Pageable pageable, @Param("req") OrlFindQuestionsRequest req);

    @Query(value = """
            SELECT COUNT(1) FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.question} LIKE '' OR :#{#req.question} IS NULL OR a.question LIKE %:#{#req.question}%)
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Integer countAllSearchQuestion(@Param("req") OrlFindQuestionsRequest req);

}
