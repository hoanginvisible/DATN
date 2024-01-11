package com.portalevent.core.approver.eventwaitingapproval.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedCommentResponse;
import com.portalevent.entity.Comment;
import com.portalevent.repository.CommentRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AewaCommentRepository extends CommentRepository {
    @Query(value = """
            SELECT * FROM comment WHERE reply_id = :replyId
            """, nativeQuery = true)
    List<Comment> getReplyCommentByReplyId(@Param("replyId") String replyId);

    @Query(value = """
            SELECT a.id AS id, a.user_id as user_id, 
            a.last_modified_date AS last_modified_date, a.comment, a.reply_id
            FROM comment a 
            WHERE a.reply_id IS NOT NULL AND a.event_id = :eventId AND a.reply_id IN :listId
            ORDER BY a.last_modified_date ASC; 
            """, nativeQuery = true)
    List<AedCommentResponse> getReplyCommentByReplyIdAndEventId(@Param("eventId") String eventId, @Param("listId") List<String> listReplyId);

    Page<Comment> findByEventIdAndReplyIdIsNullOrderByLastModifiedDateDesc(String eventId, Pageable pageable);

}
