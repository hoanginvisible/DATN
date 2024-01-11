package com.portalevent.core.approver.statisticsEvent.repository;

import com.portalevent.core.approver.statisticsEvent.model.response.AseSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseSemesterRepository extends SemesterRepository {
    @Query(value = """
            SELECT id, name, start_time, end_time FROM semester
            """, nativeQuery = true)
    List<AseSemesterResponse> getAllSemester();
}
