import { Button, DatePicker } from "antd";
import Title from "antd/lib/typography/Title";
import axios from "axios";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { isEmpty, noop } from "lodash";
import moment from "moment";
import React, { useState } from "react";
import * as Yup from "yup";
import { datePattern } from "../../utils/date/dateHelper";
import CustomErrorMessage from "../errorMessage/CustomErrorMessage";
import "./newEventForm.css";

const NewEventForm = () => {
  const requiredFieldErrorMsg = "This field is required";
  const nameErrorMsg = "Must be 32 characters or less";

  const initialValues = {
    name: "",
    description: "",
    startDate: "",
    endDate: "",
  };

  const formValidationSchema = Yup.object().shape({
    name: Yup.string()
      .min(1, nameErrorMsg)
      .max(32, nameErrorMsg)
      .required(requiredFieldErrorMsg)
      .trim(),
    description: Yup.string().required(requiredFieldErrorMsg).trim(),
    startDate: Yup.string().required(requiredFieldErrorMsg).trim(),
    endDate: Yup.string().required(requiredFieldErrorMsg).trim(),
  });

  const compareDates = (start: string | null, end: string | null) => {
    const isValid =
      isEmpty(start) || isEmpty(end) ? false : moment(start) < moment(end);
    setDateError(isValid);
  };

  const [dateError, setDateError] = useState(false);
  const [networkError, setNetworkError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);

  const containsNetworkError = !isEmpty(networkError);
  const hasErrors = dateError || containsNetworkError;

  return (
    <>
      <Title level={3}>New event form</Title>
      <Formik
        initialValues={initialValues}
        validationSchema={formValidationSchema}
        onSubmit={noop}
      >
        {({
          setErrors,
          setTouched,
          setValues,
          values,
          errors,
          setFieldValue,
        }) => (
          <Form>
            <div className="input">
              <Title level={5} id="eventNameTitle">
                Name
              </Title>
              <Field name="name" placeholder="Enter an event name" />
              <ErrorMessage name="name" />
            </div>

            <div className="input">
              <Title level={5} id="eventDescTitle">
                Description
              </Title>
              <Field
                name="description"
                placeholder="Enter an event description"
              />
              <ErrorMessage name="description" />
            </div>

            <div className="input">
              <Title level={5} id="eventStartDateTitle">
                Start Date
              </Title>
              <DatePicker
                id="startDate"
                showTime
                format={datePattern}
                onChange={(value) => {
                  setFieldValue("startDate", value?.toISOString());
                  compareDates(values.startDate, values.endDate);
                }}
                onOk={(value) => {
                  setFieldValue("startDate", value.toISOString());
                  compareDates(values.startDate, values.endDate);
                }}
              />
              <ErrorMessage name="startDate" />
            </div>

            <div className="input">
              <Title level={5} id="eventEndDateTitle">
                End Date
              </Title>
              <DatePicker
                id="endDate"
                showTime
                format={datePattern}
                onChange={(value) => {
                  setFieldValue("endDate", value?.toISOString());
                  compareDates(values.startDate, values.endDate);
                }}
                onOk={(value) => {
                  setFieldValue("endDate", value.toISOString());
                  compareDates(values.startDate, values.endDate);
                }}
              />
              <ErrorMessage name="endDate" />
            </div>

            <div className="buttons">
              <Button
                id="reset"
                shape="round"
                type="primary"
                danger
                onClick={() => {
                  setValues(initialValues);
                  setErrors({});
                  setTouched({});
                  setDateError(false);
                  setNetworkError("");
                  setSuccess(false);
                }}
              >
                Reset
              </Button>
              <Button
                id="submit"
                shape="round"
                type="primary"
                loading={submitting}
                disabled={
                  !isEmpty(errors) ||
                  isEmpty(values.description) ||
                  isEmpty(values.name) ||
                  isEmpty(values.startDate) ||
                  isEmpty(values.endDate) ||
                  dateError
                }
                onClick={() => {
                  setSubmitting(true);
                  axios
                    .post(`${process.env.REACT_APP_API_URL}/events`, {
                      name: values.name,
                      description: values.description,
                      startDate: values.startDate,
                      endDate: values.endDate,
                    })
                    .then(() => {
                      setValues(initialValues);
                      setErrors({});
                      setTouched({});
                      setSuccess(true);
                    })
                    .catch((error) => {
                      setNetworkError(error.response.data.message);
                    })
                    .finally(() => setSubmitting(false));
                }}
              >
                Submit
              </Button>
            </div>
            {hasErrors && (
              <CustomErrorMessage
                message={
                  dateError
                    ? "End date must be more recent than start date!"
                    : networkError
                }
              />
            )}
            {success && (
              <Title
                level={5}
                id="successMessage"
                className="notification"
                type="success"
              >
                Successfully created new event!
              </Title>
            )}
          </Form>
        )}
      </Formik>
    </>
  );
};

export default NewEventForm;
