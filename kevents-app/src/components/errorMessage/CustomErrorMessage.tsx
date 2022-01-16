import Title from "antd/lib/typography/Title";
import { memo } from "react";
import "./customErrorMessage.css";

interface ErrorMessageProps {
  message: string;
}

const ErrorMessage: React.FC<ErrorMessageProps> = ({ message }) => {
  return (
    <Title level={5} id="errorMessage" className="message" type="danger">
      {message}
    </Title>
  );
};

export default memo(ErrorMessage);
