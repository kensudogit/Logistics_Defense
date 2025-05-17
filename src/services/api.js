import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const getItems = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/items`);
    return response.data;
  } catch (error) {
    console.error('Error fetching items:', error);
    throw error;
  }
};

export const createItem = async (item) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/items`, item);
    return response.data;
  } catch (error) {
    console.error('Error creating item:', error);
    throw error;
  }
};
