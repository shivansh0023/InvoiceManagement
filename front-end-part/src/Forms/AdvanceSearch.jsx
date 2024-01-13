import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@material-ui/core/Grid";

export default function AdvanceSearch(props) {
  const [advSearch, setadvSearch] = useState({
    cust_number: "",
    buisness_year: "",
    doc_id: "",
    invoice_id: "",
  });

  const handleChange = (e) => {
    setadvSearch({
      ...advSearch,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async () => {
    props.setAdvSearchHandler(advSearch);
  };

  useEffect(() => {
    setadvSearch({
      cust_number: "",
      buisness_year: "",
      doc_id: "",
      invoice_id: "",
    });
  }, [props.refreshData]); // eslint-disable-line react-hooks/exhaustive-deps

  useEffect(() => {
    handleSubmit();
  }, [props.page, props.pageSize]); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <div>
      <Dialog open={props.open} onClose={props.handleClose} maxWidth={"xs"}>
        <DialogTitle className="formTitle">Advance Search</DialogTitle>

        <DialogContent>
          <Grid container spacing={1}>
            <Grid item xs={6}>
              <TextField
                label="Document ID"
                variant="standard"
                className="input"
                fullWidth
                value={advSearch.doc_id}
                onChange={handleChange}
                name="doc_id"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                label="Invoice Id"
                variant="standard"
                className="input"
                fullWidth
                value={advSearch.invoice_id}
                onChange={handleChange}
                name="invoice_id"
              />
            </Grid>

            <Grid item xs={6}>
              <TextField
                label="Customer Number"
                variant="standard"
                className="input"
                fullWidth
                value={advSearch.cust_number}
                onChange={handleChange}
                name="cust_number"
              />
            </Grid>

            <Grid item xs={6}>
              <TextField
                label="Business Year"
                variant="standard"
                className="input"
                fullWidth
                value={advSearch.buisness_year}
                onChange={handleChange}
                name="buisness_year"
              />
            </Grid>
          </Grid>

          <DialogActions>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <Button className="formButton" onClick={props.handleClose}>
                  Cancel
                </Button>
              </Grid>
              <Grid item xs={6}>
                <Button
                  className="formButton"
                  onClick={() => {
                    props.handleClose();
                    handleSubmit();
                  }}
                >
                  Search
                </Button>
              </Grid>
            </Grid>
          </DialogActions>
        </DialogContent>
      </Dialog>
    </div>
  );
}
