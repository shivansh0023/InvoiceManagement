import React, { useState } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@material-ui/core/Grid";
import axios from "axios";

export default function FormDialog(props) {
  const [newInfo, setNewInfo] = useState({
    business_code: "",
    cust_number: "",
    clear_date: "",
    buisness_year: "",
    doc_id: "",
    posting_date: "",
    document_create_date: "",
    due_in_date: "",
    invoice_currency: "",
    document_type: "",
    posting_id: "",
    total_open_amount: "",
    baseline_ceate_date: "",
    cust_payment_terms: "",
    invoice_id: "",
  });

  const handleChange = (e) => {
    setNewInfo({
      ...newInfo,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = () => {
    axios.post("http://localhost:8080/HRC/insert", newInfo);
  };

  const values = {
    someDate: "2022-06-26",
  };

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        maxWidth={"xl"}
        fullWidth
      >
        <DialogTitle className="formTitle">Add</DialogTitle>

        <DialogContent>
          <Grid container spacing={1}>
            <Grid item xs={3}>
              <TextField
                label="Business Code"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.business_code}
                onChange={handleChange}
                name="business_code"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Customer Number"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.cust_number}
                onChange={handleChange}
                name="cust_number"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Clear Date"
                variant="standard"
                className="input"
                fullWidth
                type="date"
                InputLabelProps={{ shrink: true }}
                defaultValue={values.someDate}
                value={newInfo.clear_date}
                onChange={handleChange}
                name="clear_date"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Business Year"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.buisness_year}
                onChange={handleChange}
                name="buisness_year"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Document Id"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.doc_id}
                onChange={handleChange}
                name="doc_id"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Posting Date"
                variant="standard"
                className="input"
                type="date"
                fullWidth
                InputLabelProps={{ shrink: true }}
                defaultValue={values.someDate}
                value={newInfo.posting_date}
                onChange={handleChange}
                name="posting_date"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Document Create Date"
                variant="standard"
                className="input"
                type="date"
                fullWidth
                InputLabelProps={{ shrink: true }}
                defaultValue={values.someDate}
                value={newInfo.document_create_date}
                onChange={handleChange}
                name="document_create_date"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Due Date"
                variant="standard"
                className="input"
                type="date"
                fullWidth
                InputLabelProps={{ shrink: true }}
                defaultValue={values.someDate}
                value={newInfo.due_in_date}
                onChange={handleChange}
                name="due_in_date"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Invoice Currency"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.invoice_currency}
                onChange={handleChange}
                name="invoice_currency"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Document Type"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.document_type}
                onChange={handleChange}
                name="document_type"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Posting Id"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.posting_id}
                onChange={handleChange}
                name="posting_id"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Total open amount"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.total_open_amount}
                onChange={handleChange}
                name="total_open_amount"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Baseline Create Date"
                variant="standard"
                className="input"
                type="date"
                fullWidth
                InputLabelProps={{ shrink: true }}
                defaultValue={values.someDate}
                value={newInfo.baseline_ceate_date}
                onChange={handleChange}
                name="baseline_ceate_date"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Customer Payments Terms"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.cust_payment_terms}
                onChange={handleChange}
                name="cust_payment_terms"
              />
            </Grid>
            <Grid item xs={3}>
              <TextField
                label="Invoice Id"
                variant="standard"
                className="input"
                fullWidth
                value={newInfo.invoice_id}
                onChange={handleChange}
                name="invoice_id"
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
                Add
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
