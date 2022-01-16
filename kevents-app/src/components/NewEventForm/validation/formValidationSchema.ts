import * as Yup from "yup";

const REQUIRED_FIELD = "This field is required";
const NAME_ERROR_MSG = "Must be 32 characters or less";

export const formValidationSchema = Yup.object().shape({
  name: Yup.string()
    .min(1, NAME_ERROR_MSG)
    .max(32, NAME_ERROR_MSG)
    .required(REQUIRED_FIELD)
    .trim(),
  description: Yup.string().required(REQUIRED_FIELD).trim(),
  startDate: Yup.string().required(REQUIRED_FIELD).trim(),
  endDate: Yup.string().required(REQUIRED_FIELD).trim(),
});
