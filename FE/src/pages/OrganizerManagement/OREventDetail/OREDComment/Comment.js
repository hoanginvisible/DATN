import React, { useEffect, useState } from "react";
import "../../OREventDetail/OREDComment/Comment.css";
import { Comment } from "@ant-design/compatible";
import {
  Avatar,
  Button,
  Divider,
  Form,
  Input,
  message,
  Popconfirm,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCaretDown,
  faCommentDots,
  faPaperPlane,
} from "@fortawesome/free-solid-svg-icons";
import OREventDetailApi from "../../OREventDetail/OREventDetailApi";
import AVATAR from "../../../../assets/img/avatar_default.png";
import { useParams } from "react-router-dom";
import { DEFAULT_COMMENT_AVATAR } from "../../../../constants/EventProperties";

const CommentSection = () => {
  const [visibleCount, setVisibleCount] = useState(0);
  const [repliesVisible, setRepliesVisible] = useState({});
  const [comments, setComments] = useState([]); // danh sách comment
  const [comment, setComment] = useState("");
  const [replyContent, setReplyContent] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const { id } = useParams();

  useEffect(() => {
    OREventDetailApi.getCommentByIdEvent(id, currentPage).then(
      (res) => {
        if (res.data.data.length !== 0) {
          setTotalPages(res.data.data[0].totalPages);
          setCurrentPage(res.data.data[0].currentPage);
          setComments([...res.data.data, ...comments]);
        }
      },
      (err) => {
        console.log("Lỗi: " + err);
      }
    );
  }, [currentPage]);

  useEffect(() => {
    OREventDetailApi.getCommentByIdEvent(id, currentPage).then(
      (res) => {
        if (res.data.data.length !== 0) {
          setTotalPages(res.data.data[0].totalPages);
          setCurrentPage(res.data.data[0].currentPage);
          setComments(res.data.data);
        } else {
          setComments(res.data.data);
        }
      },
      (err) => {
        console.log("Lỗi: " + err);
      }
    );
  }, [id]);

  // Hiển thị thêm bình luận
  const handleShowMore = () => {
    setVisibleCount(visibleCount + 2);
    setCurrentPage((prevCount) => prevCount + 1); // Tăng số lượng comment hiển thị lên 2
  };

  // Ẩn / hiện input reply comment
  const handleToggleReply = (commentIndex) => {
    setRepliesVisible((prevState) => ({
      ...prevState,
      [commentIndex]: !prevState[commentIndex],
    }));
  };

  const handleChange = (e) => {
    setReplyContent(e.target.value);
  };

  // Reply comment
  const handleSend = (idComment, commentIndex) => {
    if (replyContent === null || replyContent === "") {
      message.error("Bình luận không được để trống!");
      return;
    }
    if (replyContent.length >= 1000) {
      message.error("Bạn đã nhập quá giới hạn số lượng từ");
      return;
    }

    OREventDetailApi.postReplyComment({
      eventId: id,
      comment: replyContent,
      replyId: idComment,
    }).then(
      (res) => {
        const updatedComments = [...comments];
        updatedComments[commentIndex].listReply.push(res.data.data);
        setComments(updatedComments);
        setComment("");
        setReplyContent("");
      },

      (err) => {
        console.log("Lỗi: " + err);
        message.error(err.response.data.message);
      }
    );
    setReplyContent("");
    handleToggleReply(commentIndex);
  };

  // Delete comment chính
  const handleDelete = (commentID) => {
    OREventDetailApi.deleteComment({
      commentId: commentID,
      // userId: "",
    })
      .then((res) => {
        let id = commentID;
        comments.forEach((item) => {
          if (item.id === id) {
            let updatedComments = comments.filter((record) => record.id !== id);
            setComments(updatedComments);
            message.success("Xóa thành công");
          }
        });
      })
      .catch((error) => {
        message.error(error.response.data.message);
      });
  };

  // Delete comment reply
  const handleDeleteReply = (commentIndex, replyIndex, commentID) => {
    OREventDetailApi.deleteComment({
      commentId: commentID,
    })
      .then((res) => {
        let id = commentID;
        comments[commentIndex].listReply.forEach((item) => {
          if (item.id === id) {
            const updatedComments = [...comments];
            updatedComments[commentIndex].listReply.splice(replyIndex, 1);
            setComments(updatedComments);
            message.success("Xóa thành công");
          }
        });
      })
      .catch((error) => {
        message.error(error.response.data.message);
      });
  };

  // Viết Comment
  const handlePostComment = () => {
    if (comment === null || comment.trim().length === 0) {
      message.error("Bình luận không được để trống!");
      return;
    }
    if (comment.length >= 1000) {
      message.error("Bạn đã nhập quá giới hạn số lượng từ");
      return;
    }

    OREventDetailApi.postComment({
      eventId: id,
      comment: comment,
    }).then(
      (res) => {
        setComments([...comments, res.data.data]);
        setComment("");
      },

      (err) => {
        console.log("Lỗi: " + err);
        message.error(err.response.data.message);
      }
    );
  };

  // Các hành động của comment chính
  const actionsComment = (commentIndex, commentID) => [
    <span
      className="btn-reply-delete"
      key="reply"
      onClick={() => handleToggleReply(commentIndex)}
    >
      Trả lời
    </span>,
    <Popconfirm
      title="Xác nhận xóa!"
      description="Bạn có chắc muốn xóa comment này không?"
      onConfirm={() => handleDelete(commentID)}
      // onCancel={cancel}
      okText="Yes"
      cancelText="No"
      getPopupContainer={(triggerNode) => triggerNode.parentNode}
    >
      <span key="delete" className="btn-reply-delete">
        Xóa
      </span>
    </Popconfirm>,
  ];

  // Các hành động của comment reply
  const actionsReplyComment = (index, indexReply, commentID) => [
    <Popconfirm
      title="Xác nhận xóa!"
      description="Bạn có chắc muốn xóa comment này không?"
      onConfirm={() => handleDeleteReply(index, indexReply, commentID)}
      // onCancel={cancel}
      okText="Yes"
      cancelText="No"
      getPopupContainer={(triggerNode) => triggerNode.parentNode}
    >
      <span key="delete" className="btn-reply-delete">
        Xóa
      </span>
    </Popconfirm>,
  ];

  return (
    <>
      <div style={{ textAlign: "center" }}>
        <h3 className="title-comment">
          <b>
            <FontAwesomeIcon
              icon={faCommentDots}
              style={{ color: "#172b4d" }}
            />
            <span style={{ marginLeft: "7px" }}>Bình luận</span>
          </b>
        </h3>
      </div>
      {/* ************ Xem thêm bình luận *************** */}
      {comments.length > 0 && totalPages - 1 !== currentPage && (
        <Button
          onClick={handleShowMore}
          style={{ width: "20%" }}
          className="btn-see-more"
        >
          <span style={{ marginRight: "7px" }}>Xem thêm bình luận</span>
          <FontAwesomeIcon icon={faCaretDown} style={{ color: "#0098d1" }} />
        </Button>
      )}
      <Divider />
      {/* *************** Danh sách bình luận ******************** */}
      {comments.map((comment, index) => (
        <Comment
          key={index}
          avatar={
            comment.avatar === DEFAULT_COMMENT_AVATAR ? (
              <Avatar src={AVATAR} />
            ) : (
              <Avatar src={comment.avatar} />
            )
          }
          author={<strong>{comment.email}</strong>}
          datetime={comment.lastModifiedDate}
          content={comment.comment}
          actions={actionsComment(index, comment.id)}
          style={{
            background: "rgba(0, 0, 0, 0)",
            width: "85%",
          }}
        >
          {repliesVisible[index] && (
            <Form>
              <Form.Item>
                <Input.TextArea
                  placeholder="Nhập nội dung trả lời..."
                  autoSize={{ minRows: 2, maxRows: 6 }}
                  value={replyContent}
                  onChange={handleChange}
                />
              </Form.Item>
              <Form.Item>
                <Button
                  type="primary"
                  onClick={() => handleSend(comment.id, index)}
                  style={{
                    float: "right",
                    backgroundColor: "#0098d1",
                  }}
                >
                  <span style={{ marginRight: "7px" }}>Trả lời</span>
                  <FontAwesomeIcon
                    icon={faPaperPlane}
                    style={{ color: "#ffffff" }}
                  />
                </Button>
              </Form.Item>
            </Form>
          )}
          {comment.listReply.map((reply, replyIndex) => (
            <Comment
              key={replyIndex}
              avatar={
                reply.avatar === DEFAULT_COMMENT_AVATAR ? (
                  <Avatar src={AVATAR} alt={"Avatar"} />
                ) : (
                  <Avatar src={reply.avatar} alt={"Avatar"} />
                )
              }
              author={<strong>{reply.email}</strong>}
              datetime={reply.lastModifiedDate}
              content={reply.comment}
              actions={actionsReplyComment(index, replyIndex, reply.id)}
              style={{
                background: "rgba(0, 0, 0, 0)",
                width: "85%",
              }}
            />
          ))}
        </Comment>
      ))}
      {/* *************** Danh sách bình luận ******************** */}
      {/* ************* Imput nhập comment ************ */}
      <Divider />
      <div style={{ width: "100%" }}>
        <Input.TextArea
          style={{ width: "100%" }}
          rows={2}
          value={comment}
          onChange={(e) => {
            setComment(e.target.value);
          }}
          placeholder="Nhập nội dung bình luận..."
        />
        <Button
          className="btn-post-comment"
          type="primary"
          onClick={() => handlePostComment()}
        >
          <span style={{ marginRight: "7px" }}>Bình luận</span>
          <FontAwesomeIcon icon={faPaperPlane} style={{ color: "#ffffff" }} />
        </Button>
      </div>
      <Divider />
      {/* ************* Imput nhập comment ************ */}
    </>
  );
};

export default CommentSection;
