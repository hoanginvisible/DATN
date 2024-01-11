package com.portalevent.infrastructure.session;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface PortalEventsSession {

    String getToken();

    String getCurrentIdUser();

    String getCurrentEmail();

    String getCurrentUserName();

    String getCurrentName();

    List<String> getCurrentUserRole();

}
