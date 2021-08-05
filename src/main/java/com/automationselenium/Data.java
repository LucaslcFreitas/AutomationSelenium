package com.automationselenium;

import java.util.Set;

/**
 *
 * @author Lucas
 */
public class Data {
    private int length;
    private YearData yearData[];
    
    public Data(String labels[], int length) {
        this.length = length;
        yearData = new YearData[length];
        for(int i=0; i<length; i++) {
            yearData[i] = new YearData(labels[i]);
        }
    }
    
    public void setData(String school, int year, int month, float value) {
        yearData[year].setData(school, month, value);
    }
    
    public YearData[] getYearsData() {
        return yearData;
    }
    
    public void printData() {
        for(int i=0; i<length; i++) {
            System.out.println("Ano: "+yearData[i].getYear());
            Set<String> schools = yearData[i].getKeys();
            for(String school : schools) {
                System.out.print(school+" ");
                //System.out.print(yearData[i].getAllData(school)[0]);
                for(int j=0; j<12; j++) {
                    System.out.print(yearData[i].getData(school, j)+" ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }
}