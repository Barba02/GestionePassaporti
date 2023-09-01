import React, { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import Board from "./App";
import "..resources/static/styles.css";

const root = createRoot(document.getElementById("root"));
root.render(
  <StrictMode>
    <Board />
  </StrictMode>
);
