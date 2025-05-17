import React, { useState } from 'react';
import { createItem } from '../services/api';

const ItemForm = () => {
  const [name, setName] = useState('');
  const [quantity, setQuantity] = useState(0);
  const [price, setPrice] = useState(0.0);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const newItem = { name, quantity, price };
      await createItem(newItem);
      alert('Item created successfully!');
      setName('');
      setQuantity(0);
      setPrice(0.0);
    } catch (error) {
      console.error('Error creating item:', error);
      alert('Failed to create item.');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="name">Name:</label>
        <input id="name" type="text" value={name} onChange={(e) => setName(e.target.value)} required />
      </div>
      <div>
        <label htmlFor="quantity">Quantity:</label>
        <input id="quantity" type="number" value={quantity} onChange={(e) => setQuantity(Number(e.target.value))} required />
      </div>
      <div>
        <label htmlFor="price">Price:</label>
        <input id="price" type="number" step="0.01" value={price} onChange={(e) => setPrice(Number(e.target.value))} required />
      </div>
      <button type="submit">Create Item</button>
    </form>
  );
};

export default ItemForm;