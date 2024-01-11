package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AewaEvidenceResponse;
import com.portalevent.repository.EvidenceRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AedEvidenceRepository extends EvidenceRepository {
    @Query(value = """
                SELECT name, path, evidence_type, description FROM evidence
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<AewaEvidenceResponse> getEvidenceByIdEvent(@Param("idEvent") String id);
}
