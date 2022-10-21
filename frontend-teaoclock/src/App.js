import "./App.css";
import "primeicons/primeicons.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.css";
import "primeflex/primeflex.css";

import React from "react";
import { Splitter, SplitterPanel } from "primereact/splitter";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { InputText } from "primereact/inputtext";
import * as addresseeApi from "./api/AddresseeApi";
import * as messageApi from "./api/MessageApi";
import { InputTextarea } from "primereact/inputtextarea";
import { Button } from "primereact/button";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      addresses: [],
      messages: [],
      editingRows: {},
    };
    this.loadAddresses = this.loadAddresses.bind(this);
    this.loadMessages = this.loadMessages.bind(this);
    this.deleteAddressee = this.deleteAddressee.bind(this);
    this.deleteMessage = this.deleteMessage.bind(this);
    this.deleteAddresseeTemplate = this.deleteAddresseeTemplate.bind(this);
    this.deleteMessageTemplate = this.deleteMessageTemplate.bind(this);
    this.onAddresseeRowEditComplete =
      this.onAddresseeRowEditComplete.bind(this);
    this.onMessageRowEditComplete = this.onMessageRowEditComplete.bind(this);
  }
  componentDidMount() {
    this.loadAddresses();
    this.loadMessages();
  }
  loadAddresses() {
    addresseeApi.getAllAddresses({
      onSuccess: (response) => {
        const temp = response.map((element) => {
          return {
            id: element.id,
            email: element.email,
            message: element.message?.id,
          };
        });
        temp.push({});
        this.setState({ addresses: temp });
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  loadMessages() {
    messageApi.getAllMessages({
      onSuccess: (response) => {
        const temp = response.map((element) => {
          return {
            id: element.id,
            subject: element.subject,
            content: element.content,
          };
        });
        temp.push({});
        this.setState({ messages: temp });
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  saveMessage(message) {
    messageApi.saveMessage(message, {
      onSuccess: (response) => {
        this.loadMessages();
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  saveAddressee(addressee) {
    addresseeApi.saveAddressee(addressee, {
      onSuccess: (response) => {
        this.loadAddresses();
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  deleteMessage(id) {
    messageApi.deleteMessage(id, {
      onSuccess: () => {
        this.loadMessages();
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  deleteAddressee(id) {
    addresseeApi.deleteAddressee(id, {
      onSuccess: () => {
        this.loadAddresses();
      },
      onErorr: (error) => {
        console.log(error);
      },
    });
  }

  onAddresseeRowEditComplete(e) {
    if (e.newData.email) {
      const temp = e.newData;
      if (temp.message)
        temp.message = this.state.messages.find(
          (message) => (message.id = temp.message)
        );
      console.log(temp);
      this.saveAddressee(temp);
    }
  }

  onMessageRowEditComplete(e) {
    if (e.newData.content || e.newData.subject) {
      this.saveMessage(e.newData);
    }
  }

  inputTextEditor(options) {
    return (
      <InputText
        type="text"
        value={options.value}
        onChange={(e) => options.editorCallback(e.target.value)}
      />
    );
  }

  deleteAddresseeTemplate(rowData) {
    return (
      <React.Fragment>
        <Button
          disabled={!rowData.email}
          icon="pi pi-trash"
          className="p-button-rounded p-button-warning"
          onClick={() => this.deleteAddressee(rowData.id)}
        />
      </React.Fragment>
    );
  }

  deleteMessageTemplate(rowData) {
    return (
      <React.Fragment>
        <Button
          disabled={!rowData.content && !rowData.subject}
          icon="pi pi-trash"
          className="p-button-rounded p-button-warning"
          onClick={() => this.deleteMessage(rowData.id)}
        />
      </React.Fragment>
    );
  }

  textAreaEditor(options) {
    return (
      <InputTextarea
        type="text"
        value={options.value}
        onChange={(e) => options.editorCallback(e.target.value)}
      />
    );
  }

  render() {
    return (
      <div style={{ height: "100%" }}>
        <div className="card" style={{ height: "100%" }}>
          <Splitter style={{ height: "100%" }} className="mb-5">
            <SplitterPanel className="flex justify-content-center">
              <DataTable
                size="small"
                style={{ width: "100%" }}
                value={this.state.addresses}
                editMode="row"
                dataKey="id"
                onRowEditComplete={this.onAddresseeRowEditComplete}
                responsiveLayout="scroll"
              >
                <Column
                  body={this.deleteAddresseeTemplate}
                  exportable={false}
                  style={{ width: "5%" }}
                />
                <Column
                  rowEditor
                  headerStyle={{ width: "5%" }}
                  bodyStyle={{ textAlign: "center" }}
                />
                <Column
                  sortable
                  field="id"
                  header="Addressee Id"
                  style={{ width: "20%" }}
                />
                <Column
                  sortable
                  field="email"
                  header="Email"
                  editor={(options) => this.inputTextEditor(options)}
                  style={{ width: "45%" }}
                />
                <Column
                  sortable
                  field="message"
                  header="Message Id"
                  editor={(options) => this.inputTextEditor(options)}
                  style={{ width: "25%" }}
                />
              </DataTable>
            </SplitterPanel>
            <SplitterPanel className="flex justify-content-center">
              <DataTable
                size="small"
                style={{ width: "100%" }}
                value={this.state.messages}
                editMode="row"
                dataKey="id"
                onRowEditComplete={this.onMessageRowEditComplete}
                responsiveLayout="scroll"
              >
                <Column
                  sortable
                  field="id"
                  header="Message Id"
                  editor={(options) => this.inputTextEditor(options)}
                  style={{ width: "15%" }}
                />
                <Column
                  sortable
                  field="subject"
                  header="Subject"
                  editor={(options) => this.inputTextEditor(options)}
                  style={{ width: "15%" }}
                />
                <Column
                  sortable
                  field="content"
                  header="Content"
                  editor={(options) => this.textAreaEditor(options)}
                  style={{ width: "50%" }}
                />
                <Column
                  rowEditor
                  headerStyle={{ width: "5%" }}
                  bodyStyle={{ textAlign: "center" }}
                />
                <Column
                  body={this.deleteMessageTemplate}
                  exportable={false}
                  style={{ width: "5%" }}
                />
              </DataTable>
            </SplitterPanel>
          </Splitter>
        </div>
      </div>
    );
  }
}
export default App;
