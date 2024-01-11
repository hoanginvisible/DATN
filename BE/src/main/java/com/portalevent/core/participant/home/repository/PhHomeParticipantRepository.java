package com.portalevent.core.participant.home.repository;

import com.portalevent.entity.Participant;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author SonPT
 */

@Repository
public interface PhHomeParticipantRepository extends ParticipantRepository {
    @Query(value = """
        select * from participant as p
        where p.event_id = :idEvent
        and p.id_user = :idUser
    """, nativeQuery = true)
    Optional<Participant> getByIdEventAndIdUser(@Param("idEvent") String idEvent, @Param("idUser") String idUser);

    Participant getByEventIdAndIdUser(String eventId, String userId);

}
