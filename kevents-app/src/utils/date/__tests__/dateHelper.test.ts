import React from "react";
import { toLocalDate } from "../dateHelper";

const expectedDate = "Saturday 2022-01-15 21:17";

test("convert utc date to locale", () => {
  const date = toLocalDate("2022-01-16T02:17:19.464Z");
  expect(date).toBe(expectedDate);
});
