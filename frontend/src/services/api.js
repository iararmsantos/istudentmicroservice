import axios from "axios";

const api = axios.create({
  baseUrl: "http://localhost:8081",
});

export default api;
