package tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "IssuesMantisTestData.csv")
public class IssuesMantisTest {
    private WebDriver navegador;

    @Before
    public void setUp() {
       navegador = Web.createChrome();
        WebElement formLogin = navegador.findElement(By.cssSelector("body div:nth-of-type(3")); //body//div[3]
        formLogin.findElement(By.cssSelector("input[name='username']")).sendKeys("elvercio.rodrigues");
        formLogin.findElement(By.cssSelector("input[name='password']")).sendKeys("provaqa");
        formLogin.findElement(By.cssSelector("input[class='button']")).click();

    }
    @Test
    public void testAdicionarUmaIssue(@Param(name="Category")String Category,
                                      @Param(name="Reproducibility")String Reproducibility,
                                      @Param(name="Severity")String Severity,
                                      @Param(name="Priority")String Priority,
                                      @Param(name="Profile")String Profile,
                                      @Param(name="Summary")String Summary,
                                      @Param(name="Description")String Description,
                                      @Param(name="Steps")String Steps) {



        //Clicar em Report Issue
        navegador.findElement(By.cssSelector("table td.menu > a:nth-child(3)")).click();

        //Preencher os campos da issue

        //Escolher Category
        WebElement comboCategory = navegador.findElement(By.cssSelector("select[name=category_id]"));
        Select category = new Select(comboCategory);
        category.selectByVisibleText(Category);

        //Escolher Reproducibility
        WebElement comboReproducibility = navegador.findElement(By.cssSelector("select[name=reproducibility]"));
        new Select(comboReproducibility).selectByVisibleText(Reproducibility);

        // Escolher Severity
        WebElement comboSeverity = navegador.findElement(By.cssSelector("select[name=severity]"));
        new Select(comboSeverity).selectByVisibleText(Severity);

        //Escolher Priority
        WebElement comboPriority = navegador.findElement(By.cssSelector("select[name=priority]"));
        new Select(comboPriority).selectByVisibleText(Priority);

        //Escolher Profile
        WebElement comboProfile = navegador.findElement(By.cssSelector("select[name=profile_id]"));
        new Select(comboProfile).selectByVisibleText(Profile);

        //Preencher Summary
        navegador.findElement(By.cssSelector("input[name=summary")).sendKeys(Summary);

        //Preencher Description
        navegador.findElement(By.cssSelector("textarea[name=description]"))
                .sendKeys(Description);

        //Preencher Steps to Reproduce
        navegador.findElement(By.cssSelector("textarea[name=steps_to_reproduce"))
                .sendKeys(Steps);

        //Clicar no botão "Submit Report"
        navegador.findElement(By.cssSelector("input[class=button]")).click();

        //Validar a mensagem de sucesso
        WebElement mensagemSucesso = navegador.findElement(By.xpath("//div[contains(text(),'Operation successful')]"));
        String mensagem = mensagemSucesso.getText();
        assertEquals(mensagem, mensagem);

        //Tirar print
        Screenshot.tirar(navegador, "C:\\Users\\Elvécio\\test-report\\mantis\\" + Generator.dataHoraParaArquivo() + ".png");


        // Aguardar até 5 segundos para que a página mude
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemSucesso));

        //Realizar Logout
        navegador.findElement(By.cssSelector("table td.menu > a:nth-child(7")).click();
    }

    @Test
    public void testAdicionarNotesNaIssue() {
         //Clicar em View Issues
         navegador.findElement(By.xpath("//a[contains(text(),'View Issues')]")).click();

         //Escolher uma Issue para visualizar
         navegador.findElement(By.xpath("//a[contains(text(),'0004528')]")).click();


         //Acrescentar uma nota na Issue
        navegador.findElement(By.xpath("//textarea[@name='bugnote_text']"))
                .sendKeys("Esse bug deve ser tratado com urgência");

        //Clicar em Adicionar
        navegador.findElement(By.cssSelector("form[name=bugnoteadd] input.button")).click();
        }

    @After
    public  void tearDown() {
        navegador.quit();
    }

}
