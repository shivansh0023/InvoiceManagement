import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@material-ui/core/Grid";
import axios from "axios";

export default function EditForm(props) {
  const [edit, setEdit] = useState({
    sl_no: "",
    invoice_currency: "",
    cust_payment_terms: "",
  });

  const handleChange = (e) => {
    setEdit({
      ...edit,
      [e.target.name]: e.target.value,
    });
  };

  useEffect(() => {
    setEdit({
      ...edit,
      sl_no: props.selectionModel[0],
    });
  }, [props.selectionModel]);// eslint-disable-line react-hooks/exhaustive-deps

  
  const handleSubmit = () => {
    axios.post("http://localhost:8080/HRC/edit", edit);
  
  };

  return (
    <div>
      <Dialog open={props.open} onClose={props.handleClose}>
        <DialogTitle className="formTitle">Edit</DialogTitle>

        <DialogContent>
          <Grid container spacing={1}>
            <Grid item xs={5}>
              <TextField
                label="Invoice Currency"
                variant="standard"
                className="input"
                fullWidth
                value={edit.invoice_currency}
                onChange={handleChange}
                name="invoice_currency"
              />
            </Grid>
            <Grid item xs={5}>
              <TextField
                label="Customer Payment Terms"
                variant="standard"
                className="input"
                fullWidth
                value={edit.cust_payment_terms}
                onChange={handleChange}
                name="cust_payment_terms"
              />
            </Grid>
          </Grid>
        </DialogContent>

        <DialogActions>
          <Grid container spacing={1}>
            <Grid item xs={6}>
              <Button
                className="formButton"
                onClick={() => {
                  props.handleClose();
                  handleSubmit();
                }}
              >
                Edit
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button className="formButton" onClick={props.handleClose}>
                Cancel
              </Button>
            </Grid>
          </Grid>
        </DialogActions>
      </Dialog>
    </div>
  );
}
