/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.exam;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.exam.model.Dettaglio;
import it.polito.tdp.exam.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDettagli"
    private Button btnDettagli; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<String> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtTifosi"
    private TextField txtTifosi; // Value injected by FXMLLoader

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	String team = cmbSquadra.getValue();
    	if (team == null) {
    	    txtResult.setText("È necessario selezionare una squadra per poter creare il grafo.");
    	} else {
    	    this.model.creaGrafo(team);
    	    
    	    Set<Integer> vertici = this.model.getVertici();
    	    Set<DefaultWeightedEdge> archi = this.model.getArchi();

    	    txtResult.setText("Grafo creato.\n");
    	    txtResult.appendText("Ci sono " + vertici.size() + " vertici\n");
    	    txtResult.appendText("Ci sono " + archi.size() + " archi\n\n");

    	    cmbAnno.setDisable(false);
    	    btnDettagli.setDisable(false);
    	    cmbAnno.getItems().clear();
    	    cmbAnno.getItems().addAll(vertici);
    	}
    
    }

    @FXML
    void handleDettagli(ActionEvent event) {
    	
    	
    	// metodo per dettagli

    	Integer anno = this.cmbAnno.getValue();
    	if (anno == null) {
    		this.txtResult.setText("Selezionare un anno dall'apposito menu\n");
    		return;
    	}
    	
    	
    	Set<DefaultWeightedEdge> archi = this.model.getArchi();
    	
    	if (archi.size() == 0) {
    	    this.txtResult.appendText("Non ci sono archi da poter analizzare.");
    	} else {
    	    List<Dettaglio> dettagli = this.model.getDettagli(anno);
    	    this.txtResult.appendText("\nDettagli per l'anno scelto (in ordine crescente di peso):\n");
    	    for (Dettaglio d : dettagli) {
    	        this.txtResult.appendText("anno: " + anno + "<->" + d + "\n");
    	    }
    	}
    	
    }

    @FXML
    void handleSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDettagli != null : "fx:id=\"btnDettagli\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTifosi != null : "fx:id=\"txtTifosi\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
        this.model = model;
        this.cmbSquadra.getItems().setAll(model.getTeamsNames());
        this.cmbAnno.setDisable(true);
        this.btnDettagli.setDisable(true);
    
    }

}
