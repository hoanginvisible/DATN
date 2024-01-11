package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.entity.EventMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HoangDV
 */
@Repository
public interface OedEventMajorRepository extends JpaRepository<EventMajor, String>  {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_major WHERE event_id = :idEvent", nativeQuery = true)
    public void deleteEventMajorByIdEvent(@Param("idEvent") String id);
}
