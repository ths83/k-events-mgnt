import List from "antd/lib/list";
import { memo } from "react";
import { Event } from "../../../features/eventsSlice";
import { toLocalDate } from "../../../utils/date/dateHelper";

interface EventProps {
  item: Event;
}

const EventItem: React.FC<EventProps> = ({ item }) => {
  return (
    <List.Item>
      <List.Item.Meta title={item.name} description={item.description} />
      {`From ${toLocalDate(item.startDate)} to ${toLocalDate(item.endDate)}`}
    </List.Item>
  );
};

export default memo(EventItem);
