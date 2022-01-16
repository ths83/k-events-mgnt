import React from "react";
import renderer from "react-test-renderer";
import { mockEvent } from "../../../../fixtures/EventsFixtures";
import EventItem from "../EventItem";

test("renders component", () => {
  const item = renderer.create(<EventItem item={mockEvent} />).toJSON();
  expect(item).toMatchSnapshot();
});
