import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

export class ORRegistrationListApi {

  static fetchAll = (data) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/registration-list`,
      params: data
    });
  };

}