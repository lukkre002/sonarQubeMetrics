package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.json.JSONException;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PaneController implements Initializable {
    @FXML
    private Pane pane_vun, pane_read, pane_qualit, pane_extra1, pane_extra2;

    @FXML
    private JFXButton btn_vun, btn_readabilty, btn_quality, btn_extra1, btn_extra2;
    public void initialize(URL location, ResourceBundle resources) {
    }
    @FXML
    private Label remediationLabel, quailityLabel;
    @FXML
    private BarChart quailityBar;
    @FXML
    private PieChart remediationChart;


    @FXML
    private void handleButtonAction(ActionEvent event){
        //TODO Pamiętaj żeby poprawić buttony
        DataLoader dl = new DataLoader();
        if(event.getSource()==btn_vun){
            try {
                //Tutaj są podatności, bugi oraz issues
                HashMap<String, HashMap<Integer, Boolean>> vunResult = dl.loadHttpDate("http://localhost:9000/api/measures/component?componentKey=my%3Aproject&metricKeys=bugs,violations,vulnerabilities");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            pane_vun.toFront();
        }
        else if (event.getSource()==btn_readabilty){
            try {
                //tutaj
                HashMap<String, HashMap<Integer, Boolean>> readResult = dl.loadHttpDate("http://localhost:9000/api/measures/component?componentKey=my%3Aproject&metricKeys=functions,lines,classes,code_smells,comment_lines");
            DataCalculator dataCalculator = new DataCalculator();
            dataCalculator.caluateCleanCode(readResult);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pane_read.toFront();
        }
        else if (event.getSource()==btn_quality){
            try {
                ChartsDrawer chartsDrawer  = new ChartsDrawer();
                quailityBar.getData().clear();
                XYChart.Series set1 = new XYChart.Series();
                HashMap<String, HashMap<Integer, Boolean>> qualityResult = dl.loadHttpDate("http://localhost:9000/api/measures/component?componentKey=my%3Aproject&metricKeys=security_rating,reliability_rating,code_smells,duplicated_lines_density");
                DataCalculator dataCalculator = new DataCalculator();
                String quailityResult = dataCalculator.codeQualityRateing(qualityResult);
                quailityLabel.setText(quailityResult);
                XYChart.Series series = chartsDrawer.fillCodeQualitiBar(qualityResult);
                quailityBar.getData().addAll(series);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pane_qualit.toFront();
        }
        else if(event.getSource()==btn_extra1){
            //remediationChart
            //Czas potrzebny na wyprowadzenie
            //reliability_remediation_effort
            //security_remediation_effort
            //sqale_index
            try {
                HashMap<String, HashMap<Integer, Boolean>> remedationResult = dl.loadHttpDate("http://localhost:9000/api/measures/component?componentKey=my%3Aproject&metricKeys=reliability_remediation_effort,security_remediation_effort,sqale_index");
                DataCalculator dataCalculator = new DataCalculator();
                ChartsDrawer chartsDrawer = new ChartsDrawer();
                Integer remediationTime = dataCalculator.calulateRemediationEffort(remedationResult);
                remediationLabel.setText(remediationTime.toString()+" minut, czyli: "+(remediationTime/60) +" godzin"+ " ,co daje: "+((remediationTime/60)/24)+" dni");
                ObservableList<PieChart.Data> data =FXCollections.observableArrayList();

                      data = chartsDrawer.fillRemediationChart(remedationResult);
                ObservableList<PieChart.Data>  xd= FXCollections.observableArrayList();
                xd.addAll(new PieChart.Data("chuj",90.0),
                        new PieChart.Data("aaa",20.0));
                remediationChart.setData(data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pane_extra1.toFront();
        }
        else {
            pane_extra2.toFront();
        }
    }




}
