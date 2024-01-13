import React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { Bar, Pie } from "react-chartjs-2";
// eslint-disable-next-line no-unused-vars
import { Chart as ChartJS } from "chart.js/auto";
import Grid from "@material-ui/core/Grid";
import "./AnalyticsChart.css";
import CloseIcon from "@mui/icons-material/Close";
import FormLabel from "@mui/material/FormLabel";
export default function AnalyticsChart(props) {
  console.log(props.chartData[0]);
  const bar = {
    labels: props.chartData[0],
    datasets: [
      {
        label: "No. of Customers",
        data: props.chartData[1],
        backgroundColor: "#ffb3b3",
      },
      {
        label: "Total Open Amounts",
        data: props.chartData[2],
        backgroundColor: "#80bfff",
      },
    ],
  };

  const options = {
    scales: {
      y: {
        type: "logarithmic",
      },
    },
  };

  const pie = {
    labels: props.chartData[4],
    datasets: [
      {
        data: props.chartData[3],
        backgroundColor: ["#ffb3b3","#80bfff"]
      },
    ],
  };

  return (
    <div className="chart">
      <Dialog
        open={props.openChart}
        onClose={props.handleClose}
        sx={{
          "& .MuiDialog-container": {
            "& .MuiPaper-root": {
              width: "100%",
              maxWidth: "65%",
            },
          },
        }}
      >
        <DialogActions className="chart">
          <Button onClick={props.handleClose}>
            <CloseIcon className="closeButton"></CloseIcon>
          </Button>
        </DialogActions>

        <DialogContent className="chart">
          <Grid className="chartTitle">
            <FormLabel className="chartTitle">Chart.js Bar Chart</FormLabel>
          </Grid>
          <Grid><Bar data={bar} options={options} className="barChart" /></Grid>
          <Grid className="chartTitle">
            <FormLabel className="chartTitle">Chart.js Bar Chart</FormLabel>
          </Grid>
          <Grid className="pieChart" ><Pie data={pie} /></Grid>
        </DialogContent>
      </Dialog>
    </div>
  );
}
