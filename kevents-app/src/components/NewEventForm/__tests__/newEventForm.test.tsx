import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import NewEventForm from "../NewEventForm";

const expectedTitle = "New event form";
const expectedNameInputTitle = "Name";
const expectedDescInputTitle = "Description";
const expectedStartDateInputTitle = "Start Date";
const expectedEndDateInputTitle = "End Date";
const expectedResetButton = "Reset";
const expectedSubmitButton = "Submit";

const expectedNameInputPlaceholder = "Enter an event name";
const expectedDescInputPlaceholder = "Enter an event description";

const expectedRequiredFieldMsg = "This field is required";

test("renders form", () => {
  render(<NewEventForm />);
  const title = screen.getByText(expectedTitle);
  const name = screen.getByText(expectedNameInputTitle);
  const namePlaceholder = screen.getByPlaceholderText(
    expectedNameInputPlaceholder
  );
  const desc = screen.getByText(expectedDescInputTitle);
  const descPlaceholder = screen.getByPlaceholderText(
    expectedDescInputPlaceholder
  );
  const startDate = screen.getByText(expectedStartDateInputTitle);
  const endDate = screen.getByText(expectedEndDateInputTitle);
  const resetButton = screen.getByText(expectedResetButton);
  const submitButton = screen.getByText(expectedSubmitButton);

  expect(title).toBeInTheDocument();
  expect(name).toBeInTheDocument();
  expect(namePlaceholder).toBeInTheDocument();
  expect(desc).toBeInTheDocument();
  expect(descPlaceholder).toBeInTheDocument();
  expect(startDate).toBeInTheDocument();
  expect(endDate).toBeInTheDocument();
  expect(resetButton).toBeInTheDocument();
  expect(submitButton).toBeInTheDocument();
});

describe("Input", () => {
  test("displays an error message when the description field is touched only", async () => {
    render(<NewEventForm />);
    const descPlaceholder = screen.getByPlaceholderText(
      expectedDescInputPlaceholder
    );
    fireEvent.blur(descPlaceholder);

    expect(await screen.findByText(expectedRequiredFieldMsg)).not.toBeNull();
  });

  test("displays an error message when the name field is touched only", async () => {
    render(<NewEventForm />);
    const namePlaceholder = screen.getByPlaceholderText(
      expectedNameInputPlaceholder
    );
    fireEvent.blur(namePlaceholder);

    expect(await screen.findByText(expectedRequiredFieldMsg)).not.toBeNull();
  });
});
