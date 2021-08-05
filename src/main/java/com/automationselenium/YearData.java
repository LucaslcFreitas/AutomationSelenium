package com.automationselenium;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lucas
 */
public class YearData {
    private String year;
    private Map<String, Float[]> data;
    
    public YearData(String year) {
        this.year = year;
        data = new HashMap<>();
    }
    
    public void setData(String school, int month, float value) {
        if(month >= 0 && month < 12)        
            if(data.keySet().contains(school)){
                data.get(school)[month] = value;
            } else {
                data.put(school, new Float[12]);
                for(int i=0; i<12; i++)
                    data.get(school)[i] = (float)0.0;
                data.get(school)[month] = value;
            }
    }
    
    public float getData(String school, int month) {
        if(month >=0 && month < 12)
            if(data.keySet().contains(school)) {
                return data.get(school)[month];
            }
        return -1;
    }
    
    public Float[] getAllData(String school) {
        return data.get(school);
    }
    
    public Set getKeys() {
        return data.keySet();
    }
    
    public String getYear() {
        return year;
    }
}