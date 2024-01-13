import React, { useState } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from "@material-ui/core/Grid";
import axios from "axios";
import AnalyticsChart from "./AnalyticsChart";
export default function AnalyticView(props) {
  const [analyticsData, setAnalyticsData] = useState({
    clear_date1: "",
    due_in_date1: "",
    invoice_currency: "",
    baseline_ceate_date1: "",
    clear_date2: "",
    due_in_date2: "",
    baseline_ceate_date2: "",
  });
  const [chart, setChart] = useState(false);
  const [chartData, setChartData] = useState([]);

  const chartPageOpen = () => {
    setChart(true);
  };

  const chartPageClose = () => {
    setChart(false);
  };

  const handleChange = (e) => {
    setAnalyticsData({
      ...analyticsData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = () => {
    axios
      .post("http://localhost:8080/HRC/analytics", analyticsData)
      .then((res) => setChartData(res.data));
    chartPageOpen();
  };

  return (
    <>
      <div>
        <Dialog open={props.open} onClose={props.handleClose}>
          <DialogTitle className="formTitle">Analytics</DialogTitle>

          <DialogContent>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <label>Clear Date</label>
                <TextField
                  label="Clear Date"
                  variant="standard"
                  className="input"
                  fullWidth
                  type="date"
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.clear_date1}
                  onChange={handleChange}
                  name="clear_date1"
                />
              </Grid>
              <Grid item xs={6}>
                <label>Due Date</label>
                <TextField
                  label="Due Date"
                  variant="standard"
                  className="input"
                  type="date"
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.due_in_date1}
                  onChange={handleChange}
                  name="due_in_date1"
                />
              </Grid>
            </Grid>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <TextField
                  label="Clear Date"
                  variant="standard"
                  className="input"
                  fullWidth
                  type="date"
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.clear_date2}
                  onChange={handleChange}
                  name="clear_date2"
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  label="Due Date"
                  variant="standard"
                  className="input"
                  type="date"
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.due_in_date2}
                  onChange={handleChange}
                  name="due_in_date2"
                />
              </Grid>
            </Grid>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <label>Baseline Create Date</label>
                <TextField
                  label="Baseline Create Date"
                  variant="standard"
                  className="input"
                  type="date"
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.baseline_ceate_date1}
                  onChange={handleChange}
                  name="baseline_ceate_date1"
                />
              </Grid>
              <Grid item xs={6}>
                <label>Invoice Currency</label>
                <TextField
                  label="Invoice Currency"
                  variant="standard"
                  className="input"
                  fullWidth
                  value={analyticsData.invoice_currency}
                  onChange={handleChange}
                  name="invoice_currency"
                />
              </Grid>
            </Grid>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <TextField
                  label="Baseline Create Date"
                  variant="standard"
                  className="input"
                  type="date"
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  value={analyticsData.baseline_ceate_date2}
                  onChange={handleChange}
                  name="baseline_ceate_date2"
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
                  Submit
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
      <div>
        <AnalyticsChart handleClose={chartPageClose} openChart={chart} chartData={chartData} />
      </div>
    </>
  );
}
