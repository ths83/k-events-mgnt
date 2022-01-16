import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import store from "./components/app/store";
import "./index.css";
import App from "./pages/App/App";

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
