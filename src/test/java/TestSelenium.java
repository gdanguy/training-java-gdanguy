import model.Pages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import service.dao.DAOService;
import service.dao.DAOServiceImpl;
import model.computer.Computer;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestSelenium{
    private WebDriver driver;
    private String baseUrl = "http://localhost:8080/dashboard";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void getStrated() {
        System.setProperty("webdriver.chrome.driver", "/home/ebiz/projet2/training-java-gdanguy/src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Test
    public void testDashboard() throws InterruptedException{
        driver.get(baseUrl);

        //Test number of computer
        String countWeb = driver.findElement(By.xpath("//*[@id=\"homeTitle\"]")).getText();
        DAOService service = new DAOServiceImpl();
        int countDAO = service.countComputers();
        assertEquals("Correct number display", (countDAO + " Computers found"),countWeb);

        //Test number of computer in list
        int countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", Pages.PAGE_SIZE, countComputer);
        driver.findElement(By.xpath("//*[@id=\"pagination\"]/a[2]")).click();
        countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", 50, countComputer);
        driver.findElement(By.xpath("//*[@id=\"pagination\"]/a[3]")).click();
        countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", 100, countComputer);

        //pagination
        //TODO

        driver.close();
    }

    @Test
    public void testCreateComputer() throws InterruptedException {
        driver.get(baseUrl);

        DAOService service = new DAOServiceImpl();
        int countComputer = service.countComputers();

        //go to add page
        driver.findElement(By.xpath("//*[@id=\"addComputer\"]")).click();

        //Add a computer
        WebElement name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.sendKeys(TestDAOService.NAME_COMPUTER_TEST);
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/div/form/div/input")).click();

        //Verif in DataBase
        assertTrue("Check Computer added in DataBase", service.countComputers() == countComputer + 1);
        service.deleteLastComputer();

        driver.close();
    }

    @Test
    public void testEditComputer() throws InterruptedException {
        driver.get(baseUrl);

        DAOService service= new DAOServiceImpl();
        Computer c = service.getFirstComputer();

        //go to add page
        driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td[2]/a")).click();

        //update name a computer
        WebElement name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.sendKeys(TestDAOService.NAME_COMPUTER_TEST_2);
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/div/form/div/input")).click();

        WebElement nameList = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td[2]/a"));
        assertEquals("Verif the name was changed", nameList.getText(), TestDAOService.NAME_COMPUTER_TEST_2);

        //return set name previous status
        nameList.click();
        name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.sendKeys(c.getName());
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/div/form/div/input")).click();

        nameList = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td[2]/a"));
        assertEquals("Verif the name was changed", nameList.getText(), TestDAOService.NAME_COMPUTER_TEST_2);

        driver.close();


    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}
