import { Button, DatePicker } from "antd";
import Title from "antd/lib/typography/Title";
import axios from "axios";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { isEmpty } from "lodash";
import moment from "moment";
import React, { useState } from "react";
import * as Yup from "yup";
import "./newEventForm.css";

const NewEventForm = () => {
  const requiredFieldErrorMsg = "This field is required";
  const nameErrorMsg = "Must be 32 characters or less";
  const displayedDateFormat = "YYYY-MM-DD HH:mm";

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

  const initialValues = {
    name: "",
    description: "",
    startDate: "",
    endDate: "",
  };

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
    <div>
      <Title level={3}>New event form</Title>
      <Formik
        initialValues={initialValues}
        validationSchema={formValidationSchema}
        onSubmit={(values) => {
          // replaced by antd button, no longer needed but mandatory
        }}
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
                format={displayedDateFormat}
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
                format={displayedDateFormat}
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
                    .post("http://localhost:8080/events", {
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
              <Title
                level={5}
                id="errorMessage"
                className="notification"
                type="danger"
              >
                {dateError && "End date must be more recent than start date!"}
                {containsNetworkError && networkError}
              </Title>
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
    </div>
  );
};

export default NewEventForm;
