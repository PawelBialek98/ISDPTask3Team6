import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class SeleniumTests {
    WebDriver webDriver;
    //private String url = "https://macbook-pro-pawe.local:8181/";
    private String url = "https://localhost:8181/";

    @BeforeTest
    void setUp(){
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Lukasz\\Desktop\\geckodriver-v0.26.0-win64\\geckodriver.exe");
    }

    @Test
    void modifyLocationTest() {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to(url + "faces/main/index.xhtml");
        webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div/nav/div/div[2]/ul[1]/li[2]/a")).click(); // przejdz do zakladki logowanie
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[1]/td[2]/input")).sendKeys("JDoe");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input")).sendKeys("P@ssw0rd");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/p/input")).click(); //click login
        webDriver.navigate().to(url + "/faces/location/listLocations.xhtml");
        String orgValue = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText(); //get current value;
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[5]/input[1]")).click(); //click Edycja lokalizacji;
        Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"EditLocationForm:locationType\"]")));
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

    @Test
    void addContractorTest() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to(url + "faces/main/index.xhtml");
        webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div/nav/div/div[2]/ul[1]/li[2]/a")).click(); // przejdz do zakladki logowanie
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[1]/td[2]/input")).sendKeys("LRey");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input")).sendKeys("P@ssw0rd");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/p/input")).click(); //click login
        webDriver.navigate().to(url + "faces/contractor/listContractors.xhtml");
        List<WebElement> rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 4);

        webDriver.navigate().to(url + "faces/contractor/registerContractor.xhtml");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:contractorNumber\"]")).sendKeys("0048856746851");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:contractorName\"]")).sendKeys("Jan Kowalski");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:nip\"]")).sendKeys("542-315-12-58");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:street\"]")).sendKeys("Wiejska");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:house\"]")).sendKeys("18");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:zip\"]")).sendKeys("98-013");
        webDriver.findElement(By.xpath("//*[@id=\"RegisterContractorForm:city\"]")).sendKeys("Warszawa");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/input[2]")).click();
        webDriver.navigate().to(url + "faces/contractor/listContractors.xhtml");
        rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        String name = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[5]/td[2]")).getText();
        Assert.assertEquals(name, "Jan Kowalski");
        Assert.assertEquals(rows.size(), 5);
        WebElement element = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[5]/td[10]/input[2]"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[5]/td[10]/input[2]")).click();
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/input[2]")).click();
        webDriver.navigate().to(url + "faces/contractor/listContractors.xhtml");
        rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 4);
        webDriver.quit();
    }

    @Test
    public void testChangeName() {
        webDriver = new FirefoxDriver();
        webDriver.get(url + "faces/main/index.xhtml");
        webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div/nav/div/div[2]/ul[1]/li[2]/a")).click();
        webDriver.findElement(By.name("j_username")).sendKeys("DMitchell");
        webDriver.findElement(By.name("j_password")).sendKeys("P@ssw0rd");
        webDriver.findElement(By.cssSelector("input:nth-child(2)")).click();
        webDriver.navigate().to(url + "faces/account/listAuthorizedAccounts.xhtml");
        webDriver.findElement(By.name("j_idt26:j_idt27:0:j_idt40")).click();
        String originalName = webDriver.findElement(By.id("EditForm:name")).getAttribute("value");
        webDriver.findElement(By.id("EditForm:name")).clear();
        webDriver.findElement(By.id("EditForm:name")).sendKeys("John");
        webDriver.findElement(By.name("EditForm:j_idt32")).click();
        {
            String value = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText();
            Assert.assertEquals(value, "John");
        }
        webDriver.findElement(By.name("j_idt26:j_idt27:0:j_idt40")).click();
        webDriver.findElement(By.id("EditForm:name")).clear();
        webDriver.findElement(By.id("EditForm:name")).sendKeys(originalName);
        webDriver.findElement(By.name("EditForm:j_idt32")).click();
        {
            String value = webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr[1]/td[2]")).getText();
            Assert.assertEquals(value, originalName);
        }
        webDriver.quit();
    }

    @Test
    public void removeLocationTest() {
        webDriver = new FirefoxDriver();

        webDriver.navigate().to(url + "faces/main/index.xhtml");
        webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div/nav/div/div[2]/ul[1]/li[2]/a")).click(); // przejdz do zakladki logowanie
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[1]/td[2]/input")).sendKeys("JDoe");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input")).sendKeys("P@ssw0rd");
        webDriver.findElement(By.xpath("/html/body/div/div[3]/div/form/p/input")).click();

        webDriver.navigate().to(url + "/faces/location/listLocations.xhtml");
        List<WebElement> rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 10);

        webDriver.navigate().to(url + "faces/location/createNewLocation.xhtml");
        webDriver.findElement(By.id("CreateLocationForm:locationSymbol")).sendKeys("XX-12-34-56");
        Select select = new Select(webDriver.findElement(By.id("CreateLocationForm:locationType")));
        select.selectByValue("SHELF3");
        webDriver.findElement(By.name("CreateLocationForm:j_idt34")).click();

        webDriver.navigate().to(url + "/faces/location/listLocations.xhtml");
        rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 11);

        webDriver.findElement(By.name("j_idt26:j_idt27:10:onlyWarehouse:j_idt38")).click();
        webDriver.findElement(By.name("DeleteLocationForm:j_idt30")).click();

        rows = webDriver.findElements(By.xpath("/html/body/div/div[3]/div/form/table/tbody/tr"));
        Assert.assertEquals(rows.size(), 10);

        webDriver.quit();
    }


}
