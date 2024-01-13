import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@material-ui/core/Grid";
import axios from "axios";

export default function DeleteForm(props) {
  const [del, setDel] = useState({
    sl_no: "",
  });

  useEffect(() => {
    setDel({
      ...del,
      sl_no: props.selectionModel,
    });
  }, [props.selectionModel]); // eslint-disable-line react-hooks/exhaustive-deps

  const handleSubmit = () => {
    axios.post("http://localhost:8080/HRC/delete", del);
  };
  return (
    <div>
      <Dialog open={props.open} onClose={props.handleClose}>
        <DialogTitle className="formTitle">Delete Records ?</DialogTitle>

        <DialogContent>
          <Grid container spacing={1}>
            <Grid item xs={12}>
              <DialogContentText className="deleteWarning">
                Are you sure you want to delete these record[s] ?
              </DialogContentText>
            </Grid>
          </Grid>
        </DialogContent>

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
                Delete
              </Button>
            </Grid>
          </Grid>
        </DialogActions>
      </Dialog>
    </div>
  );
}
