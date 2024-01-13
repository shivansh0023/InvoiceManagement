import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import axios from "axios";
import "./styles/DataLoadingGrid.css";

export default function DataLoadingGrid(props) {
  const [data, setData] = useState([]);
  const [rowCount, setRowCount] = useState();
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [field, setField] = useState("sl_no");
  const [order, setOrder] = useState("asc")
  

  const getData = async () => {
    if (
      props.advSearch &&
      (props.advSearch.cust_number.length !== 0 ||
        props.advSearch.invoice_id.length !== 0 ||
        props.advSearch.doc_id.length !== 0 ||
        props.advSearch.buisness_year.length !== 0)
    )
      getAdvData();
    else if (props.search&&props.search.cust_number.length !== 0) {
      getSearchData();
    } else {
      axios
        .get(
          `http://localhost:8080/HRC/?page=${page}&pageSize=${pageSize}&sort=${field}&order=${order}`
        )
        .then((res) => {
          setData(res.data[1]);
          setRowCount(res.data[0])
        });
    }
  };

  const getAdvData = async () => {
    await axios
      .post(
        `http://localhost:8080/HRC/advSearch?page=${page}&pageSize=${pageSize}&sort=${field}&order=${order}`,
        props.advSearch
      )
      .then((res) => {
        setData(res.data[1]);
        setRowCount(res.data[0])
      });
  };

  const getSearchData = async () => {
    await axios
      .post(
        `http://localhost:8080/HRC/search?page=${page}&pageSize=${pageSize}&sort=${field}&order=${order}`,
        props.search
      )
      .then((res) => {
        setData(res.data[1]);
        setRowCount(res.data[0])
      });
  };
  useEffect(() => {
    getData();
    
  }, [props.refreshData,page,pageSize,props.advSearch,props.search,field,order]); // eslint-disable-line react-hooks/exhaustive-deps

  const setPageHandler = (newPage) => {
    setPage(newPage);
  };
  const setPageSizeHandler = (newPageSize) => {
    setPageSize(newPageSize);
  };
  const setSortOrder = (column) => {
    setField(column[0].field);
    setOrder(column[0].sort)
  };
  

  const columns = [
    { field: "sl_no", headerName: "Sl no" },
    { field: "business_code", headerName: "Business Code" },
    { field: "cust_number", headerName: "Customer Number" },
    { field: "clear_date", headerName: "Clear Date" },
    { field: "buisness_year", headerName: "Buisness Year" },
    { field: "doc_id", headerName: "Document ID" },
    { field: "posting_date", headerName: "Posting Date" },
    { field: "document_create_date", headerName: "Document Create Date" },
    { field: "due_in_date", headerName: "Due in Date" },
    { field: "invoice_currency", headerName: "Invoice Currency" },
    { field: "document_type", headerName: "Document Type" },
    { field: "posting_id", headerName: "Posting Id" },
    { field: "total_open_amount", headerName: "Total Open Amount" },
    { field: "baseline_create_date", headerName: "Baseline Create Date" },
    { field: "cust_payment_terms", headerName: "Cust Payment Terms" },
    { field: "invoice_id", headerName: "Invoice Id" },
    { field: "aging_bucket", headerName: "Aging Bucket", width: 150 },
  ];

  console.log(data);
  return (
    <div className="datagrid">
      <DataGrid
        getRowId={(row) => row.sl_no}
        disableColumnMenu
        checkboxSelection  
        rowHeight={42.2}
        rowsPerPageOptions={[5, 10, 20, 100]}  
        rows={data}
        columns={columns}
        rowCount={rowCount}
        pageSize={pageSize}
        paginationMode="server"
        sortingMode="server"
        selectionModel={props.selectionModel}
        onSelectionModelChange={(newSelectionModel) => {
          props.setUpdate(newSelectionModel);
        }}
        onPageChange={(pageNumber) => {
          setPageHandler(pageNumber);
        }}
        onSortModelChange={(model)=>{
          
          setSortOrder(model)
        }}
        onPageSizeChange={(pageSize) => {
          setPageSizeHandler(pageSize);
        }}
      />
    </div>
  );
}
