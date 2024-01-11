import React, {Suspense, useEffect} from "react";

// react-router components
import {Routes, Route, BrowserRouter, Navigate} from "react-router-dom";
import Cookies from "js-cookie";
// Icon Fonts
import AuthGuard from "./guard/AuthGuard";
import NotFound from "./pages/ErrorPage/404";
import NotAuthorized from "./pages/ErrorPage/401";
import OrganizerManagement from "./layout/organizer/OrganizerManagement";
import OrganizerRoutes from "./layout/organizer/OrganizerRoutes";
import ApproverManagement from "./layout/approver/ApproverManagement";
import ApproverRoutes from "./layout/approver/ApproverRoutes";
import HomeComponent from "./pages/Home";
import APEventDetail from "./pages/ApproverManagement/APEventDetail/index";
import APAttendanceList from "./pages/ApproverManagement/APAttendanceList/index";
import OREventDetail from "./pages/OrganizerManagement/OREventDetail/index";
import ORAttendanceList from "./pages/OrganizerManagement/ORAttendanceList/index";
import ORRegistrationList from "./pages/OrganizerManagement/ORRegistrationList/index";
import APRegistrationList from "./pages/ApproverManagement/APRegistrationList";
import ORPermission from "./pages/ORPermission/index";
import Forbidden from "./pages/ErrorPage/403";
import NotAceptable from "./pages/ErrorPage/NotAceptable";
import { useAppSelector } from "./app/hook";
import {GetCountRequest, GetLoading, SetLoadingFalse, SetLoadingTrue} from "./app/reducer/Loading.reducer";
import LoadingIndicator from "./helper/LoadingIndicator";
import UnknownError from "./pages/ErrorPage/500";
import {dispatch} from "./app/store";
import Loading from "./utils/Loading/Loading";

export default function App() {

    const previousURL = window.location.search;
    const previousURLParams = new URLSearchParams(previousURL);
    const tokenFromPreviousURL = previousURLParams.get("Token");
    if (tokenFromPreviousURL) {
        Cookies.set("token", tokenFromPreviousURL, {expires: 365});
    }

    const ORGANIZER_MANAGEMENT_URL = '/organizer-management';
    const APPROVER_MANAGEMENT_URL = '/approver-management';
    // const PARTICIPANT_URL = '/';

    const getOrganizerRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getOrganizerRoutes(route.collapse);
            }
            if (route.route && route.type !== "redirect") {
                return (
                    <Route
                        exact
                        path={ORGANIZER_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <OrganizerManagement urlRole={ORGANIZER_MANAGEMENT_URL}
                                                     routeName={route.name}>{route.component}</OrganizerManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const getApproverRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getApproverRoutes(route.collapse);
            }
            if (route.route) {
                return (
                    <Route
                        exact
                        path={APPROVER_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <ApproverManagement routeName={route.name}
                                                    urlRole={APPROVER_MANAGEMENT_URL}>{route.component}</ApproverManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

        const loadingOverlay = useAppSelector(GetLoading);
        const countRequest = useAppSelector(GetCountRequest);
        useEffect(() => {
            // console.log("aaaaaaaaaaaa " + countRequest)
            if(countRequest > 0) {
                dispatch(SetLoadingTrue());
            }
            if(countRequest === 0) {
                dispatch(SetLoadingFalse());
            }
        }, [countRequest]);

    return (
        <div className="App scroll-smooth md:scroll-auto font-sans">
            {loadingOverlay && <Loading />}
            <BrowserRouter>
                <Suspense>
                    <Routes>
                        <Route path="*" element={<NotFound/>}/>
                        <Route path="/not-authorization" key="not-authorization" element={<NotAuthorized/>}/>
                        <Route path="/forbidden" key="forbidden" element={<Forbidden/>}/>
                        <Route path="/not-aceptable/*" key="not-aceptable" element={<NotAceptable/>}/>
                        <Route path="/unknown-error" key="unknown-error" element={<UnknownError/>}/>
                        {getApproverRoutes(ApproverRoutes)}
                        {getOrganizerRoutes(OrganizerRoutes)}
                        {/* Trang mặc định */}
                        <Route path="/" element={<Navigate replace to="/permission-event"/>}/>
                        <Route
                            path="/home"
                            name="home"
                            element={
                                <AuthGuard>
                                    <HomeComponent/>
                                </AuthGuard>
                            }
                        />
                        {/* Những màn hình không hiển thị nút bấm trên NAV hoặc menu thì viết như dưới đây */}
                        <Route path={APPROVER_MANAGEMENT_URL + "/event-detail/:id"} key={'event-detail'}
                               element={
                                   <AuthGuard>
                                       <ApproverManagement routeName={'Chi tiết sự kiện'}
                                                           urlRole={APPROVER_MANAGEMENT_URL}>
                                           <APEventDetail/>
                                       </ApproverManagement>
                                   </AuthGuard>
                               }/>
                        <Route path={APPROVER_MANAGEMENT_URL + "/event-attendance/:id"} key={'attendance-list'}
                               element={
                                   <AuthGuard>
                                       <ApproverManagement routeName={'Danh sách điểm danh'}
                                                           urlRole={APPROVER_MANAGEMENT_URL}>
                                           <APAttendanceList/>
                                       </ApproverManagement>
                                   </AuthGuard>
                               }/>
                        <Route path={APPROVER_MANAGEMENT_URL + "/registration-list/:id"} key={'register-list'}
                               element={
                                   <AuthGuard>
                                       <ApproverManagement routeName={'Danh sách đăng ký'}
                                                           urlRole={APPROVER_MANAGEMENT_URL}>
                                           <APRegistrationList/>
                                       </ApproverManagement>
                                   </AuthGuard>
                               }/>
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/event-detail/:id"}
                            key={"event-detail"}
                            element={
                                <AuthGuard>
                                    <OrganizerManagement routeName={'Chi tiết sự kiện'}
                                                         urlRole={ORGANIZER_MANAGEMENT_URL}>
                                        <OREventDetail/>
                                    </OrganizerManagement>
                                </AuthGuard>
                            }
                        />
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/attendance-list/:id"}
                            key={"or-attendance-list"}
                            element={
                                <AuthGuard>
                                    <OrganizerManagement routeName={'Danh sách điểm danh'}
                                                         urlRole={ORGANIZER_MANAGEMENT_URL}>
                                        <ORAttendanceList/>
                                    </OrganizerManagement>
                                </AuthGuard>
                            }
                        />
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/registration-list/:id"}
                            key={"or-registration-list"}
                            element={<AuthGuard>
                                <OrganizerManagement routeName={'Danh sách đăng ký'} urlRole={ORGANIZER_MANAGEMENT_URL}>
                                    <ORRegistrationList/>
                                </OrganizerManagement>
                            </AuthGuard>}
                        />
                        <Route
                            path={"/permission-event"}
                            key={"permission-event"}
                            element={<ORPermission/>}
                        />
                    </Routes>
                </Suspense>
            </BrowserRouter>
        </div>
    );
}
