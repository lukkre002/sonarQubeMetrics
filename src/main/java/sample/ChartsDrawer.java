package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ChartsDrawer {
    public ChartsDrawer() {
    }

    public ObservableList<PieChart.Data> fillRemediationChart(HashMap<String, HashMap<Integer, Boolean>> remediationMap) {
        String mainKey ="";
        Integer keyIntegerValue=0;
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        Set<String> strings = remediationMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            mainKey = iterator.next();
            Set<Integer> integers = remediationMap.get(mainKey).keySet();
            Iterator<Integer> integerIterator = integers.iterator();

            while (integerIterator.hasNext()){
                String zmiennatest = "aa";
                keyIntegerValue = integerIterator.next();
                if(mainKey.equals("security_remediation_effort"))
                    zmiennatest = "Podatnosci";
                else if (mainKey.contains("reliability_remediation_effort"))
                    zmiennatest="Bugi";
                else {
                    zmiennatest="Code Smells";
                }

                dataList.add(new PieChart.Data(zmiennatest,keyIntegerValue));
            }


        }
        return dataList;
    }
}

