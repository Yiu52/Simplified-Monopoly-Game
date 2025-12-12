/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5;
import java.net.*;
import java.util.*;
import weather.*;
import java.math.*;
/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class Weather {
    public static void main(String[] args) {
        OpenWeather ow = new OpenWeather();
        ow.getWeatherSummary("Hong Kong");

        Scanner s = new Scanner(System.in);
        System.out.print("Enter city name, or exit to quit >");
        String city = s.nextLine();
        while (!city.equals("exit")) {
            try {
                WeatherSummary ws = ow.getWeatherSummary(city);

                if (ws != null) {
                    WeatherAdaptor adt = new WeatherAdaptor();
                    adt.setWeatherSummary(ws);

                    System.out.println(" City:" + adt.getCity()
                            + System.lineSeparator()
                            + " Description:" + adt.getDescription()
                            + System.lineSeparator()
                            + " Temperature:" + Math.round(adt.getTemperature()*100.00)/100.00
                            + System.lineSeparator()
                            + " Wind Direction:" + adt.getWindDirection()
                            + System.lineSeparator()
                            + " Wind Speed:" + adt.getWindSpeed() + "km/h"
                            + System.lineSeparator()
                            + " Icon:" + adt.getIcon()
                    );
                }
            } catch (Exception e) {
                System.out.println("There is an error");
            }
            System.out.println("Enter city name, or exit to quit");
            city = s.nextLine();
        }
    }
}
