package com.portalevent.core.participant.home.service.impl;

import com.mchange.v2.lang.StringUtils;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.participant.home.model.request.PhDeleteCommentRequest;
import com.portalevent.core.participant.home.model.request.PhRegisterEventRequest;
import com.portalevent.core.participant.home.model.request.PhRollCallRequest;
import com.portalevent.core.participant.home.model.request.PhPostCommentRequest;
import com.portalevent.core.participant.home.model.request.PhReplyCommentRequest;
import com.portalevent.core.participant.home.model.response.PhAllCommentResponse;
import com.portalevent.core.participant.home.model.response.PhCommentResponse;
import com.portalevent.core.participant.home.model.response.PhEventCommingUpResponse;
import com.portalevent.core.participant.home.model.response.PhEventScheduleResponse;
import com.portalevent.core.participant.home.model.response.PhDetailEventResponse;
import com.portalevent.core.participant.home.model.response.PhReplyCommentResponse;
import com.portalevent.core.participant.home.repository.PhHomeCommentRepository;
import com.portalevent.core.participant.home.repository.PhHomeEventRepository;
import com.portalevent.core.participant.home.repository.PhHomeParticipantRepository;
import com.portalevent.core.participant.home.service.PhHomeService;
import com.portalevent.entity.Comment;
import com.portalevent.entity.Event;
import com.portalevent.entity.Participant;
import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.LoggerUtil;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author SonPT
 */

@Validated
@Service
public class PhHomeServiceImpl implements PhHomeService {

    @Autowired
    private PhHomeEventRepository eventRepository;

    @Autowired
    private PhHomeParticipantRepository participantRepository;

    @Autowired
    private PhHomeCommentRepository commentRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private PortalEventsSession portalEventsSession;

    @Autowired
    private LoggerUtil loggerUtil;

    private boolean isSameDay(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() &&
                date1.getMonth() == date2.getMonth() &&
                date1.getDate() == date2.getDate();
    }

    @Override
    public List<PhEventScheduleResponse> getAll() {
        List<String> currentUserRole = portalEventsSession.getCurrentUserRole();
        if (currentUserRole.contains(ActorConstants.ACTOR_ORGANIZER)) {
            return eventRepository.getEventApproved(EventType.LECTURER_EVENT.ordinal());
        }
        if (currentUserRole.contains(ActorConstants.ACTOR_PARTICIPANT)) {
            return eventRepository.getEventApproved(EventType.STUDENT_EVENT.ordinal());
        }
        return new ArrayList<>();
    }

    @Override
    public List<PhEventCommingUpResponse> getEventComingUp() {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        List<PhEventCommingUpResponse> response = new ArrayList<>();
        //Lấy ra các SK sắp diễn ra
        List<PhEventCommingUpResponse.EventQueryResponse> eventResponses = eventRepository.getEventComingUp(new Date().getTime());
        List<SimpleResponse> listAllOrganizer = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ORGANIZER);

        Map<String, SimpleResponse> mapAllOrganizer = new HashMap<>();
        for (SimpleResponse organizer :listAllOrganizer) {
            mapAllOrganizer.put(organizer.getId(), organizer);
        }

