import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

export class APRegistrationListApi {
    // lấy ra danh sách người đăng ký
    static fetchAll = (id, data) => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/registration-list/${id}`,
            params: {
                className: data?.className,
                email: data?.email,
                lecturer: data?.lecturer,
                page: data?.page,
                size: data?.size,
            }
        });
    };

    // lấy ra danh sách để xuất file excel
    static getListExport = (id, data) => {
        return request({
            method: "GET",
            url: URL_API_APRROVER_MANAGEMENT + `/registration-list/${id}`,
            params: {
                className: data?.className,
                email: data?.email,
                lecturer: data?.lecturer,
                size: 1_000_000,
            }
        });
    };
}