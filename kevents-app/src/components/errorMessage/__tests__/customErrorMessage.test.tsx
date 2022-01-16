import React from "react";
import renderer from "react-test-renderer";
import ErrorMessage from "../CustomErrorMessage";

test("renders component", () => {
  const item = renderer.create(<ErrorMessage message="" />).toJSON();
  expect(item).toMatchSnapshot();
});
