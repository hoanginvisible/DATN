import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/event-approval`;
export class APEventWaitingApprovalApi {
  

  static fetchListCategory = () => {
    return request({
      method: "GET",
      url: api + `/event-category/list`,
    });
  };

  static fetchListEventMajor = () => {
    return request({
      method: "GET",
      url: api + `/event-major/list`,
    });
  };

  static fetchListEventWaiting = (data) => {
    return request({
      method: "POST",
      url: api + `/list-event-waiting-approve`,
      data: data
    });
  };

  static detail = (idEvent) => {
    return request({
      method: "GET",
      //Muốn thêm Path Variable thì điền trực tiếp ?<<tên biên>=<<value>> đường link của thuộc tính URL
      url: api + `/detail/` + idEvent,
      //Muốn thêm Request Params thì thêm vào thuộc tính params
      //params: {
        //pageIndex : 1
      //}
    });
  };

  static register = (data) => {
    return request({
      method: "POST",
      url: URL_API_APRROVER_MANAGEMENT + `/event-detail`,
      data: data,
    });
  };
}