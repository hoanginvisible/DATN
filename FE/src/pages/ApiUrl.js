// API URL
const domain = process.env.REACT_APP_HOST_IP_ADDRESS;
export const portIdentity = process.env.REACT_APP_IDENTITY_WEB;
export const URL_API_PARTICIPANT = domain + 'api/home'
export const URL_API_ORGANIZER_MANAGEMENT = domain + 'api/organizer-management';
export const URL_API_APRROVER_MANAGEMENT = domain + 'api/approver-management';
export const URL_API_ADMINISTRATIVE = domain + "api/administrative";
export const URL_API_SYSTEM = domain + "api/system";

class CommonAPI{
    static ADD = '/add';
    static UPDATE = '/update';
    static DELETE = '/delete';
    static DETAIL = '/detail';
    static FIND_BY_ID = '/findById';
}

export default CommonAPI;