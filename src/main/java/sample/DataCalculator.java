package sample;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DataCalculator
{

    public DataCalculator() {
    }

    public Integer calulateRemediationEffort(HashMap<String, HashMap<Integer, Boolean>> remediationMap) {
        Integer remediationValue = 0;
        Collection<HashMap<Integer, Boolean>> values = remediationMap.values();
        Iterator<HashMap<Integer, Boolean>> iterator = values.iterator();
        while (iterator.hasNext()){
            HashMap<Integer, Boolean> next = iterator.next();
            Set<Integer> integers = next.keySet();
            Iterator<Integer> integerIterator = integers.iterator();
            while (integerIterator.hasNext()){
                Integer next1 = integerIterator.next();
                remediationValue = remediationValue + next1;
            }
        }
        return remediationValue;
    }

    public Integer codeQualityRateing(HashMap<String, HashMap<Integer, Boolean>> qualityMap){
        Integer result = 0;
        Set<String> metricsNames = qualityMap.keySet();
        Iterator<String> metricNamesIt = metricsNames.iterator();
        while (metricNamesIt.hasNext()){
            String name = metricNamesIt.next();
            Set<Integer> rateingValues = qualityMap.get(name).keySet();
            Iterator<Integer> rvIterator = rateingValues.iterator();
            while (rvIterator.hasNext()){
                Integer singleValue = rvIterator.next();
                if(name.equals("code_smells")){
                    singleValue = calculateCodeSmellsRateing(singleValue);
                }
                result= result+singleValue;


            }

        }
        result = result/metricsNames.size();
        return result;
    }

    private Integer calculateCodeSmellsRateing(Integer value){
        Integer result = 0;
        if(value == 0) {
            result = 1;
        } else if (value>0 && value<20){
            result = 2;
        }
        else if (value>=20 && value<50){
            result = 3;
        }
        else if (value>=50 && value<100){
            result = 4;
        }
        else {
            result = 5;
        }
        return result;
    }

}
