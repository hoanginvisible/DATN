import React, { useEffect, useState } from "react";
import { Modal, Button, Select, message, Form, Row, Col, Radio } from "antd";
import "./style-modal-add-organizer.css";
import OREventDetailApi from "../../OREventDetailApi";
import { useParams } from "react-router-dom";

const { Option } = Select;

const ModalAddOrganizer = ({ visible, onCancel, loadOrganizers }) => {
  const { id } = useParams();
  const [role, setRole] = useState();
  const [organizerId, setOrganizerId] = useState('');
  const [listOrganizerNotInEvent, setListOrganizerNotInEvent] = useState([]);

  const handleChangeListOrganizer = (e) => {
    setOrganizerId(e);
  };

  useEffect(() => {
    loadDataOrganizerNotInEvent();

    return () => {
      setListOrganizerNotInEvent([]);
    };
  }, [visible]);

  const loadDataOrganizerNotInEvent = () => {
    OREventDetailApi.getOrganizersNotInEvent(id).then((response) => {
      setListOrganizerNotInEvent(response.data.data);
    });
  };

  const createEventOrganizer = () => {
    let data = {
      organizerId: organizerId,
      eventRole: role,
      eventId: id,
    };

    OREventDetailApi.createEventOrganizer(data).then(
      (response) => {
        // window.location.href = "/organizer-management/event-detail/" + id;
        onCancel();
        setOrganizerId('');
        loadOrganizers();
        message.success("Thêm thành công");
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
        width="55%"
        footer={null}
        className="modal_show_detail"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Thêm người tổ chức</span>
          </div>
          <div
            style={{
              marginTop: "15px",
              borderBottom: "1px solid black",
              marginBottom: "15px",
            }}
          >
            <div style={{ marginBottom: "15px" }}>
              <Form labelCol={{ span: 8 }}>
                <Row gutter={16}>
                  <Col span={12} className='col-left'>
                    <Form.Item label="Người tổ chức">
                      <Select
                        placeholder="Thêm người tổ chức"
                        style={{
                          width: "100%",
                          height: "auto",
                        }}
                        value={organizerId}
                        onChange={handleChangeListOrganizer}
                        optionLabelProp="label"
                        defaultValue={''}
                        filterOption={(text, option) =>
                          option.label.toLowerCase().indexOf(text.toLowerCase()) !== -1
                        }
                      >
                        {listOrganizerNotInEvent.map((item) => (
                          <Option label={item.email} key={item.id} value={item.id}>
                            {item.name + " (" + item.email + ")"}
                          </Option>
                        ))}
                      </Select>{" "}
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
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={createEventOrganizer}
              >
                Thêm
              </Button>
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
export default ModalAddOrganizer;
