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

    public ObservableList<PieChart.Data> fillCleanCodeChart(HashMap<String, HashMap<Integer, Boolean>> cleanCodeMap) {
        DataCalculator dl = new DataCalculator();
        Integer codeSmellsRate = 0;
        Integer numOfFunctions = 0;
        Integer numOfClasses = 0;
        Integer numOfLine = 0;
        Integer numOfComments = 0;
        Set<String> metricsNames = cleanCodeMap.keySet();
        Iterator<String> metricNamesIt = metricsNames.iterator();
        while (metricNamesIt.hasNext()) {
            String name = metricNamesIt.next();
            Set<Integer> rateingValues = cleanCodeMap.get(name).keySet();
            Iterator<Integer> rvIterator = rateingValues.iterator();
            while (rvIterator.hasNext()) {
                Integer singleValue = rvIterator.next();
                if (name.equals("code_smells")) {
                    codeSmellsRate = dl.calculateCodeSmellsRateing(singleValue);
                } else if (name.equals("functions")) {
                    numOfFunctions = dl.calculateDuplicatesRateing(singleValue);
                } else if (name.equals("comment_lines")) {
                    numOfComments = singleValue;

                } else if (name.equals("lines")) {
                    numOfLine = singleValue;
                } else if (name.equals("classes")) {
                    numOfClasses = singleValue;
                }
            }
        }
        Integer methodsPerClass = dl.calculateMethodsPerClass(numOfClasses, numOfFunctions);
        Integer linePerMethod = dl.calculateLinePerMethod(numOfLine, numOfFunctions);
        Integer linePerClass = dl.calculateLinesPerClass(numOfClasses, numOfLine);
        Integer commentsPerClass = dl.calculatePercantageCommentsPerClass(numOfClasses, numOfComments);
        Integer rateingLinesPerMethods = dl.calculateRateingLinesPerMethods(linePerMethod);
        Integer rateingLinesPerClass = dl.calculateRateingLinesPerClass(linePerClass);
        Integer rateingCommentsByClass = dl.calculateRateingCommentsByClass(commentsPerClass);
        Integer rateingMethodsPerClass = dl.calculateRateingMethodsPerClass(methodsPerClass);

        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        dataList.add(new PieChart.Data("Ocena stounku ilości lini kodu/metode",rateingLinesPerMethods));
        dataList.add(new PieChart.Data("Ocena stounku ilości lini komentarzy/klase",rateingCommentsByClass));
        dataList.add(new PieChart.Data("Ocena stounku ilości lini kodu/klase",rateingLinesPerClass));
        dataList.add(new PieChart.Data("Ocena stounku ilości metod/klase",rateingMethodsPerClass));
        dataList.add(new PieChart.Data("Ocena stosunku code smells/klase",codeSmellsRate));
        return dataList;
    }

    public XYChart.Series drawCleanCodeBar(HashMap<String, HashMap<Integer, Boolean>> cleanCodeMap){
        DataCalculator dl = new DataCalculator();
        Integer codeSmellsRate = 0;
        Integer numOfFunctions = 0;
        Integer numOfClasses = 0;
        Integer numOfLine = 0;
        Integer numOfComments = 0;
        Set<String> metricsNames = cleanCodeMap.keySet();
        Iterator<String> metricNamesIt = metricsNames.iterator();
        while (metricNamesIt.hasNext()) {
            String name = metricNamesIt.next();
            Set<Integer> rateingValues = cleanCodeMap.get(name).keySet();
            Iterator<Integer> rvIterator = rateingValues.iterator();
            while (rvIterator.hasNext()) {
                Integer singleValue = rvIterator.next();
                if (name.equals("code_smells")) {
                    codeSmellsRate = singleValue;
                } else if (name.equals("functions")) {
                    numOfFunctions = singleValue;
                } else if (name.equals("comment_lines")) {
                    numOfComments = singleValue;

                } else if (name.equals("lines")) {
                    numOfLine = singleValue;
                } else if (name.equals("classes")) {
                    numOfClasses = singleValue;
                }
            }
        }
        Integer methodsPerClass = dl.calculateMethodsPerClass(numOfClasses, numOfFunctions);
        Integer linePerMethod = dl.calculateLinePerMethod(numOfLine, numOfFunctions);
        Integer linePerClass = dl.calculateLinesPerClass(numOfClasses, numOfLine);
        Integer commentsPerClass = dl.calculateCommentsPerClass(numOfClasses, numOfComments);

        XYChart.Series set1 = new XYChart.Series();
        set1.getData().add(new XYChart.Data<>("Stounek ilości lini kodu/metode", linePerMethod));
        set1.getData().add(new XYChart.Data<>("Stounek ilości lini komentarzy/klase", commentsPerClass));
        set1.getData().add(new XYChart.Data<>("Stosunek ilości lini kodu/klase",linePerClass));
        set1.getData().add(new XYChart.Data<>("Stosunek ilości metod/klase",methodsPerClass));
        set1.getData().add(new XYChart.Data<>("Stosunek code smells/klase",codeSmellsRate/numOfClasses));
        return set1;




    }


}

