package com.portalevent.core.approver.statisticsEvent.repository;

import com.portalevent.core.approver.statisticsEvent.model.response.AseMajorResponse;
import com.portalevent.repository.MajorRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseMajorRepository extends MajorRepository {
    @Query(value = """
    SELECT id, code, name FROM major
    """, nativeQuery = true)
    List<AseMajorResponse> getAllMajor();
}
