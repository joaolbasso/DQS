/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import DAO.BeneficiarioDAO;
import Model.Beneficiario;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ControllerCadastrarBeneficiario implements Initializable {
    
    private Scene cenaAnterior;
    private ItemCallback itemCallBack;
    
    @FXML
    private TextField txtfldNomeBeneficiario;
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        if (!txtfldNomeBeneficiario.getText().trim().isEmpty()) {
           String message = "Tem certeza que deseja voltar? Todos os dados já preenchidos serão perdidos!";
           String title = "Confirmação";

        // Opções de botões
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType = JOptionPane.WARNING_MESSAGE;

        // Exibe o diálogo de confirmação
        int response = JOptionPane.showConfirmDialog(null, message, title, optionType, messageType);
        if (response == JOptionPane.YES_OPTION) {
            if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(this.cenaAnterior);
            window.show();
        }  

        } else {
            if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(this.cenaAnterior);
            window.show();
        }
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

    private void popupConfirmacao(String mensagem_confirmacao) {
        final JOptionPane optionPane = new JOptionPane(mensagem_confirmacao, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = optionPane.createDialog("Mensagem");
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dialog.dispose();
            }
        });
        
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
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
        popupConfirmacao("Beneficiário registrado com sucesso!");
        limparCampos(event);
    }  
}
