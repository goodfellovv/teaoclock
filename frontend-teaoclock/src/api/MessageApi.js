import axios from "axios";
import Config from "../config.json";

const BASE_URL = Config["server-url"] + "/messages";

export function getAllMessages(callback) {
  axios
    .get(BASE_URL + "/getAll")
    .then((response) => {
      callback.onSuccess(response.data);
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}

export function saveMessage(message, callback) {
  axios
    .post(BASE_URL + "/save", message)
    .then((response) => {
      callback.onSuccess(response.data);
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}

export function deleteMessage(id, callback) {
  axios
    .delete(BASE_URL + "/" + id)
    .then(() => {
      callback.onSuccess();
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}
