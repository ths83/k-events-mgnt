import { SyncOutlined } from "@ant-design/icons";
import Button from "antd/lib/button";
import List from "antd/lib/list";
import Title from "antd/lib/typography/Title";
import axios from "axios";
import { isEmpty } from "lodash";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { EventsResult, setEvents } from "../../features/eventsSlice";
import { RootState } from "../app/store";
import ErrorMessage from "../errorMessage/CustomErrorMessage";
import EventItem from "./event/EventItem";
import "./events.css";

const Events = () => {
  const defaultPageSize = 5;

  const dispatch = useDispatch();

  const {
    eventsReducer: { result },
  } = useSelector((state: RootState) => state);

  const totalEventsNumber = result.totalEvents || 0;

  const [networkError, setNetworkError] = useState("");
  const hasErrors = !isEmpty(networkError);

  const getEvents = (page: number) => {
    setNetworkError("");
    axios
      .get(`http://localhost:8080/events?page=${page}&size=${defaultPageSize}`)
      .then((response) => dispatch(setEvents(response.data as EventsResult)))
      .catch((error) => setNetworkError(error.response.data.message));
  };

  useEffect(() => {
    getEvents(0);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <Title level={3}>List of events</Title>
      <List
        bordered
        pagination={{
          onChange: (page) => {
            const pageIndex = page - 1;
            getEvents(pageIndex);
          },
          pageSize: defaultPageSize,
          total: totalEventsNumber,
          showTotal: (total, range) =>
            `${range[0]}-${range[1]} of ${totalEventsNumber} items`,
        }}
        dataSource={result.events}
        renderItem={(item) => <EventItem item={item} />}
      />
      <div className="button">
        <Button
          id="refresh"
          shape="round"
          type="primary"
          icon={<SyncOutlined />}
          onClick={() => getEvents(0)}
        >
          Refresh
        </Button>
      </div>
      {hasErrors && <ErrorMessage message={networkError} />}
    </>
  );
};

export default Events;
