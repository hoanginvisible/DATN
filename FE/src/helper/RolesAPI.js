import { request } from "./Request.helper";

export class RolesAPI {
  static getRolesUser = (idUser) => {
    return request({
      method: "GET",
      url: `https://103.56.161.210:1626/roles?idUser=` + idUser,
    });
  };
}
