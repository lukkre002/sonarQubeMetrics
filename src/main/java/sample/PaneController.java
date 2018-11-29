package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.json.JSONException;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaneController implements Initializable {
    @FXML
    private Pane pane_vun, pane_read, pane_qualit, pane_extra1, pane_extra2;

    @FXML
    private JFXButton btn_vun, btn_readabilty, btn_quality, btn_extra1, btn_extra2;
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleButtonAction(ActionEvent event){
        DataLoader dl = new DataLoader();
        if(event.getSource()==btn_vun){

            pane_vun.toFront();
        }
        else if (event.getSource()==btn_readabilty){
            try {
                dl.loadHttpDate("http://localhost:9000/api/measures/component?componentKey=my%3Aproject&metricKeys=bugs,code_smells,vulnerabilities");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pane_read.toFront();
        }
        else if (event.getSource()==btn_quality){
            pane_qualit.toFront();
        }
        else if(event.getSource()==btn_extra1){
            pane_extra1.toFront();
        }
        else {
            pane_extra2.toFront();
        }
    }


}