        for (PhEventCommingUpResponse.EventQueryResponse item :eventResponses) {
            PhEventCommingUpResponse phEventCommingUpResponse = new PhEventCommingUpResponse();
            phEventCommingUpResponse.setId(item.getId());
            phEventCommingUpResponse.setName(item.getName());
            Date startTime = new Date(item.getStartTime());
            Date endTime = new Date(item.getEndTime());
            if (isSameDay(startTime, endTime)) {
                phEventCommingUpResponse.setTime(sdfTime.format(startTime) + " - " + sdfTime.format(endTime) + " ngày " + sdfDate.format(startTime));
            } else {
                phEventCommingUpResponse.setTime(sdfDate.format(startTime) + " - " + sdfDateTime.format(endTime));
            }
            phEventCommingUpResponse.setBanner(item.getBannerPath());
            String[] arrOrganizerId = item.getOrganizerAccount().split(",");
            List<String> organizerAccountInEvent = new ArrayList<>();
            for (String organizerId :arrOrganizerId) {
                organizerAccountInEvent.add(mapAllOrganizer.get(organizerId).getName() + " - " + mapAllOrganizer.get(organizerId).getUserName());
            }
            phEventCommingUpResponse.setListOrganizerAccount(organizerAccountInEvent);
            response.add(phEventCommingUpResponse);
        }
        return response;
    }

    // Đăng ký sự kiện
    @Override
    @Synchronized
    public Boolean registerEvent(PhRegisterEventRequest req) {
        String emailUser = portalEventsSession.getCurrentEmail(); // Email Session
        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (event.get().getStatus() != EventStatus.APPROVED) {
            throw new RestApiException(Message.INVALID_EVENT_STATUS);
        }
        if (event.get().getIsOpenRegistration() == false) {
            throw new RestApiException(Message.REGISTRATION_NOT_OPEN);
        }
        List<String> currentUserRole = portalEventsSession.getCurrentUserRole();
        if (!currentUserRole.contains(ActorConstants.ACTOR_ORGANIZER) && !currentUserRole.contains(ActorConstants.ACTOR_APPROVER)
                && !currentUserRole.contains(ActorConstants.ACTOR_ADMINISTRATIVE)) {
            if (!StringUtils.nonWhitespaceString(req.getLecturerName())) {
                throw new RestApiException(Message.LECTURER_IS_REQURIED);
            }
            if (!StringUtils.nonWhitespaceString(req.getClassName())) {
                throw new RestApiException(Message.CLASS_NAME_IS_REQUIRED);
            }
        }
        Optional<Participant> p = participantRepository.getByIdEventAndIdUser(req.getEventId(), portalEventsSession.getCurrentIdUser());
        if (!p.isPresent()) {
            Participant participant = new Participant();
            participant.setEventId(req.getEventId());
            participant.setClassName(req.getClassName());
            participant.setEmail(emailUser);
            participant.setQuestion(req.getQuestion());
            participant.setLecturerName(req.getLecturerName());
            participant.setIdUser(portalEventsSession.getCurrentIdUser());
            if (participantRepository.save(participant) == null) {
                return false;
            } else {
                loggerUtil.sendLog("Đăng ký tham gia sự kiện", participant.getEventId());
                return true;
            }
        } else {
            throw new RestApiException(Message.PARTICIPANT_ALREADY_EXIST);
        }
    }

    // Điểm danh sự kiện
    @Override
    @Synchronized
    public Boolean rollCall(PhRollCallRequest request) {
        Optional<Event> event = eventRepository.findById(request.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (event.get().getIsAttendance() == false) {
            throw new RestApiException(Message.ROLL_CALL_NOT_OPEN);
        }
        Optional<Participant> participant = participantRepository.getByIdEventAndIdUser(request.getEventId(), portalEventsSession.getCurrentIdUser());
        if (!participant.isPresent()) {
            throw new RestApiException(Message.PARTICIPANT_NOT_EXIST);
        }
        participant.get().setFeedback(request.getFeedback());
        participant.get().setRate(request.getRate());
        participant.get().setAttendanceTime(new Date().getTime());
        participantRepository.save(participant.get());
        loggerUtil.sendLog("Điểm danh với đánh giá " + participant.get().getRate() + " sao", participant.get().getEventId());
        return true;
    }

    @Override
    public List<SimpleResponse> listOrganizer() {
        return callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ORGANIZER);
    }

    @Override
    @Synchronized
    public PhAllCommentResponse postComment(PhPostCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        SimpleResponse responseEntity = callApiIdentity.handleCallApiGetUserById(userId);
        if (responseEntity == null) {
            throw new RestApiException(Message.PARTICIPANT_NOT_EXIST);
        }
        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Optional<Participant> participant = participantRepository.getByIdEventAndIdUser(req.getEventId(), userId);
        if (!participant.isPresent()) {
            throw new RestApiException(Message.PARTICIPANT_NOT_IN_EVENT);
        }
        Comment comment = new Comment();
        comment.setEventId(req.getEventId());
        comment.setUserId(responseEntity.getId());
        comment.setComment(req.getComment());
        comment = commentRepository.save(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        PhAllCommentResponse newComment = new PhAllCommentResponse();
        newComment.setId(comment.getId());
        newComment.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
        newComment.setComment(comment.getComment());
        newComment.setListReply(new ArrayList<>());
        newComment.setUserId(responseEntity.getId());
        newComment.setName(responseEntity.getEmail());
        newComment.setAvatar(responseEntity.getPicture());
        loggerUtil.sendLog("Comment với nội dung: " + comment.getComment(), event.get().getId());
        return newComment;
    }

    @Override
    public List<PhAllCommentResponse> getComment(String eventId, int pageNumber) {
        long a = new Date().getTime();
        if (!eventRepository.existsById(eventId)) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (pageNumber < 0) {
            throw new RestApiException(Message.PAGE_NOT_EXIST);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Pageable pageable = PageRequest.of(pageNumber, 10);
        //List chứa ID các user cần lấy thông tin
        List<String> lstUserIdToGet = new ArrayList<>();
        // List chứa comment để trả về cho FE
		List<PhAllCommentResponse> listComment = new ArrayList<>();
        //List chứa các reply comment
        List<PhReplyCommentResponse> listReplyComment = new ArrayList<>();
        List<String> listReplyCommentId = new ArrayList<>();

        Page<Comment> commentFromDB = commentRepository.findByEventIdAndReplyIdIsNullOrderByLastModifiedDateDesc(eventId, pageable);

        for (Comment comment : commentFromDB) {
                PhAllCommentResponse commentResponse = new PhAllCommentResponse();
                commentResponse.setTotalPages(commentFromDB.getTotalPages());
                commentResponse.setCurrentPage(commentFromDB.getNumber());
                commentResponse.setId(comment.getId());
                commentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
                commentResponse.setComment(comment.getComment());
                commentResponse.setUserId(comment.getUserId());
                commentResponse.setIsReply(false);
				commentResponse.setListReply(new ArrayList<>());
                lstUserIdToGet.add(comment.getUserId());
                listReplyCommentId.add(comment.getId());
				listComment.add(commentResponse);
        }
        List<PhCommentResponse> listReply = commentRepository.getReplyCommentByReplyIdAndEventId(eventId, listReplyCommentId);
		listReply.forEach(comment -> {
            PhReplyCommentResponse replyCommentResponse = new PhReplyCommentResponse();
                replyCommentResponse.setId(comment.getId());
                replyCommentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
                replyCommentResponse.setComment(comment.getComment());
                replyCommentResponse.setReplyId(comment.getReplyId());
                replyCommentResponse.setUserId(comment.getUserId());

                lstUserIdToGet.add(comment.getUserId());
                listReplyComment.add(replyCommentResponse);
        });
        listReplyComment.forEach(replyComment -> {
            listComment.forEach(comment -> {
                if (comment.getId().equals(replyComment.getReplyId())) {
                    comment.addElementToListReply(replyComment);
                }
            });
        });

        if (lstUserIdToGet.size() > 0) {
            //Lấy thông tin user từ list phía trên
        	List<SimpleResponse> listUser = callApiIdentity.handleCallApiGetListUserByListId(lstUserIdToGet);
            //Map chứa User
            Map<String, SimpleResponse> mapUser = new HashMap<>();
            listUser.forEach(user -> {
                mapUser.put(user.getId(), user);
            });
            listComment.forEach(comment -> {
                //Duyệt qua các phần tử comment và reply comment để gán các thông tin user được lấy từ Map thông qua UserId
                SimpleResponse commentUser = mapUser.get(comment.getUserId());
                if (commentUser != null) {
                    comment.setUserId(commentUser.getId());
                    comment.setName(commentUser.getName());
                    comment.setAvatar(commentUser.getPicture());
                }
                if (comment.getListReply() != null) {
                    for (PhReplyCommentResponse replyComment : comment.getListReply()) {
                        SimpleResponse replyCommentUser = mapUser.get(replyComment.getUserId());
                        if (replyCommentUser != null) {
                            replyComment.setUserId(replyCommentUser.getId());
                            replyComment.setEmail(replyCommentUser.getName());
                            replyComment.setAvatar(replyCommentUser.getPicture());
                        }
                    }
                }
            });
        }
        // Vì khi lấy comment ra đang sắp xếp giảm dần theo thời gian comment nên cần đảo ngược để
        // khi trả về cho FE sẽ hiển thị được các comment mới nhất xuống dưới
        Collections.reverse(listComment);
        return listComment;
    }

    @Override
    @Synchronized
    public Boolean deleteComment(PhDeleteCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        Optional<Comment> comment = commentRepository.findById(req.getCommentId());
        if (!comment.isPresent()) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }
        if (!comment.get().getUserId().equals(userId)) {
            throw new RestApiException(Message.DONT_HAVE_PERMISSION_DELETE);
        }
        if (comment.get().getReplyId() == null) {
            List<Comment> listReply = commentRepository.getReplyCommentByReplyId(comment.get().getId());
            commentRepository.deleteAll(listReply);
        }
        commentRepository.delete(comment.get());
		loggerUtil.sendLog("Xóa comment có nội dung: " + comment.get().getComment(), comment.get().getEventId());
        return true;
    }

    @Override
    @Synchronized
    public PhReplyCommentResponse replyComment(PhReplyCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        if (!eventRepository.existsById(req.getEventId())) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        SimpleResponse responseEntity = callApiIdentity.handleCallApiGetUserById(userId);
        if (responseEntity == null) {
            throw new RestApiException(Message.PARTICIPANT_NOT_EXIST);
        }
        Optional<Participant> participant = participantRepository.getByIdEventAndIdUser(req.getEventId(), userId);
        if (!participant.isPresent()) {
            throw new RestApiException(Message.PARTICIPANT_NOT_IN_EVENT);
        }
        if (!commentRepository.existsById(req.getReplyId())) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }
        Comment replyComment = new Comment();
        replyComment.setEventId(req.getEventId());
        replyComment.setComment(req.getComment());
        replyComment.setUserId(responseEntity.getId());
        replyComment.setReplyId(req.getReplyId());
        replyComment = commentRepository.save(replyComment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        PhReplyCommentResponse commentResponse = new PhReplyCommentResponse();
        commentResponse.setId(replyComment.getId());
        commentResponse.setLastModifiedDate(dateFormat.format(new Date(replyComment.getLastModifiedDate())));
        commentResponse.setComment(replyComment.getComment());
        commentResponse.setReplyId(replyComment.getReplyId());
        commentResponse.setUserId(responseEntity.getId());
        commentResponse.setEmail(responseEntity.getEmail());
        commentResponse.setAvatar(responseEntity.getPicture());
        loggerUtil.sendLog("Đã trả lời comment với nội dung: " + replyComment.getComment(), replyComment.getEventId());
        return commentResponse;
    }


    /**
     * Hàm lấy chi tiết 1 sự kiện
     * @param id: ID của SK cần lấy chi tiết
     * @return Response: Gồm các thông tin của SK, DS NTC, DS resource, DS Location, DS Object, DS Major
     */
    @Override
    public List<PhDetailEventResponse> eventDetail(String id) {
        Map<String, String> organizers = new HashMap<>();
        List<PhDetailEventResponse> eventResponses = eventRepository.detail(id);
        if (!eventResponses.isEmpty()) {
            eventResponses.forEach(event -> {
                if (organizers.get(event.getOrganizerId()) == null) {
                    organizers.put(event.getOrganizerId(), "");
                }
            });
            List<SimpleResponse> listOrganizer = callApiIdentity.handleCallApiGetListUserByListId(organizers.keySet().stream().toList());
            listOrganizer.forEach(item -> {
                if (organizers.get(item.getId()) != null) {
                    organizers.put(item.getId(), item.getName() + " - " + item.getUserName());
                }
            });
            eventResponses.get(0).setListOrganizer(organizers.values().stream().toList());
            Participant participant = participantRepository.getByEventIdAndIdUser(eventResponses.get(0).getId(), portalEventsSession.getCurrentIdUser());
            if (participant == null) {
            	eventResponses.get(0).setIsRegisted(false);
				eventResponses.get(0).setIsAttended(false);
            } else {
                eventResponses.get(0).setIsRegisted(true);
                if (participant.getAttendanceTime() != null) {
                    eventResponses.get(0).setIsAttended(true);
                } else {
                    eventResponses.get(0).setIsAttended(false);
                }
            }
        } else {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        return eventResponses;
    }

}
