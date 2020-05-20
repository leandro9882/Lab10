/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.bar;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.bar.model.Model;
import it.polito.tdp.bar.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class FXMLController {
	Simulator s;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Map<Integer, Integer> tavoli=new HashMap<>();
		tavoli.put(4, 5); //5 tavoli da 4 posti
		tavoli.put(6, 4);
		tavoli.put(8, 4);
		tavoli.put(10, 2);
		s.setTavoli(tavoli, 15);
		int d=(int)(Math.random()*10)+1;
		s.setClientFrequency(Duration.of(d, ChronoUnit.MINUTES));
		
		s.run();
    	this.txtResult.setText(s.getStatistiche().toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setSimulator(Simulator s) {
	this.s=s;
	}
}
