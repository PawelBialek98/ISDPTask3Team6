import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumTests {
    WebDriver webDriver = new FirefoxDriver();
    private String url = "https://macbook-pro-pawe.local:8181/";

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.26.0");
    }

    @Test
    void sampleTest(){
        webDriver.navigate().to(url + "faces/main/index.xhtml");
        webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div/nav/div/div[2]/ul[1]/li[2]/a")).click(); // przejdz do zakladki logowanie
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[1]/td[2]/input")).sendKeys("JDoe");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input")).sendKeys("P@ssw0rd");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/p/input")).click(); //click login
        webDriver.navigate().to(url +"/faces/location/listLocations.xhtml");
        String orgValue = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText(); //get current value;
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[5]/input[1]")).click(); //click Edycja lokalizacji;
        Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"EditLocationForm:locationType\"]")));
        String selectedValue = select.getFirstSelectedOption().getText();
        select.selectByValue("SHELF3");
        String newSelectedValue = select.getFirstSelectedOption().getText();
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/input[2]")).click();//zatwierdzamy zmianę
        String curValue = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText();
        Assert.assertEquals(curValue, newSelectedValue);
        //Cofamy zmiany
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[5]/input[1]")).click();
        select = new Select(webDriver.findElement(By.xpath("//*[@id=\"EditLocationForm:locationType\"]")));
        select.selectByVisibleText(orgValue);
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/input[2]")).click();//zatwierdzamy zmianę
        String finalValue = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText();
        Assert.assertEquals(orgValue, finalValue);
        webDriver.quit();
    }
}
