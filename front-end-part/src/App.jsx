import React, { useState } from "react";
import Header from "./Header";
import Footer from "./Footer";
import ButtonBar from "./ButtonBar";
import DataLoadingGrid from "./DataLoadingGrid";
import "./styles/App.css";

function App() {
  const [advSearch, setAdvSearch] = useState();
  const [search, setSearch] = useState();
  const [selectionModel, setSelectionModel] = useState([]);
  const [refreshData, setRefreshData] = useState(false);

  const setSearchHandler = (setNewSearch) => {
    setSearch(setNewSearch);
  };

  const setAdvSearchHandler = (setNewAdvSearch) => {
    setAdvSearch(setNewAdvSearch);
  };


  const setUpdate = (update) => {
    setSelectionModel(update);
  };
  const setRefreshDataHandler = (refresh) => {
    setRefreshData(refresh);
    setAdvSearch();
    setSearch();
  };
  return (
    <>
      <div>
        <Header />
      </div>
      <div>
        <ButtonBar
          selectionModel={selectionModel}
          refreshData={refreshData}
          setRefreshDataHandler={setRefreshDataHandler}
          setAdvSearchHandler={setAdvSearchHandler}
          setSearchHandler={setSearchHandler}
        />
      </div>
      <div className="dataGrid">
        <DataLoadingGrid
          selectionModel={selectionModel}
          setUpdate={setUpdate}
          refreshData={refreshData}
          advSearch={advSearch}
          search={search}
        />
      </div>
      <div>
        <Footer />
      </div>
    </>
  );
}

export default App;
