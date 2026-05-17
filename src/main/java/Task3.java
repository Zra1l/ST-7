import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileWriter;
import java.time.Duration;

public class Task3 {
    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
            String jsonText = element.getText();

            JSONParser parser = new JSONParser();
            JSONObject rootObj = (JSONObject) parser.parse(jsonText);

            JSONObject hourlyObj = (JSONObject) rootObj.get("hourly");

            JSONArray timesArray = (JSONArray) hourlyObj.get("time");
            JSONArray tempsArray = (JSONArray) hourlyObj.get("temperature_2m");
            JSONArray rainsArray = (JSONArray) hourlyObj.get("rain");

            StringBuilder table = new StringBuilder();
            table.append(String.format("|%-3s|%-13s|%-13s|%-14s|\n", "№", "  Дата/время   ", " Температура ", " Осадки (мм)  "));
            table.append("|---|-------------|-------------|--------------|\n");

            int size = timesArray.size();
            for (int i = 0; i < size; i++) {
                String time = (String) timesArray.get(i);
                double temp = ((Number) tempsArray.get(i)).doubleValue();
                double rain = ((Number) rainsArray.get(i)).doubleValue();

                table.append(String.format("|%-3d|%-13s|%-13.1f|%-14.2f|\n",
                        (i + 1),
                        time,
                        temp,
                        rain));
            }

            System.out.print(table.toString());

            File dir = new File("result");
            if (!dir.exists()) {
                dir.mkdir();
            }
            try (FileWriter writer = new FileWriter("result/forecast.txt")) {
                writer.write(table.toString());
            }
            System.out.println("\nПрогноз погоды успешно сохранен в result/forecast.txt\n");

        } catch (Exception e) {
            System.out.println("Ошибка при выполнении Задания №3:");
            e.printStackTrace();
        }
    }
}
