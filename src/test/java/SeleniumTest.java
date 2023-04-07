import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    private static ChromeDriver driver;
    private static JavascriptExecutor js;

    public void wait(int msec){
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            // Handle the exception
        }
    }

    @BeforeClass
    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void scroll_down(){
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://www.browserstack.com/guide/selenium-scroll-tutorial");
        WebElement element = driver.findElement(By.linkText("Try BrowserStack for Free"));
        //over scroll sometimes and element is at the top and invisible
//        js.executeScript("arguments[0].scrollIntoView();", element);

        //scroll to mid of view
        int yOffset = (int) (element.getSize().getHeight() / 2);
        String script = "window.scrollTo(0,arguments[0].getBoundingClientRect().top - window.innerHeight/2 + " + yOffset + ")";
        js.executeScript(script, element);
        wait(5000);
    }

    @Test
    public void double_right_click() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://demoqa.com/buttons");

        //double click
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.id("doubleClickBtn"));
        action.doubleClick(element).perform();
        wait(1000);

        //right click
        action = new Actions(driver);
        element = driver.findElement(By.id("rightClickBtn"));
        action.contextClick(element).perform();
        wait(5000);
    }

    @Test
    public void hover_click() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://www.browserstack.com/");

        WebElement developers = driver.findElement(By.id("developers-menu-toggle"));
        WebElement support = driver.findElement(By.cssSelector("#developers-menu-dropdown > li:nth-child(6) > a"));
        Actions action = new Actions(driver);
        action.moveToElement(developers).perform();
        support.click();
        wait(5000);
    }

    @Test
    public void drop_down() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://demoqa.com/select-menu");

        //https://www.toolsqa.com/selenium-webdriver/dropdown-in-selenium/
        //select by index
        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByIndex(4);
        wait(1000);

        //select by visible text
        select.selectByVisibleText("Magenta");
        wait(1000);

        //select by value
        select.selectByValue("6");
        wait(5000);
    }

    @Test
    public void multi_select() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://demoqa.com/select-menu");

        //scroll to select view
        WebElement element = driver.findElement(By.id("cars"));
        int yOffset = (int) (element.getSize().getHeight() / 2);
        String script = "window.scrollTo(0,arguments[0].getBoundingClientRect().top - window.innerHeight/2 + " + yOffset + ")";
        js.executeScript(script, element);
        wait(1000);

        Select select = new Select(driver.findElement(By.id("cars")));
        if(select.isMultiple()){
            //select by index - Opel
            select.selectByIndex(2);
            wait(1000);

            //select by value
            select.selectByValue("saab");
            wait(1000);

            //select by text
            select.selectByVisibleText("Audi");
            wait(1000);

            //deselect by index - Audi
            select.deselectByIndex(3);
            wait(1000);

            //deselect by visible text
            select.deselectByVisibleText("Opel");
            wait(1000);

            //deselect all
            select.deselectAll();
            wait(5000);
        }
    }

    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }
}
