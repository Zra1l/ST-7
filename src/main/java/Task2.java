import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Task2 {
    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
            String jsonText = element.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonText);
            String ipAddress = (String) obj.get("ip");

            System.out.println("Ваш IPv4 адрес: " + ipAddress + "\n");
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении Задания №2:");
            e.printStackTrace();
        }
    }
}
