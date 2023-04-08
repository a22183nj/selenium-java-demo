import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    private static ChromeDriver driver;
//    private static FirefoxDriver driver;
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
//        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
//        driver = new FirefoxDriver();
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
        //hover to main menu then click submenu
//        action.moveToElement(developers).perform();
//        support.click();

        //hover to main menu then hover to submenu then click
        action.moveToElement(developers);
        action.moveToElement(support);
        action.click().build().perform();
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

    @Test
    public void file_upload() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://www.monsterindia.com/seeker/registration");

        WebElement element = driver.findElement(By.cssSelector("input[type='file']"));
        File file = new File("resources/sample.pdf");
        element.sendKeys(file.getAbsolutePath());
        wait(5000);
    }

    @Test
    public void drag_drop() {
        System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement from = driver.findElement(By.id("column-a"));
        WebElement to = driver.findElement(By.id("column-b"));

        Actions action = new Actions(driver);
        //option 1 not working
//        action.dragAndDrop(from, to).perform();

        //option 2 not working
//        action.clickAndHold(from)
//                .moveToElement(to)
//                .release(to).build().perform();

        //option 3 not working
//        int x = to.getLocation().x;
//        int y = to.getLocation().y;
//        action.moveToElement(from)
//                .pause(Duration.ofSeconds(1))
//                .clickAndHold(from)
//                .pause(Duration.ofSeconds(1))
//                .moveByOffset(x, y)
//                .moveToElement(to)
//                .moveByOffset(x,y)
//                .pause(Duration.ofSeconds(1))
//                .release().build().perform();

        //https://stackoverflow.com/questions/39436870/why-drag-and-drop-is-not-working-in-selenium-webdriver
        js.executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
                + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
                + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
                + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
                + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                + "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
                + "var dropEvent = createEvent('drop');\n"
                + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                + "var dragEndEvent = createEvent('dragend');\n"
                + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                + "var source = arguments[0];\n" + "var destination = arguments[1];\n"
                + "simulateHTML5DragAndDrop(source,destination);", from, to);
        wait(5000);
    }

    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }
}
