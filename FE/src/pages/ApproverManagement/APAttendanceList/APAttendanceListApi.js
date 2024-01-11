import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/attendance-list`;
export class APAttendanceListApi {
    static fetchAttendanceList = (data, idEvent) => {
        return request({
            method: "POST",
            url: api + `/` + idEvent,
            data: data,
        });
    };

    static fetchAttendanceListByIdEvent = (idEvent) => {
        return request({
            method: "GET",
            url: api + "/get-attendanse/" + idEvent,
        });
    };
}
