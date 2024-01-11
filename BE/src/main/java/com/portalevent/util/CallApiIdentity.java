package com.portalevent.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalevent.core.common.RolesResponse;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.infrastructure.apiconstant.ApiConstants;
import com.portalevent.infrastructure.apiconstant.PortalEventConstants;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.logger.LoggerObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Component
public class CallApiIdentity {

    @Autowired
    private PortalEventsSession portalEventsSession;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${domain.identity}")
    private String domainIdentity;

    /**
     * @param listIdUser: Truyền một list id của các user cần lấy thông tìn
     * @return Trả về 1 list SimpleResponse (là các user có id được truyền vào bằng list)
     */
    public List<SimpleResponse> handleCallApiGetListUserByListId(List<String> listIdUser) {
        try {
            String apiUrl = domainIdentity + ApiConstants.API_GET_USER_BY_LIST_ID;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String authorizationToken = "Bearer " + portalEventsSession.getToken();
            headers.set("Authorization", authorizationToken);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonList = null;
            try {
                jsonList = objectMapper.writeValueAsString(listIdUser);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

            HttpEntity<String> httpEntity = new HttpEntity<>(jsonList, headers);

            ResponseEntity<List<SimpleResponse>> responseEntity =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
                            new ParameterizedTypeReference<List<SimpleResponse>>() {
                            });

            List<SimpleResponse> response = responseEntity.getBody();
            return response;
        } catch (Exception e) {
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    /**
     * @param roleCode: Truyền vào Constants role
     * @return List tất cả các user có role được truyền vào
     */
    public List<SimpleResponse> handleCallApiGetUserByRoleAndModule(String roleCode) {
        try {
            String apiUrl = domainIdentity + ApiConstants.API_GET_ALL_USER_BY_ROLE_AND_MODULE;

            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + portalEventsSession.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<List<SimpleResponse>> responseEntity =
                    restTemplate.exchange(apiUrl + "?roleCode=" + roleCode + "&moduleCode=" + PortalEventConstants.MODULE_CODE, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<List<SimpleResponse>>() {
                            });

            List<SimpleResponse> response = responseEntity.getBody();
            return response;
        } catch (Exception e) {
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    /**
     * @param idUSer: Truyền vào id của 1 user cần lấy thông tin
     * @return Trả về thông tin của 1 user
     */
    public SimpleResponse handleCallApiGetUserById(String idUSer) {
        try {
            String apiUrl = domainIdentity + ApiConstants.API_GET_USER_BY_ID;
            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + portalEventsSession.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<SimpleResponse> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + idUSer, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<SimpleResponse>() {
                            });

            SimpleResponse response = responseEntity.getBody();
            return response;
        } catch (Exception e) {
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public Object handleCallApiGetRoleUserByIdUserAndModuleCode(String idUser) {
        try {
            String apiUrl = domainIdentity + ApiConstants.API_GET_ROLES_USER_BY_ID_USER_AND_MODULE_CODE;
            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + portalEventsSession.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<List<RolesResponse>> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + idUser + "/" + PortalEventConstants.MODULE_CODE, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<List<RolesResponse>>() {
                            });

            List<RolesResponse> response = responseEntity.getBody();
            List<String> roles = response.stream()
                    .map(RolesResponse::getRoleCode)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            if (roles.size() > 1) {
                return roles;
            }
            return roles.get(0);
        } catch (Exception e) {
            throw new RestApiException(Message.INVALID_USER);
        }
    }
}
