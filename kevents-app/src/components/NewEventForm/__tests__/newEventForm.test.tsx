import { fireEvent, render, screen } from "@testing-library/react";
import React from "react";
import NewEventForm from "../NewEventForm";
import renderer from "react-test-renderer";

const expectedTitle = "New event form";
const expectedNameInputTitle = "Name";
const expectedDescInputTitle = "Description";
const expectedStartDateInputTitle = "Start Date";
const expectedEndDateInputTitle = "End Date";
const expectedResetButton = "Reset";
const expectedSubmitButton = "Submit";

const expectedNameInputPlaceholder = "Enter an event name";
const expectedDescInputPlaceholder = "Enter an event description";
const expectedDateInputPlaceholder = "Select date";

const expectedRequiredFieldMsg = "This field is required";

test("renders component", () => {
  const item = renderer.create(<NewEventForm />).toJSON();
  expect(item).toMatchSnapshot();
});

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

  const datePlaceholder = screen.getAllByPlaceholderText(
    expectedDateInputPlaceholder
  );

  expect(title).toBeInTheDocument();
  expect(name).toBeInTheDocument();
  expect(namePlaceholder).toBeInTheDocument();
  expect(desc).toBeInTheDocument();
  expect(descPlaceholder).toBeInTheDocument();
  expect(startDate).toBeInTheDocument();
  expect(endDate).toBeInTheDocument();
  expect(resetButton).toBeInTheDocument();
  expect(submitButton).toBeInTheDocument();
  expect(datePlaceholder.length).toBe(2);
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
