import model.Pages;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import model.computer.Computer;
import service.ComputerService;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestSelenium{
    private WebDriver driver;
    private String baseUrl = "http://localhost:8080/dashboard";
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
    @Ignore
    public void testDashboard() throws InterruptedException{
        driver.get(baseUrl);

        //Test number of computer in list
        int countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", Pages.PAGE_SIZE, countComputer);
        click("//*[@id=\"pagination\"]/a[2]");
        countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", 50, countComputer);
        click("//*[@id=\"pagination\"]/a[3]");
        countComputer = driver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr")).size();
        assertEquals("Correct number of cumputer list display", 100, countComputer);

        //pagination
        //boucle cherche un ordinateur
        //tant que trouve pas clique sur '>>' dans la pagination
        ComputerService service = ComputerService.getInstance();
        Computer computer = service.list(2).getListPage().get(0);
        search(computer.getId());

        driver.close();
    }

    @Test
    @Ignore
    public void testCreateDeleteComputer() throws InterruptedException {

        driver.get(baseUrl);

        int countComputer = countComputer();

        //go to add page
        clickAdd();

        //Add a computer
        WebElement name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.sendKeys(TestDAOService.NAME_COMPUTER_TEST);
        click("//*[@id=\"main\"]/div/div/div/form/div/input");

        //Test if Computer was added
        int countComputerAfter = countComputer();
        assertTrue("Check Computer added in DataBase", countComputerAfter == countComputer + 1);

        //DELETE
        deleteLast();
        isAlertPresent();
        closeAlertAndGetItsText();
        countComputerAfter = countComputer();
        assertTrue("Check Computer added in DataBase", countComputerAfter == countComputer);

        driver.close();
    }

    @Test
    @Ignore
    public void testEditComputer() throws InterruptedException {
        driver.get(baseUrl);

        //go to add page
        click("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td[2]/a");

        //get the current name of the computer
        String currentName = driver.findElement(By.xpath("//*[@id=\"computerName\"]")).getAttribute("value");

        //update name a computer
        WebElement name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.clear();
        name.sendKeys(TestDAOService.NAME_COMPUTER_TEST_2);
        click("//*[@id=\"main\"]/div/div/div/form/div/input");

        WebElement nameList = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td/a"));
        assertEquals("Test the name was changed", nameList.getText(), TestDAOService.NAME_COMPUTER_TEST_2);

        //return set name previous status
        nameList.click();
        name = driver.findElement(By.xpath("//*[@id=\"computerName\"]"));
        name.clear();
        name.sendKeys(currentName);
        click("//*[@id=\"main\"]/div/div/div/form/div/input");

        nameList = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td/a"));
        assertEquals("Test the name was changed", nameList.getText(), currentName);

        driver.close();
    }


    //Fonction Utilitaire

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement search(int id) throws NoSuchElementException {
        boolean find;
        int page = 1;
        click("//*[@id=\"pagination\"]/a[1]");
        do {
            find = isElementPresent(By.xpath("//*[@id=\"main\"]/div/table/tbody/tr/td/a[@href=\"/editComputer?id=" + id + "\"]"));
            if (!find) {
                //Fail when no more pages
                page = goNextPage(page);
            }
        } while(!find);
        return driver.findElement(By.xpath("//*[@id=\"main\"]/div/table/tbody/tr/td/a[@href=\"/editComputer?id=" + id + "\"]"));
    }

    private int goNextPage(int newtPage) throws NoSuchElementException {
        click("/html/body/footer/div/ul/li/a[@href=\"/dashboard?currentPage=" + newtPage + "&sizePages=10\"]");
        return ++newtPage;
    }

    private void goLastPage() {
        try {
            int page = 1;
            while(true) {
                page = goNextPage(page);
            }
        } catch (NoSuchElementException e) {
        }
    }

    private int countComputer() {
        return Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"homeTitle\"]")).getText().split(" ")[0]);
    }

    private void clickAdd() throws NoSuchElementException {
        click("//*[@id=\"addComputer\"]");
    }

    private void clickEdit() throws NoSuchElementException {
        click("//*[@id=\"editComputer\"]");
    }

    private void clickDelete() throws NoSuchElementException {
        click("//*[@id=\"deleteSelected\"]/i");
    }

    private void click(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    private void deleteLast() throws NoSuchElementException {
        goLastPage();
        clickEdit();
        //Click on the edit of last of the page
        boolean click = false;
        int line = 10;
        while(!click) {
            try {
                click("//*[@id=\"main\"]/div[2]/table/tbody/tr[" + line + "]/td[1]/input");
                click = true;
            } catch (NoSuchElementException e) {
                //if the page if empty
                if (line <= 0) {
                    throw e;
                }
                line--;
            }
        }
        clickDelete();
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
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
