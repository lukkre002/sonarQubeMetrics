package sample;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DataCalculator {

    public DataCalculator() {
    }

    public Integer calulateRemediationEffort(HashMap<String, HashMap<Integer, Boolean>> remediationMap) {
        Integer remediationValue = 0;
        Collection<HashMap<Integer, Boolean>> values = remediationMap.values();
        Iterator<HashMap<Integer, Boolean>> iterator = values.iterator();
        while (iterator.hasNext()) {
            HashMap<Integer, Boolean> next = iterator.next();
            Set<Integer> integers = next.keySet();
            Iterator<Integer> integerIterator = integers.iterator();
            while (integerIterator.hasNext()) {
                Integer next1 = integerIterator.next();
                remediationValue = remediationValue + next1;
            }
        }
        return remediationValue;
    }

    public String codeQualityRateing(HashMap<String, HashMap<Integer, Boolean>> qualityMap) {
        Integer result = 0;
        Set<String> metricsNames = qualityMap.keySet();
        Iterator<String> metricNamesIt = metricsNames.iterator();
        while (metricNamesIt.hasNext()) {
            String name = metricNamesIt.next();
            Set<Integer> rateingValues = qualityMap.get(name).keySet();
            Iterator<Integer> rvIterator = rateingValues.iterator();
            while (rvIterator.hasNext()) {
                Integer singleValue = rvIterator.next();
                if (name.equals("code_smells")) {
                    singleValue = calculateCodeSmellsRateing(singleValue);
                } else if (name.equals("duplicated_lines_density")) {
                    singleValue = calculateDuplicatesRateing(singleValue);
                }
                result = result + singleValue;


            }

        }
        result = result / metricsNames.size();
        if (result == 1) {
            return "A";
        } else if (result == 2) {
            return "B";
        } else if (result == 3) {
            return "C";
        } else if (result == 4) {
            return "D";
        } else {
            return "E";
        }
    }

    public Integer calculateDuplicatesRateing(Integer singleValue) {
        Integer result = 0;
        if (singleValue == 0) {
            result = 1;
        } else if (singleValue > 0 && singleValue < 10) {
            result = 2;
        } else if (singleValue >= 10 && singleValue < 30) {
            result = 3;
        } else if (singleValue >= 30 && singleValue < 60) {
            result = 4;
        } else {
            result = 5;
        }
        return result;
    }

    public Integer calculateCodeSmellsRateing(Integer value) {
        Integer result = 0;
        if (value == 0) {
            result = 1;
        } else if (value > 0 && value < 20) {
            result = 2;
        } else if (value >= 20 && value < 50) {
            result = 3;
        } else if (value >= 50 && value < 100) {
            result = 4;
        } else {
            result = 5;
        }
        return result;
    }

    public Integer caluateCleanCode(HashMap<String, HashMap<Integer, Boolean>> cleanCodeMap) {
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
                    codeSmellsRate = calculateCodeSmellsRateing(singleValue);
                } else if (name.equals("functions")) {
                    numOfFunctions = calculateDuplicatesRateing(singleValue);
                } else if (name.equals("comment_lines")) {
                    numOfComments = singleValue;

                } else if (name.equals("lines")) {
                    numOfLine = singleValue;
                } else if (name.equals("classes")) {
                    numOfClasses = singleValue;
                }
            }
        }
        Integer methodsPerClass = calculateMethodsPerClass(numOfClasses, numOfFunctions);
        Integer linePerMethod = calculateLinePerMethod(numOfLine, numOfFunctions);
        Integer linePerClass = calculateLinesPerClass(numOfClasses, numOfLine);
        Integer commentsPerClass = calculatePercantageCommentsPerClass(numOfClasses, numOfComments);
        Integer rateingLinesPerMethods = calculateRateingLinesPerMethods(linePerMethod);
        Integer rateingLinesPerClass = calculateRateingLinesPerClass(linePerClass);
        Integer rateingCommentsByClass = calculateRateingCommentsByClass(commentsPerClass);
        Integer rateingMethodsPerClass = calculateRateingMethodsPerClass(methodsPerClass);


        return (rateingCommentsByClass + rateingLinesPerClass + rateingMethodsPerClass + rateingLinesPerMethods) / 4;
    }

    public Integer calculateMethodsPerClass(Integer numOfClass, Integer numOfFunc) {
        return numOfFunc / numOfClass;
    }

    public Integer calculateLinePerMethod(Integer numOfLines, Integer numOfFunc) {
        return numOfLines / numOfFunc;
    }

    public Integer calculateLinesPerClass(Integer numOfClasses, Integer numOfLines) {
        return numOfLines / numOfClasses;
    }

    public Integer calculatePercantageCommentsPerClass(Integer numOfClasses, Integer numberOfComments) {
        return numberOfComments / numOfClasses * 100;
    }

    public Integer calculateRateingLinesPerMethods(Integer methods) {
        if (methods < 30) {
            return 1;
        } else if (methods >= 30 && methods < 60) {
            return 2;
        } else if (methods >= 60 && methods < 90) {
            return 3;
        } else if (methods >= 90 && methods < 120) {
            return 4;
        } else {
            return 5;
        }
    }

    public Integer calculateRateingLinesPerClass(Integer lines) {
        if (lines < 900) {
            return 1;
        } else if (lines >= 900 && lines < 1200)
            return 2;
        else if (lines >= 1200 && lines < 1500) {
            return 3;
        } else if (lines >= 1500 && lines < 1800)
            return 4;
        else
            return 5;
    }

    public Integer calculateRateingCommentsByClass(Integer comments) {
        if (comments < 10) {
            return 4;
        } else if (comments >= 10 && comments < 25) {
            return 1;
        } else if (comments >= 25 && comments < 30) {
            return 2;
        } else if (comments >= 30 && comments < 40) {
            return 4;
        } else {
            return 5;
        }
    }

    public Integer calculateRateingMethodsPerClass(Integer methods) {
        if (methods < 30) {
            return 1;
        } else if (methods >= 30 && methods < 60) {
            return 2;
        } else if (methods >= 60 && methods < 90) {
            return 3;
        } else if (methods >= 90 && methods < 120) {
            return 4;
        } else {
            return 5;
        }
    }

}
