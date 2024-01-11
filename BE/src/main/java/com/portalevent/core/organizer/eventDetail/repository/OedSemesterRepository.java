package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.organizer.eventDetail.model.response.OedSemesterResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedTimeSemesterResponse;
import com.portalevent.entity.Semester;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OedSemesterRepository extends JpaRepository<Semester, String> {
    @Query(value = """
        SELECT id, name,
        start_time, end_time,
        start_time_first_block, end_time_first_block,
        start_time_second_block, end_time_second_block 
        FROM semester
    """, nativeQuery = true)
    public List<OedSemesterResponse> getAll();
}
