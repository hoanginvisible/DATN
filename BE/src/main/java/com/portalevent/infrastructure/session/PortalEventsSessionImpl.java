package com.portalevent.infrastructure.session;

import com.portalevent.infrastructure.constant.SessionConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class PortalEventsSessionImpl implements PortalEventsSession{

    @Autowired
    private HttpSession session;

    @Override
    public String getToken() {
        return String.valueOf(session.getAttribute(SessionConstant.TOKEN));
    }

    @Override
    public String getCurrentIdUser() {
        return String.valueOf(session.getAttribute(SessionConstant.IDUSER));
    }

    @Override
    public String getCurrentEmail() {
        return String.valueOf(session.getAttribute(SessionConstant.EMAIL));
    }

    @Override
    public String getCurrentUserName() {
        return String.valueOf(session.getAttribute(SessionConstant.USERNAME));
    }

    @Override
    public String getCurrentName() {
        return String.valueOf(session.getAttribute(SessionConstant.NAME));
    }

    @Override
    public List<String> getCurrentUserRole() {
        return (List<String>) session.getAttribute(SessionConstant.ROLE);
    }

}
