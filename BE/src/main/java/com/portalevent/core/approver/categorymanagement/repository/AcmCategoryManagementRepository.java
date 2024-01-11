package com.portalevent.core.approver.categorymanagement.repository;

import com.portalevent.core.approver.categorymanagement.model.request.AcmCategoryManagementListRequest;
import com.portalevent.core.approver.categorymanagement.model.response.AcmCategoryManagementListReponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcmCategoryManagementRepository extends CategoryRepository {

    /**
     * @param pageable Phân trang
     * @param req      các trường cần tìm kiếm
     * @return trả ra list data của thể loại và tích hợp tìm kiếm
     */
    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC) as indexs,
                c.id as categoryId,
                c.name as categoryName
            FROM
                category c
            WHERE
                (:#{#req.name} IS NULL OR c.name LIKE :#{'%'+#req.name+'%'} )
            """,
            countQuery = """
                    SELECT
                        COUNT(*)
                                FROM
                                    category c
                                WHERE
                                    (:#{#req.name} IS NULL OR c.name LIKE :#{'%'+#req.name+'%'} )
                    """,
            nativeQuery = true)
    Page<AcmCategoryManagementListReponse> getCategoryList(Pageable pageable, AcmCategoryManagementListRequest req);

    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC) as indexs,
                c.id as categoryId,
                c.name as categoryName
            FROM
                category c WHERE c.name LIKE ?1
            """, nativeQuery = true)
    List<AcmCategoryManagementListReponse> getCategoryByName(@Param("name") String categoryName);

}
