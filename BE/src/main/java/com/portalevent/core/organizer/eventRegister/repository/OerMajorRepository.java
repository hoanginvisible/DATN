package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerCategoryResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorResponse;
import com.portalevent.repository.CategoryRepository;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerMajorRepository extends MajorRepository {

    @Query(value = "SELECT id, code, name, mail_of_manager FROM major " +
            "WHERE main_major_id IS NOT NULL ORDER BY created_date DESC", nativeQuery = true)
    List<OerMajorResponse> getAll();
}
