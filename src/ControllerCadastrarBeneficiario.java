/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import DAO.BeneficiarioDAO;
import Model.Beneficiario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastrarBeneficiario implements Initializable {
    
    private Scene cenaAnterior;
    private ItemCallback itemCallBack;
    
    @FXML
    private TextField txtfldNomeBeneficiario;
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(this.cenaAnterior);
        window.show();
    }
    
    public void limparCampos(ActionEvent event) {
        txtfldNomeBeneficiario.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Scene getCenaAnterior() {
        return cenaAnterior;
    }

    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }
    
    public interface ItemCallback {
        void onItemUpdated();
    } 

    public void setItemCallBack(ItemCallback itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
    
    public void cadastrarBeneficiario(ActionEvent event) {
        Beneficiario beneficiario = new Beneficiario(txtfldNomeBeneficiario.getText());
        BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
        beneficiarioDAO.insert(beneficiario);
        JOptionPane.showMessageDialog(null, "Beneficiario registrado com sucesso!");
        limparCampos(event);
    }  
}
