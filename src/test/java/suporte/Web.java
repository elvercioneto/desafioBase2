package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Web {
    public static  WebDriver createChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chrome\\84\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://mantis-prova.base2.com.br/");
        navegador.manage().window().maximize();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return navegador;
    }
}
