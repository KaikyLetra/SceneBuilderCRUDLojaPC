package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    @FXML private Button btnLimpar;
    @FXML private Button btnDeletar;
    @FXML private Button btnCadastrar;
    @FXML private Button btnEditar;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtGabinete;
    @FXML private TextField txtCPU;
    @FXML private TextField txtGPU;
    @FXML private TextField txtRAM;
    @FXML private TextField txtDChannel;
    @FXML private TextField txtArmaz;
    @FXML private TextField txtBluetooth;

    @FXML private TableView<ComponentesDTO> tblComponentes;

    @FXML private TableColumn<ComponentesDTO, Integer> colId;
    @FXML private TableColumn<ComponentesDTO, String> colNome;
    @FXML private TableColumn<ComponentesDTO, String> colGabinete;
    @FXML private TableColumn<ComponentesDTO, String> colCPU;
    @FXML private TableColumn<ComponentesDTO, String> colGPU;
    @FXML private TableColumn<ComponentesDTO, String> colRAM;
    @FXML private TableColumn<ComponentesDTO, Boolean> colDChannel;
    @FXML private TableColumn<ComponentesDTO, String> colArmaz;
    @FXML private TableColumn<ComponentesDTO, Boolean> colBluetooth;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPc"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGabinete.setCellValueFactory(new PropertyValueFactory<>("gabinete"));
        colCPU.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        colGPU.setCellValueFactory(new PropertyValueFactory<>("gpu"));
        colRAM.setCellValueFactory(new PropertyValueFactory<>("ram"));
        colDChannel.setCellValueFactory(new PropertyValueFactory<>("dualchannel"));
        colArmaz.setCellValueFactory(new PropertyValueFactory<>("armazenamento"));
        colBluetooth.setCellValueFactory(new PropertyValueFactory<>("bluetooth"));

        limparCampos();
        carregarComponente();    }

    @FXML
    private void carregarComponente() {
        ComponentesDAO objComponenteDAO = new ComponentesDAO();
        ArrayList<ComponentesDTO> listaComponentes = objComponenteDAO.selectComponentes();
        tblComponentes.setItems(FXCollections.observableArrayList(listaComponentes));
    }

    @FXML void btnLimparAction() {
        txtId.clear();
        txtNome.clear();
        txtGabinete.clear();
        txtCPU.clear();
        txtGPU.clear();
        txtRAM.clear();
        txtDChannel.clear();
        txtArmaz.clear();
        txtBluetooth.clear();
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        ComponentesDTO objComponenteDTO = new ComponentesDTO();
        objComponenteDTO.setNome(txtNome.getText());
        objComponenteDTO.setGabinete(txtGabinete.getText());
        objComponenteDTO.setCpu(txtCPU.getText());
        objComponenteDTO.setGpu(txtGPU.getText());
        objComponenteDTO.setRam(txtRAM.getText());
        objComponenteDTO.setDualchannel(Boolean.parseBoolean(txtDChannel.getText()));
        objComponenteDTO.setArmazenamento(txtArmaz.getText());
        objComponenteDTO.setBluetooth(Boolean.parseBoolean(txtBluetooth.getText()));

        ComponentesDAO objComponentesDAO = new ComponentesDAO();
        objComponentesDAO.insertComponente(objComponenteDTO);

        limparCampos();
        carregarComponente();
    }

    @FXML
    private void tblComponentesMouseClicked(MouseEvent event) {
        ComponentesDTO componente = tblComponentes.getSelectionModel().getSelectedItem();

        if (componente != null) {
            txtId.setText(String.valueOf(componente.getIdPc()));
            txtNome.setText(componente.getNome());
            txtGabinete.setText(componente.getGabinete());
            txtCPU.setText(componente.getCpu());
            txtGPU.setText(componente.getGpu());
            txtRAM.setText(componente.getRam());
            txtDChannel.setText(String.valueOf(componente.isDualchannel()));
            txtArmaz.setText(componente.getArmazenamento());
            txtBluetooth.setText(String.valueOf(componente.isBluetooth()));
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            ComponentesDTO objComponenteDTO = new ComponentesDTO();
            objComponenteDTO.setIdPc(Integer.parseInt(txtId.getText()));

            ComponentesDAO objComponentesDAO = new ComponentesDAO();
            objComponentesDAO.deleteComponente(objComponenteDTO);

            limparCampos();
            carregarComponente();
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            ComponentesDTO objComponenteDTO = new ComponentesDTO();
            objComponenteDTO.setIdPc(Integer.parseInt(txtId.getText()));
            objComponenteDTO.setNome(txtNome.getText());
            objComponenteDTO.setGabinete(txtGabinete.getText());
            objComponenteDTO.setCpu(txtCPU.getText());
            objComponenteDTO.setGpu(txtGPU.getText());
            objComponenteDTO.setRam(txtRAM.getText());
            objComponenteDTO.setDualchannel(Boolean.parseBoolean(txtDChannel.getText()));
            objComponenteDTO.setArmazenamento(txtArmaz.getText());
            objComponenteDTO.setBluetooth(Boolean.parseBoolean(txtBluetooth.getText()));

            ComponentesDAO objComponentesDAO = new ComponentesDAO();
            objComponentesDAO.updateComponente(objComponenteDTO);

            limparCampos();
            carregarComponente();
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtGabinete.clear();
        txtCPU.clear();
        txtGPU.clear();
        txtRAM.clear();
        txtDChannel.clear();
        txtArmaz.clear();
        txtBluetooth.clear();
    }
    @FXML void carregarCampos() {
        ComponentesDTO objComponenteDTO = tblComponentes.getSelectionModel().getSelectedItem();

        if (objComponenteDTO != null) {
            txtId.setText(String.valueOf(objComponenteDTO.getIdPc()));
            txtNome.setText(objComponenteDTO.getNome());
            txtGabinete.setText(objComponenteDTO.getGabinete());
            txtCPU.setText(objComponenteDTO.getCpu());
            txtGPU.setText(objComponenteDTO.getGpu());
            txtRAM.setText(objComponenteDTO.getRam());
            txtDChannel.setText(String.valueOf(objComponenteDTO.isDualchannel()));
            txtArmaz.setText(objComponenteDTO.getArmazenamento());
            txtBluetooth.setText(String.valueOf(objComponenteDTO.isBluetooth()));
        }
    }
}