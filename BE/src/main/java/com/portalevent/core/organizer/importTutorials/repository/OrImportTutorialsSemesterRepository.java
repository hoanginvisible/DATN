package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsSemesterResponse;
import com.portalevent.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrImportTutorialsSemesterRepository extends JpaRepository<Semester, String> {
    @Query(value = """
        SELECT 
            id, name,
            start_time, end_time,
            start_time_first_block, end_time_first_block,
            start_time_second_block, end_time_second_block 
        FROM semester
    """, nativeQuery = true)
    List<OrImportTutorialsSemesterResponse> getAllSemester();


}
