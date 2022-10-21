import axios from "axios";
import Config from "../config.json";

const BASE_URL = Config["server-url"] + "/addressees";

export function getAllAddresses(callback) {
  axios
    .get(BASE_URL + "/getAll")
    .then((response) => {
      callback.onSuccess(response.data);
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}

export function saveAddressee(addressee, callback) {
  axios
    .post(BASE_URL + "/save", addressee)
    .then((response) => {
      callback.onSuccess(response.data);
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}

export function deleteAddressee(id, callback) {
  axios
    .delete(BASE_URL + "/" + id)
    .then(() => {
      callback.onSuccess();
    })
    .catch((error) => {
      callback.onErorr(error);
    });
}
