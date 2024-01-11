import React, { useEffect, useState } from "react";
import {
  Modal,
  Button,
  Select,
  message,
  Form,
  Row,
  Col,
  Radio,
  Input,
  Popconfirm,
} from "antd";
import "./Style-update.css";
import OREventDetailApi from "../../OREventDetailApi";
import { useParams } from "react-router-dom";

const ModalUpdateOrganizer = ({ Organizer, visible, onCancel, checkRole }) => {
  const { id } = useParams();
  const [role, setRole] = useState(Organizer.eventRole);
  const [organizerId, setOrganizerId] = useState(Organizer.organizerId);

  useEffect(() => {
    setRole(Organizer.eventRole);
    setOrganizerId(Organizer.organizerId);
  }, [Organizer]);

  const updateEventOrganizer = () => {
    let data = {
      id: Organizer.id,
      organizerId: organizerId,
      organizerUser: Organizer.username,
      eventRole: role,
      eventId: id,
    };

    OREventDetailApi.updateEventOrganizer(data).then(
      (response) => {
        // window.location.href = "/organizer-management/event-detail/" + id;
        onCancel();
        checkRole();
        message.success("Cập nhật thành công");
      },
      (error) => {
        message.error(error.response.data.message);
      }
    );
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width="60%"
        footer={null}
        className="modal_show_detail"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Cập nhật Người tổ chức</span>
          </div>
          <div
            style={{
              marginTop: "15px",
              borderBottom: "1px solid black",
              marginBottom: "15px",
            }}
          >
            <div>
              <Form labelCol={{ span: 8 }}>
                <Row gutter={16}>
                  <Col span={12} className="col-left">
                    <Form.Item label="Người tổ chức">
                      <Input value={Organizer.name} readOnly />
                    </Form.Item>
                  </Col>

                  <Col span={11}>
                    <Form.Item label="Vai trò">
                      <Radio.Group
                        name="role"
                        value={role}
                        onChange={(e) => {
                          setRole(e.target.value);
                        }}
                      >
                        <Radio value={0}>Host</Radio>
                        <Radio value={1}>Co-Host</Radio>
                      </Radio.Group>
                    </Form.Item>
                  </Col>
                </Row>
              </Form>
              <br />
            </div>
          </div>

          <div style={{ textAlign: "right", marginTop: "15px" }}>
            <div style={{ paddingTop: "15px" }}>
              <Popconfirm
                title="Bạn chắc chắn muốn cập nhật không?"
                onConfirm={() => {
                  updateEventOrganizer();
                }}
                okText="Có"
                cancelText="Không"
              >
                <Button
                  style={{
                    marginRight: "5px",
                    backgroundColor: "rgb(61, 139, 227)",
                    color: "white",
                  }}
                >
                  Cập nhật
                </Button>
              </Popconfirm>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalUpdateOrganizer;
