package com.portalevent.core.approver.objectmanagement.repository;

import com.portalevent.core.approver.objectmanagement.model.request.AomObjectManagementListRequest;
import com.portalevent.core.approver.objectmanagement.model.response.AomObjectManagementListResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AomObjectManagementRepository extends ObjectRepository {

    /**
     *
     * @param pageable phân trang
     * @param req tìm kiếm object
     * @return list object và tìm kiếm
     */
    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY o.last_modified_date DESC) as indexs,
                o.id as objectId,
                o.name as objectName
            FROM
                object o
            WHERE
                (:#{#req.name} IS NULL OR o.name LIKE :#{'%'+#req.name+'%'})
            """, countQuery = """
            SELECT
                    COUNT(*)
                    FROM
                    object o
                    WHERE
                    (:#{#req.name} IS NULL OR o.name LIKE :#{'%'+#req.name+'%'})
            """, nativeQuery = true)
    Page<AomObjectManagementListResponse> getObjectList(Pageable pageable, AomObjectManagementListRequest req);

    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY o.last_modified_date DESC) as indexs,
                o.id as objectId,
                o.name as objectName
            FROM
                object o WHERE o.name LIKE ?1
            """, countQuery = """
            SELECT
                    COUNT(*)
                    FROM
                    object o
                    WHERE o.name LIKE ?1
            """, nativeQuery = true)
    List<AomObjectManagementListResponse> findNameObject(@Param("name") String name);
}
