/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

/**
 *
 * @author jason
 */
public class Data {
    String min;
    String max;
    String mean;

    public Data(String min, String max, String mean) {
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getMean() {
        return mean;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
    
}
