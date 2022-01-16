import React from "react";
import { render, screen } from "@testing-library/react";
import App from "../App";

const expectedAppName = "KEvents";
const expectedNewEventOption = "New event";
const expectedListEventsOption = "List events";
const expectedFooterContent = "©2022 Created by Thomas Perron";

test("renders menu", () => {
  render(<App />);
  const appName = screen.getByText(expectedAppName);
  const newEventMenuOption = screen.getByText(expectedNewEventOption);
  const listEventsMenuOption = screen.getByText(expectedListEventsOption);

  expect(appName).toBeInTheDocument();
  expect(newEventMenuOption).toBeInTheDocument();
  expect(listEventsMenuOption).toBeInTheDocument();
});

test("renders footer", () => {
  render(<App />);
  const footer = screen.getByText(expectedFooterContent);

  expect(footer).toBeInTheDocument();
});
