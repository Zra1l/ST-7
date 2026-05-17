import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App {
    public static void main(String[] args) {
        WebDriver webDriver = new ChromeDriver();
        try {
            System.out.println("--- Задание №1 ---");
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            String generatedPassword = "";

            try {
                WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
                generatedPassword = passwordElement.getAttribute("value");
                if (generatedPassword == null || generatedPassword.isEmpty()) {
                    generatedPassword = passwordElement.getText();
                }
            } catch (Exception e) {
                try {
                    WebElement backupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//b")));
                    generatedPassword = backupElement.getText();
                } catch (Exception ex) {
                    generatedPassword = "Не удалось локализовать элемент пароля";
                }
            }

            System.out.println("Сгенерированный пароль с сайта: " + generatedPassword.trim() + "\n");

            System.out.println("--- Задание №2 ---");
            Task2.run(webDriver);

            System.out.println("--- Задание №3 ---");
            Task3.run(webDriver);

        } catch (Exception e) {
            System.out.println("Произошла критическая ошибка в главном классе App:");
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}
