import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorRequest {
    public static void main(String[] args) {
        final String sensorName = "sensor number 9";
        final double minTemperature = -100;
        final double maxTemperature = 100;

        registerSensor(sensorName);

        for (int i = 0; i < 10; i++) {
            double value = generateRandomValue(minTemperature, maxTemperature);
            boolean raining = generateRandomRaining();
            sendMeasurement(value, raining, sensorName);
        }

        System.out.println(getMeasurements());
    }

    private static void registerSensor(String sensorName) {
        final String urlSensorReg = "http://localhost:8090/sensors/registration";

        Map<String, Object> sensorJson = new HashMap<>();
        sensorJson.put("name", sensorName);
        makePostRequest(urlSensorReg, sensorJson);
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String urlAddMeasurement = "http://localhost:8090/measurements/add";

        Map<String, Object> measurementJson = new HashMap<>();
        measurementJson.put("value", value);
        measurementJson.put("raining", raining);
        measurementJson.put("sensor", Map.of("name", sensorName));

        makePostRequest(urlAddMeasurement, measurementJson);

    }

    private static List<Map<String, Object>> getMeasurements() {
        final String urlGetMeasurements = "http://localhost:8090/measurements";
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlGetMeasurements, List.class);
    }

    private static double generateRandomValue(double min, double max) {
        double value = Math.random() * (max - min) + min;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(value));
    }

    private static boolean generateRandomRaining() {
        int n = (int) (Math.random() * 2);
        return n == 0;
    }

    private static void makePostRequest(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonData);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Data sent successfully!");
        } catch (HttpClientErrorException e) {
            System.out.println("Error! " + e.getMessage());
        }

    }

}

