import React, {useEffect, useState} from "react";
import {Button, Col, Row} from "antd";
import PersonIcon from "@mui/icons-material/Person";
import ApproverIcon from "../../assets/img/t3.png";
import OrganizerIcon from "../../assets/img/t2.png";
import logoUDPM from "../../assets/img/logo-udpm-dark.png";
import logoFPT from "../../assets/img/Logo_FPoly.png";
import "./style.css";
import {getCurrentUser} from "../../utils/Common";
import {ACTOR_ADMINISTRATIVE, ACTOR_APPROVER, ACTOR_ORGANIZER, ACTOR_PARTICIPANT} from "../../constants/ActorConstant";

export default function ORPermission() {

    useState(() => {
        const user = getCurrentUser();
        if (typeof user.role === 'string') {
            if (user.role === ACTOR_APPROVER) {
                window.location.href = "/approver-management/event-waiting-approval";
            } else if (user.role === ACTOR_ORGANIZER) {
                window.location.href = "/organizer-management/statistics-event";
            } else if (user.role === ACTOR_PARTICIPANT) {
                window.location.href = "/home";
            } else if (user.role === ACTOR_ADMINISTRATIVE) {
                window.location.href = "/organizer-management/statistics-event";
            } else {
                window.location.href = "/unknown-error";
            }
        } else if (typeof user.role === 'object') {
            if (!((user.role.includes(ACTOR_APPROVER) && user.role.includes(ACTOR_ORGANIZER)) || (user.role.includes(ACTOR_APPROVER && user.role.includes(ACTOR_ADMINISTRATIVE))))) {
                if (user.role.includes(ACTOR_ORGANIZER)) {
                    window.location.href = "/organizer-management/statistics-event";
                } else if (user.role.includes(ACTOR_ADMINISTRATIVE)) {
                    window.location.href = "/organizer-management/statistics-event";
                } else if (user.role.includes(ACTOR_APPROVER)) {
                    window.location.href = "/approver-management/event-waiting-approval";
                } else if (user.role.includes(ACTOR_PARTICIPANT)) {
                    window.location.href = "/home";
                }
            }
        }
    }, []);

    const redirectToOrganizer = () => {
        window.location.href = "/organizer-management/statistics-event";
    };

    const redirectToApprover = () => {
        window.location.href = "/approver-management/event-waiting-approval";
    };

    useEffect(() => {
    }, []);

    return (
        <>
            {/* Thẻ cha bao bọc các thẻ con chứa nó, dùng để làm background */}
            <div className="background-permission">
                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                    }}
                >
                    {/* Phần logo của màn hình role */}
                    <Row>
                        <Col>
                            <img
                                src={logoFPT}
                                width={200}
                                alt="logo Fpt Polytechnic"
                            />
                        </Col>
                        <Col>
                            <img src={logoUDPM} width={200} alt="logo xưởng thực hành"/>
                        </Col>
                    </Row>
                    {/* **********************END********************** */}

                    {/* Title của màn hình */}
                    <Row style={{marginTop: "30px"}}>
                        <h1
                            style={{
                                fontFamily: "Nunito",
                                fontWeight: "bolder",
                                fontSize: "40px",
                            }}
                        >
                            Thay đổi vai trò quản lý sự kiện
                        </h1>
                    </Row>
                    {/* **********************END********************** */}

                    {/* Images và button chuyển role */}
                    <Row
                        style={{
                            marginTop: "60px",
                            justifyContent: "space-between", // Đảm bảo các phần tử sẽ nằm cách xa nhau ngang bằng nhau
                        }}
                    >
                        <div
                            className="image-permission"
                            onClick={() => redirectToOrganizer()}
                            style={{
                                margin: "10px",
                                marginLeft: "40px",
                                marginRight: "100px",
                                position: "relative",
                                color: "white",
                                textAlign: "center", // Căn giữa văn bản
                                cursor: "pointer", // Thay đổi con trỏ khi di chuột vào
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "center", // Căn giữa icon và văn bản theo chiều dọc
                            }}
                        >
                            <Row
                                style={{
                                    display: "flex",
                                    alignItems: "center",
                                    flexDirection: "column",
                                }}
                            >
                                <img
                                    src={OrganizerIcon}
                                    alt="Người tổ chức"
                                    style={{
                                        width: "300px",
                                        height: "auto",
                                        transition: "transform 0.3s", // Hiệu ứng hover
                                    }}
                                    onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                    onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                />
                                <Button
                                    className="button-role"
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        fontSize: "20px",
                                        width: "auto",
                                        height: "40px",
                                        marginTop: "15px",
                                        color: "white",
                                        background: "#0099FF",
                                    }}
                                >
                                    <PersonIcon/>
                                    Người tổ chức
                                </Button>
                            </Row>
                        </div>
                        <div
                            className="image-permission"
                            onClick={() => redirectToApprover()}
                            style={{
                                margin: "10px",
                                position: "relative",
                                color: "black",
                                animation: "slideInFromRight 1s ease forwards",
                                textAlign: "center", // Căn giữa văn bản
                                cursor: "pointer", // Thay đổi con trỏ khi di chuột vào
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "center", // Căn giữa icon và văn bản theo chiều dọc
                            }}
                        >
                            <Row
                                style={{
                                    display: "flex",
                                    alignItems: "center",
                                    flexDirection: "column",
                                }}
                            >
                                <img
                                    src={ApproverIcon}
                                    alt="Người phê duyệt"
                                    style={{
                                        width: "288px",
                                        height: "auto",
                                        transition: "transform 0.3s", // Hiệu ứng hover
                                    }}
                                    onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                    onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                />
                                <Button
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        fontSize: "20px",
                                        marginTop: "15px",
                                        width: "auto",
                                        height: "40px",
                                        color: "white",
                                        background: "#0099FF",
                                    }}
                                >
                                    <PersonIcon/>
                                    Người phê duyệt
                                </Button>
                            </Row>
                        </div>
                    </Row>

                    {/* **********************END********************** */}
                    {/* Footer màn hình */}
                    <div className="footer">
                        <div className="content">
                            <div className="address">
                                <h2>
                                    Trụ sở chính Tòa nhà FPT Polytechnic, Phố Trịnh Văn Bô, Nam Từ
                                    Liêm, Hà Nội
                                </h2>
                            </div>
                        </div>
                    </div>
                    {/* **********************END********************** */}
                </div>
            </div>
        </>
    );
}
