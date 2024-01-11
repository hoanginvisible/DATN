package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrMajorRepository extends MajorRepository {

    @Query(value = """
			SELECT id, code, name FROM major WHERE main_major_id IS NOT NULL
	""", nativeQuery = true)
    List<OmrInfomationResponse.OmrMajorResponse> getAllForDisplay();

}
