package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.organizer.eventDetail.model.response.OedMajorResponse;
import com.portalevent.entity.Major;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OedMajorRepository extends JpaRepository<Major, String> {
    @Query(value = "SELECT id, code, name FROM major WHERE main_major_id IS NOT NULL", nativeQuery = true)
    List<OedMajorResponse> getAll();

    @Query(value = "SELECT m.id, code, name FROM major AS m " +
            "JOIN event_major AS em " +
            "ON em.major_id = m.id " +
            "WHERE em.event_id = :idEvent", nativeQuery = true)
    List<OedMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);
}
