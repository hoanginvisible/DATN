package com.portalevent.core.approver.eventapproved.repository;

import com.portalevent.core.approver.eventapproved.model.request.AeaEventApprovedRequest;
import com.portalevent.core.approver.eventapproved.model.response.AeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AeaEventRepository extends EventRepository {

    @Query(value = """
            select ROW_NUMBER() OVER(ORDER BY e.last_modified_date DESC) as indexs,
                                             e.id as eventId,
                                             e.name as eventName,
                                             e.start_time as eventStartTime,
                                             e.end_time as eventEndTime,
                                             c.name as categoryName,
                                             e.status as status
                                    from event e
                                    left join category c on c.id = e.category_id
                     where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                       and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                       and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                       and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                       and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                       and e.status in (4, 5)
            """, countQuery = """
                select count(e.id)
                     from event e
                     left join category c on c.id = e.category_id
                     where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                       and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                       and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                       and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                       and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                       and e.status in (4, 5)
            """, nativeQuery = true)
    Page<AeaEventApprovedResponse> getListEventAppoved(Pageable pageable, AeaEventApprovedRequest req);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AewaEventCategoryResponse> getListEventCategory();
}
