/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5;

import java.net.URL;
import weather.*;

/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class WeatherAdaptor {
    WeatherSummary weatherSummary;
    
    public void setWeatherSummary(WeatherSummary weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public String getCity() {
        return weatherSummary.getCity();
    }    
    
    public String getDescription() {
        return weatherSummary.getDescription();
    }

    public double getTemperature() {
        return convertedTempDKToDC(weatherSummary.getTemperature());
    }
    
    public double getWindSpeed() {
        return convertedWindSpeed(weatherSummary.getWindSpeed());
    }

    public String getWindDirection(){
        return convertedWindDirection(weatherSummary.getWindDirection());
    }
    
    public URL getIcon(){
        return weatherSummary.getIcon();
    }
    
    public double convertedTempDKToDC(double DK){
        return DK-273;
    }
    
    public double convertedWindSpeed(double WS){
        return WS*3.6;
    }
    
    public String convertedWindDirection(double WD){
        if((WD>=0 && WD <22.5) || (WD>=337.5 && WD<360)){
            return "N";
        }else{
            if(WD>=22.5 && WD<67.5){
                return "NE";
            }else{
                if(WD>=67.5 && WD <112.5){
                    return "E";
                }else{
                    if(WD>=112.5 && WD>157.5){
                        return "SE";
                    }else{
                        if(WD>=157.5 && WD>202.5){
                            return "S";
                        }else{
                            if(WD>=202.5 && WD>247.5){
                                return "SW";
                            }else{
                                if(WD>=247.5 && WD>292.5){
                                    return "W";
                                }else{
                                    if(WD>=292.5 && WD>337.5){
                                        return "NW";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "Invalid input";
    }
}
