import React from 'react';
import { StoreProvider } from "./store";
import ListView from "./components/list/ListView";
import FormView from "./components/list/FormView";

function App() {
  return <StoreProvider>
    <div className="title">
      <h3>Dashboard</h3>
    </div>
    <div className="container">
      <div className="content">
        <FormView />
        <ListView />
      </div>
    </div>
  </StoreProvider>
}

export default App;