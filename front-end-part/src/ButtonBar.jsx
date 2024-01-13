import React, { useState, useEffect } from "react";
import "./styles/ButtonBar.css";
import AddForm from "./Forms/AddForm";
import EditForm from "./Forms/EditForm";
import DeleteForm from "./Forms/DeleteForm";
import AdvanceSearch from "./Forms/AdvanceSearch";
import AnalyticView from "./Analytics/AnalyticView";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import TextField from "@mui/material/TextField";
import Grid from "@material-ui/core/Grid";
import RefreshIcon from "@material-ui/icons/Refresh";
import axios from "axios";

export default function ButtonBar(props) {
  const [add, setAdd] = useState(false);
  const [edit, setEdit] = useState(false);
  const [dlt, setDlt] = useState(false);
  const [advForm, setAdvForm] = useState(false);
  const [analytics, setAnalytics] = useState(false);
  const [predictDatas, setPredictData] = useState(false);

  const [predict, setPredict] = useState({
    sl_no: "",
  });


  useEffect(() => {
    setPredict({
      ...predict,
      sl_no: props.selectionModel,
    });
  }, [props.selectionModel]); // eslint-disable-line react-hooks/exhaustive-deps



  //Prediction Model

  const handlePredict = async () => {
    axios
      .post("http://localhost:8080/HRC/predict", predict)
      .then((res) => setPredictData(res.data));

    console.log(predictDatas);
    
    for (let i = 0; i < predictDatas.length; i++) {
      const predictData = predictDatas[i];
      const doc_id = parseInt(predictData.doc_id);
      
      await axios.post("http://127.0.0.1:5000/get_prediction", {
        data: [doc_id],
      }).then((res) =>{
        if(res.data.length >0){
          axios.post("http://localhost:8080/HRC/agingBucket", res.data[0])
        }
        else{
          axios.post("http://127.0.0.1:5000/", predictData).then((res)=>{axios
          .post("http://localhost:8080/HRC/agingBucket", res.data[0])})
        }
      })
      
    }  
  };




  const [search, setSearch] = useState({
    cust_number: "",
  });

  const handleChange = (e) => {
    setSearch({
      ...search,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async () => {
    props.setSearchHandler(search);
  };

  const AddFormOpen = () => {
    setAdd(true);
  };

  const AddFormClose = () => {
    setAdd(false);
  };

  const EditFormOpen = () => {
    setEdit(true);
  };

  const EditFormClose = () => {
    setEdit(false);
  };

  const DeleteFormOpen = () => {
    setDlt(true);
  };

  const DeleteFormClose = () => {
    setDlt(false);
  };

  const AdvSearchFormOpen = () => {
    setAdvForm(true);
  };

  const AdvSearchFormClose = () => {
    setAdvForm(false);
  };

  const AnalyticsFormOpen = () => {
    setAnalytics(true);
  };

  const AnalyticsFormClose = () => {
    setAnalytics(false);
  };

  return (
    <>
      <Grid container spacing={1} className="bar">
        <Grid item xs={4} fullWidth>
          <ButtonGroup className="bg">
            <Button className="predict button" onClick={handlePredict}>
              PREDICT
            </Button>
            <Button className="button" onClick={AnalyticsFormOpen}>
              ANALYTICS VIEW
            </Button>
            <Button className="button" onClick={AdvSearchFormOpen}>
              ADVANCE SEARCH
            </Button>
          </ButtonGroup>
        </Grid>

        <Grid item xs={false} fullWidth>
          <ButtonGroup>
            <Button
              className="button"
              onClick={() => props.setRefreshDataHandler(!props.refreshData)}
            >
              <RefreshIcon></RefreshIcon>
            </Button>
          </ButtonGroup>
        </Grid>

        <Grid item xs={2} fullWidth>
          <TextField
            label="SEARCH CUSTOMER ID"
            className="text button"
            variant="filled"
            value={search.cust_number}
            onChange={handleChange}
            name="cust_number"
            onKeyPress={(e) => {
              if (e.key === "Enter") {
                handleSubmit();
              }
            }}
          />
        </Grid>

        <Grid item xs={5} fullWidth>
          <ButtonGroup className="bg">
            <Button className="button" onClick={AddFormOpen}>
              ADD
            </Button>
            <Button
              disabled={!(props.selectionModel.length === 1)}
              className="button"
              onClick={EditFormOpen}
            >
              EDIT
            </Button>
            <Button
              disabled={!props.selectionModel.length}
              className="button"
              onClick={DeleteFormOpen}
            >
              DELETE
            </Button>
          </ButtonGroup>
        </Grid>
      </Grid>

      <div>
        <AddForm open={add} handleClose={AddFormClose} />
      </div>
      <div>
        <EditForm
          open={edit}
          handleClose={EditFormClose}
          selectionModel={props.selectionModel}
        />
      </div>
      <div>
        <DeleteForm
          open={dlt}
          handleClose={DeleteFormClose}
          selectionModel={props.selectionModel}
        />
      </div>
      <div>
        <AdvanceSearch
          open={advForm}
          handleClose={AdvSearchFormClose}
          advData={props.advData}
          setAdvDataHandler={props.setAdvDataHandler}
          page={props.page}
          setPage={props.setPage}
          pageSize={props.pageSize}
          setPageSize={props.setPageSize}
          setAdvSearchHandler={props.setAdvSearchHandler}
          refreshData={props.refreshData}
        />
      </div>
      <div>
        <AnalyticView handleClose={AnalyticsFormClose} open={analytics} />
      </div>
    </>
  );
}
