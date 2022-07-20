package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerTM;

import java.util.Optional;

public class TableFormController {
    public TableView<CustomerTM> tblCustomerDetail;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSaveCustomer;
    public Button btnNewCustomer;
    public Button btnDeleteCustomer;

    public void initialize(){

        btnDeleteCustomer.setDisable(true);

        ObservableList<CustomerTM> olCustomer = tblCustomerDetail.getItems();
        olCustomer.add(new CustomerTM("C001","Sahan","Panadura"));
        olCustomer.add(new CustomerTM("C002","Nadun","Ratnapura"));
        olCustomer.add(new CustomerTM("C003","Saman","Kegalle"));

        tblCustomerDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomerDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomerDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Address"));

        tblCustomerDetail.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observableValue, CustomerTM previousCustomer, CustomerTM currentCustomer) {

                if (currentCustomer==null) {
                    txtId.clear();
                    txtName.clear();
                    txtAddress.clear();
                    btnDeleteCustomer.setDisable(true);
                    txtId.setEditable(true);
                    btnSaveCustomer.setText("Save Customer");
                    return;
                }

                btnDeleteCustomer.setDisable(false);
                btnSaveCustomer.setText("Update Customer");
                txtId.setText(currentCustomer.getId());
                txtName.setText(currentCustomer.getName());
                txtAddress.setText(currentCustomer.getAddress());
                txtId.setEditable(false);
            }
        });

    }

    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
        ObservableList<CustomerTM> olCustomer = tblCustomerDetail.getItems();

        if (txtId.getText().isBlank()){
            new Alert(Alert.AlertType.ERROR, "Customer ID can't be empty").showAndWait();
            txtId.requestFocus();
            return;
        } else if (txtAddress.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Customer Address can't be empty").showAndWait();
            txtId.requestFocus();
            return;
        }else if (txtName.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Customer Name can't be empty").showAndWait();
            txtName.requestFocus();
            return;
        }

                /*if (tblCustomerDetail.getSelectionModel().getSelectedItem()!=null){
                    int selectedCustomer = tblCustomerDetail.getSelectionModel().getSelectedIndex();
                    CustomerTM updateCustomer = new CustomerTM(txtId.getText(), txtName.getText(), txtAddress.getText());
                    olCustomer.set(selectedCustomer,updateCustomer);
                    return;
                }*/

        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")){
            for (CustomerTM customerTM : olCustomer) {
                if (customerTM.getId().equals(txtId.getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Customer id Not allowed").showAndWait();
                    txtId.requestFocus();
                    return;
                }
            }

            olCustomer.add(new CustomerTM(txtId.getText(),txtName.getText(),txtAddress.getText()));

            txtId.clear();
            txtName.clear();
            txtAddress.clear();
            txtId.requestFocus();
        }
        else {
            CustomerTM selectedCustomer = tblCustomerDetail.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(txtName.getText());
            selectedCustomer.setAddress(txtAddress.getText());
            tblCustomerDetail.refresh();
        }

    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        txtId.requestFocus();
        tblCustomerDetail.getSelectionModel().clearSelection();
    }

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {

        ObservableList<CustomerTM> olCustomer = tblCustomerDetail.getItems();
        CustomerTM selectedCustomer = tblCustomerDetail.getSelectionModel().getSelectedItem();

        Optional<ButtonType> selectedOption = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this customer?", ButtonType.YES,ButtonType.NO).showAndWait();
        if (selectedOption.get()==ButtonType.YES) olCustomer.remove(selectedCustomer);

    }
}
