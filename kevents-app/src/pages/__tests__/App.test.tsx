import React from "react";
import { render, screen } from "@testing-library/react";
import App from "./../App";

test("renders menu", () => {
  render(<App />);
  const appName = screen.getByText("KEvents");
  const newEventMenuOption = screen.getByText("New event");
  const listEventsMenuOption = screen.getByText("List events");

  expect(appName).toBeInTheDocument();
  expect(newEventMenuOption).toBeInTheDocument();
  expect(listEventsMenuOption).toBeInTheDocument();
});

test("renders footer", () => {
  render(<App />);
  const footer = screen.getByText("©2022 Created by Thomas Perron");

  expect(footer).toBeInTheDocument();
});
