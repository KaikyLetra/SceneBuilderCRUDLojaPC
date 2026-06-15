package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

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
    @FXML private TextField txtArmaz;

    // UX: Componentes corretos mapeados do FXML
    @FXML private Spinner<Integer> txtRAM;
    @FXML private CheckBox txtDChannel;
    @FXML private CheckBox txtBluetooth;

    // UX: Feedback visual para o usuário
    @FXML private Label lblMensagem;

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
        // Mapeamento das colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("idPc"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGabinete.setCellValueFactory(new PropertyValueFactory<>("gabinete"));
        colCPU.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        colGPU.setCellValueFactory(new PropertyValueFactory<>("gpu"));
        colRAM.setCellValueFactory(new PropertyValueFactory<>("ram"));
        colDChannel.setCellValueFactory(new PropertyValueFactory<>("dualchannel"));
        colArmaz.setCellValueFactory(new PropertyValueFactory<>("armazenamento"));
        colBluetooth.setCellValueFactory(new PropertyValueFactory<>("bluetooth"));

        // UX: Configuração do Spinner de RAM (Mínimo: 4GB, Máximo: 128GB, Padrão: 8GB, Incremento: 4)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, 8, 4);
        txtRAM.setValueFactory(valueFactory);

        limparCampos();
        carregarComponente();
    }

    @FXML
    private void carregarComponente() {
        ComponentesDAO objComponenteDAO = new ComponentesDAO();
        ArrayList<ComponentesDTO> listaComponentes = objComponenteDAO.selectComponentes();
        tblComponentes.setItems(FXCollections.observableArrayList(listaComponentes));
    }

    @FXML
    void btnLimparAction() {
        limparCampos();
        mostrarMensagem("Campos limpos. Pronto para um novo cadastro.", "#00adb5");
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        // UX: Validação de Campos Obrigatórios Básicos
        if (txtNome.getText().isEmpty() || txtCPU.getText().isEmpty() || txtGPU.getText().isEmpty()) {
            mostrarMensagem("Erro: Preencha ao menos Nome, CPU e GPU!", "#dc3545");
            return;
        }

        ComponentesDTO objComponenteDTO = new ComponentesDTO();
        objComponenteDTO.setNome(txtNome.getText());
        objComponenteDTO.setGabinete(txtGabinete.getText());
        objComponenteDTO.setCpu(txtCPU.getText());
        objComponenteDTO.setGpu(txtGPU.getText());

        // Convertendo o valor numérico do Spinner para String para o DTO
        objComponenteDTO.setRam(String.valueOf(txtRAM.getValue()) + " GB");

        // Pegando o estado booleano direto do CheckBox (Muito mais seguro)
        objComponenteDTO.setDualchannel(txtDChannel.isSelected());
        objComponenteDTO.setArmazenamento(txtArmaz.getText());
        objComponenteDTO.setBluetooth(txtBluetooth.isSelected());

        ComponentesDAO objComponentesDAO = new ComponentesDAO();
        objComponentesDAO.insertComponente(objComponenteDTO);

        limparCampos();
        carregarComponente();
        mostrarMensagem("PC Setup cadastrado com sucesso!", "#28a745");
    }

    @FXML
    void carregarCampos() {
        ComponentesDTO objComponenteDTO = tblComponentes.getSelectionModel().getSelectedItem();

        if (objComponenteDTO != null) {
            txtId.setText(String.valueOf(objComponenteDTO.getIdPc()));
            txtNome.setText(objComponenteDTO.getNome());
            txtGabinete.setText(objComponenteDTO.getGabinete());
            txtCPU.setText(objComponenteDTO.getCpu());
            txtGPU.setText(objComponenteDTO.getGpu());

            // Tratamento da RAM para colocar o valor numérico de volta no Spinner
            try {
                String ramLimpa = objComponenteDTO.getRam().replaceAll("[^0-9]", "");
                txtRAM.getValueFactory().setValue(Integer.parseInt(ramLimpa));
            } catch (Exception e) {
                txtRAM.getValueFactory().setValue(8); // Fallback caso esteja vazio ou fora do padrão
            }

            // Atribui o booleano direto nas CheckBoxes
            txtDChannel.setSelected(objComponenteDTO.isDualchannel());
            txtArmaz.setText(objComponenteDTO.getArmazenamento());
            txtBluetooth.setSelected(objComponenteDTO.isBluetooth());

            // UX: Ativa botões de edição/deleção, já que existe um registro selecionado
            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            mostrarMensagem("Registro ID " + objComponenteDTO.getIdPc() + " selecionado para edição.", "#ffc107");
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
            mostrarMensagem("Registro excluído com sucesso!", "#dc3545");
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

            objComponenteDTO.setRam(String.valueOf(txtRAM.getValue()) + " GB");
            objComponenteDTO.setDualchannel(txtDChannel.isSelected());
            objComponenteDTO.setArmazenamento(txtArmaz.getText());
            objComponenteDTO.setBluetooth(txtBluetooth.isSelected());

            ComponentesDAO objComponentesDAO = new ComponentesDAO();
            objComponentesDAO.updateComponente(objComponenteDTO);

            limparCampos();
            carregarComponente();
            mostrarMensagem("Registro atualizado com sucesso!", "#28a745");
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtGabinete.clear();
        txtCPU.clear();
        txtGPU.clear();
        txtArmaz.clear();

        // Reseta o Spinner para o valor padrão
        if (txtRAM != null && txtRAM.getValueFactory() != null) {
            txtRAM.getValueFactory().setValue(8);
        }

        // Desmarca as caixas de seleção
        if (txtDChannel != null) txtDChannel.setSelected(false);
        if (txtBluetooth != null) txtBluetooth.setSelected(false);

        // UX: Bloqueia os botões que precisam de seleção na tabela
        if (btnEditar != null) btnEditar.setDisable(true);
        if (btnDeletar != null) btnDeletar.setDisable(true);
    }

    // Método utilitário para atualizar o rótulo de feedback visual (UX)
    private void mostrarMensagem(String texto, String corHex) {
        if (lblMensagem != null) {
            lblMensagem.setText(texto);
            lblMensagem.setStyle("-fx-text-fill: " + corHex + ";");
        }
    }
}