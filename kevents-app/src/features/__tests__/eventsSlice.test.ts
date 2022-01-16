import store from "../../components/app/store";
import { setEvents, clearEvents } from "../eventsSlice";
import data from "./fixtures/data.json";
import {
  expectedEmptyEventsResult,
  expectedEventsResult,
} from "./fixtures/EventsFixtures";

test("should fetch events and clear reducer with success", () => {
  store.dispatch(setEvents(data));
  const eventsReducer = store.getState().eventsReducer;
  expect(eventsReducer.result).toEqual(expectedEventsResult);

  store.dispatch(clearEvents());
  const clearedEventsReducer = store.getState().eventsReducer;
  expect(clearedEventsReducer.result).toEqual(expectedEmptyEventsResult);
});
