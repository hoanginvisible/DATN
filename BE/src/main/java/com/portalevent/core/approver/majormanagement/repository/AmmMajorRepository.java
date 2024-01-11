package com.portalevent.core.approver.majormanagement.repository;

import com.portalevent.core.approver.majormanagement.model.response.AmmMajorResponse;
import com.portalevent.entity.Major;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AmmMajorRepository extends MajorRepository {
    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
            m.id AS id,
            m.code AS code,
            m.name AS name,
            m.mail_of_manager AS mailOfManager,
            mm.code as mainMajorCode,
            mm.name as mainMajorName
            FROM major m
            LEFT JOIN major mm ON m.main_major_id = mm.id
            WHERE (m.code LIKE CONCAT('%', :value '%')
            OR m.name LIKE CONCAT('%', :value, '%')
            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
            AND (:mainMajor IS NULL OR :mainMajor = 'all' OR (:mainMajor = "true" AND m.main_major_id IS NOT NULL) OR (:mainMajor = "false" AND m.main_major_id IS NULL))
            """,countQuery = """
            SELECT COUNT(*) FROM major m
            LEFT JOIN major mm ON m.main_major_id = mm.id
            WHERE (m.code LIKE CONCAT('%', :value '%')
            OR m.name LIKE CONCAT('%', :value, '%')
            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
            AND (:mainMajor IS NULL OR :mainMajor = 'all' OR (:mainMajor = "true" AND m.main_major_id IS NOT NULL) OR (:mainMajor = "false" AND m.main_major_id IS NULL))
            """, nativeQuery = true)
    List<AmmMajorResponse> getMajorList(@Param("value") String value, @Param("mainMajor") String mainMajor);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
            m.id AS id,
            m.code AS code,
            m.name AS name,
            m.mail_of_manager AS mailOfManager
             FROM major m WHERE m.main_major_id IS NULL
            """, nativeQuery = true)
    List<AmmMajorResponse> getMajorParentList();

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
            m.id AS id,
            m.code AS code,
            m.name AS name,
            m.mail_of_manager AS mailOfManager,
            mm.code as mainMajorCode,
            mm.name as mainMajorName
            FROM major m
            LEFT JOIN major mm ON m.main_major_id = mm.id
            WHERE m.id = :id
            """, nativeQuery = true)
    AmmMajorResponse getMajor(String id);

    Boolean existsByNameIgnoreCaseAndNameIgnoreCaseNot(String name, String exceptName);

    Boolean existsByCodeIgnoreCaseAndCodeIgnoreCaseNot(String code, String exceptCode);

    Boolean existsByMailOfManagerIgnoreCaseAndMailOfManagerIgnoreCaseNot(String email, String exceptEmail);

    @Modifying
    @Transactional
    @Query(value = """
    DELETE m FROM major m 
    LEFT JOIN event_major em ON m.id = em.major_id
    LEFT JOIN organizer_major om ON m.id = om.major_id
    LEFT JOIN major mm ON m.id = mm.main_major_id
    WHERE m.id = :id
    AND em.major_id IS NULL
    AND om.major_id IS NULL
    AND mm.main_major_id IS NULL
    """, nativeQuery = true)
    void deleteMajor(@Param("id") String id);

    @Query(value = """
        SELECT COUNT(*) FROM event_major em 
        WHERE em.major_id = :idMajor
        """, nativeQuery = true)
    Integer countMajorInEvent(@Param("idMajor") String idMajor);

    @Query(value = """
        SELECT COUNT(*) FROM major m 
        WHERE m.main_major_id = :idMajor
        """, nativeQuery = true)
    Integer countParentMajor(@Param("idMajor") String idMajor);
    @Query(value = """
        SELECT COUNT(*) FROM major m 
        WHERE m.main_major_id = :idMajor
        """, nativeQuery = true)
    Integer count(@Param("idMajor") String idMajor);
}
