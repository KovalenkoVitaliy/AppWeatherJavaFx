package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text textPressure;

    @FXML
    private Text textTemperatura;

    @FXML
    private Text textCurrent;

    @FXML
    private Text textMax;

    @FXML
    private Text textMin;

    @FXML
    private TextField fieldCity;

    @FXML
    private Button getWeather;

    @FXML
    void initialize() {
        getWeather.setOnAction(actionEvent -> {
            String city = fieldCity.getText().trim();
            if (!city.equals("")) {
                String URL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=a61c775914676ba2c87d5b9bb1b33d3e";
                System.out.println(URL);
                String JsonString =  getJsonString(URL);
                if (!JsonString.equals("")) {
                    try {
                        JSONObject obj = new JSONObject(JsonString);
                        String temp = String.format("%.2f", (obj.getJSONObject("main").getDouble("temp") - 273.15f));
                        textTemperatura.setText("Температура: " + temp +"°");
                        String currTemp = String.format("%.2f", (obj.getJSONObject("main").getDouble("feels_like") - 273.15f));
                        textCurrent.setText("Ощущается: " + currTemp +"°");
                        String maxTemp = String.format("%.2f", (obj.getJSONObject("main").getDouble("temp_max") - 273.15f));
                        textMax.setText("Максимум: " + maxTemp +"°");
                        String minTemp = String.format("%.2f", (obj.getJSONObject("main").getDouble("temp_min") - 273.15f));
                        textMin.setText("Минимум: " + minTemp +"°");
                        String pressure = String.format("%.2f", (obj.getJSONObject("main").getDouble("pressure")*0.750064f));
                        textPressure.setText("Давление: " + pressure + " мм рт. ст.");
                    } catch (JSONException e) {
                    }
                } else {
                    System.out.println("cod:404, message:city not found");
                }
            } else {
                System.out.println("Empty field, please write city");
            }
        });
    }

    private String getJsonString(String url) {
        String JsonString = null;
        StringBuffer stringBuffer = null;
        try {
            URL urlAddr = new URL(url);
            URLConnection urlConnection = urlAddr.openConnection();
            stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
               stringBuffer.append(line + "\n");
                count++;
            }
            bufferedReader.close();
            System.out.println(count); //
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }
}


