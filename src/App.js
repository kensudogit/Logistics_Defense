import React from 'react';
import './App.css';
import ItemList from './components/ItemList';
import ItemForm from './components/ItemForm';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Logistics Management System</h1>
      </header>
      <main>
        <ItemForm />
        <ItemList />
      </main>
    </div>
  );
}

export default App;
