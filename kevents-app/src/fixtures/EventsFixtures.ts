import { Event, EventsResult } from "../features/eventsSlice";

export const expectedEventsResult: EventsResult = {
  currentPage: 0,
  totalEvents: 2,
  totalPages: 1,
  events: [
    {
      name: "event1",
      description: "description1",
      startDate: "startDate1",
      endDate: "endDate1",
    },
    {
      name: "event2",
      description: "description2",
      startDate: "startDate2",
      endDate: "endDate2",
    },
  ],
};

export const expectedEmptyEventsResult: EventsResult = {
  currentPage: 0,
  totalEvents: 0,
  totalPages: 0,
  events: [],
};

export const mockEvent: Event = {
  name: "event1",
  description: "description1",
  startDate: "2022-01-16T02:17:19.464Z",
  endDate: "2022-01-16T02:17:19.464Z",
};
