import Cookies from "js-cookie";
import jwtDecode from "jwt-decode";
import {ACTOR_ADMINISTRATIVE, ACTOR_APPROVER, ACTOR_ORGANIZER, ACTOR_PARTICIPANT} from "../constants/ActorConstant";

export function getCurrentUser() {
    const token = Cookies.get("token");
    if (token) {
        try {
            const payload = jwtDecode(token);
            return {
                exp: payload.exp,
                email: payload.email,
                id: payload.id,
                name: payload.name,
                avatar: payload.picture,
                role: payload.role,
                userName: payload.userName
            };
        } catch (err) {
            window.location.href = "/not-authorization";
        }
    } else {
        window.location.href = "/not-authorization";
    }
    return undefined;
}

export function convertRole(role) {
    if (role === ACTOR_APPROVER) {
        return 'Người phê duyệt';
    } else if (role === ACTOR_ORGANIZER) {
        return 'Người tổ chức';
    } else if (role === ACTOR_PARTICIPANT) {
        return 'Người tham gia';
    } else if (role === ACTOR_ADMINISTRATIVE) {
        return 'Hành chính';
    }
}