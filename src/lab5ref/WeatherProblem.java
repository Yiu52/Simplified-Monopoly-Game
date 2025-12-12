package lab5ref;

import java.net.*;
import java.util.*;

import weather.*;


public class WeatherProblem {

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
                    System.out.println(" City:" + ws.getCity()
                            + System.lineSeparator()
                            + " Description:" + ws.getDescription()
                            + System.lineSeparator()
                            + " Temperature:" + ws.getTemperature()
                            + System.lineSeparator()
                            + " Wind Direction:" + ws.getWindDirection()
                            + System.lineSeparator()
                            + " Wind Speed:" + ws.getWindSpeed()
                            + System.lineSeparator()
                            + " Icon:" + ws.getIcon()
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
