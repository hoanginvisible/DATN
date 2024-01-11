import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_ORGANIZER_MANAGEMENT + `/attendance-list`;
export class ORAttendaceListApi {
  static fetchAttendanceList = (data) => {
    return request({
        method: "POST",
        url: api,
        data: data,
    });
  };

  static register = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail`,
      data: data,
    });
  };
}