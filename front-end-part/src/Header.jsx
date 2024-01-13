import React from "react";
import "./styles/Header.css";
import logo1URL from "./images/logo1.png";
import logo2URL from "./images/logo.png";

export default function Header() {
  return (
    <>
      <div className="Header">
        <img src={logo1URL} alt="ABC" className="logo1" />

        <img src={logo2URL} alt="HRC" className="logo2" />
      </div>
      <div className="Title">Invoice List</div>
    </>
  );
}
