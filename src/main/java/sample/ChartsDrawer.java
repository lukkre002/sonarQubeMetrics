package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ChartsDrawer {
    public ChartsDrawer() {
    }

    public ObservableList<PieChart.Data> fillRemediationChart(HashMap<String, HashMap<Integer, Boolean>> remediationMap) {
        String mainKey = "";
        Integer keyIntegerValue = 0;
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        Set<String> strings = remediationMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            mainKey = iterator.next();
            Set<Integer> integers = remediationMap.get(mainKey).keySet();
            Iterator<Integer> integerIterator = integers.iterator();

            while (integerIterator.hasNext()) {
                String zmiennatest = "aa";
                keyIntegerValue = integerIterator.next();
                if (mainKey.equals("security_remediation_effort"))
                    zmiennatest = "Podatnosci";
                else if (mainKey.contains("reliability_remediation_effort"))
                    zmiennatest = "Bugi";
                else {
                    zmiennatest = "Code Smells";
                }

                dataList.add(new PieChart.Data(zmiennatest, keyIntegerValue));
            }


        }
        return dataList;
    }

    public XYChart.Series fillCodeQualitiBar(HashMap<String, HashMap<Integer, Boolean>> remediationMap) {
        DataCalculator dl = new DataCalculator();
        XYChart.Series set1 = new XYChart.Series();
        String mainKey = "";
        Integer keyIntegerValue = 0;
        Set<String> strings = remediationMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            mainKey = iterator.next();
            Set<Integer> integers = remediationMap.get(mainKey).keySet();
            Iterator<Integer> integerIterator = integers.iterator();

            while (integerIterator.hasNext()) {
                String zmiennatest = "aa";
                keyIntegerValue = integerIterator.next();
                if(mainKey.equals("code_smells")){
                    keyIntegerValue = dl.calculateCodeSmellsRateing(keyIntegerValue);
                } else if(mainKey.equals("duplicated_lines_density")){
                    keyIntegerValue = dl.calculateDuplicatesRateing(keyIntegerValue);
                }
                set1.getData().add(new XYChart.Data<>(mainKey, keyIntegerValue));

            }
        }
        return set1;
    }
}

