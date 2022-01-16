import { Button } from "antd";
import Text from "antd/lib/typography/Text";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { isEmpty } from "lodash";
import React from "react";
import "./newEventForm.css";
import { formValidationSchema } from "./validation/formValidationSchema";

const NewEventForm = () => {
  const initialValues = {
    name: "",
    description: "",
    startDate: "",
    endDate: "",
  };

  return (
    <div>
      <h1>New event form</h1>
      <Formik
        initialValues={initialValues}
        validationSchema={formValidationSchema}
        onSubmit={(values) => {
          alert(JSON.stringify(values, null, 2));
        }}
      >
        {({ setErrors, setTouched, setValues, values, errors }) => (
          <Form>
            <div className="input">
              <Text id="eventNameTitle">Name</Text>
              <Field name="name" placeholder="Enter an event name" />
              <ErrorMessage name="name" />
            </div>

            <div className="input">
              <Text id="eventDescTitle">Description</Text>
              <Field
                name="description"
                placeholder="Enter an event description"
              />
              <ErrorMessage name="description" className="error" />
            </div>

            <div className="input">
              <Text id="eventStartDateTitle">Start Date</Text>
              <Field name="startDate" />
              <ErrorMessage name="startDate" />
            </div>

            <div className="input">
              <Text id="eventEndDateTitle">End Date</Text>
              <Field name="endDate" />
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
                }}
              >
                Reset
              </Button>
              <Button
                id="submit"
                shape="round"
                type="primary"
                disabled={
                  !isEmpty(errors) ||
                  isEmpty(values.description) ||
                  isEmpty(values.name) ||
                  isEmpty(values.startDate) ||
                  isEmpty(values.endDate)
                }
              >
                Submit
              </Button>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default NewEventForm;
