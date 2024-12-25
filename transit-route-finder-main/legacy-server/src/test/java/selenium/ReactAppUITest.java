package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ReactAppUITest {
  WebDriver driver;
  WebDriverWait wait;
  WebElement startingPoint;
  WebElement destination;
  WebElement directionsButton;

  @BeforeMethod
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.get("http://localhost:3000");
    System.out.println("Driver Loaded Successfully");

    // Initialize reusable elements
    startingPoint = driver.findElement(By.className("startingbox"));
    destination = driver.findElement(By.className("destbox"));
    directionsButton = driver.findElement(By.className("submitbutton"));
  }

  
@Test
public void testVisibilityOfElements() {
    System.out.println("Testing Visibility of Essential UI Elements...");
    WebElement mapImage = driver.findElement(By.className("mapImage"));
    Assert.assertTrue(mapImage.isDisplayed(), "Map image is not visible!");
    Assert.assertTrue(startingPoint.isDisplayed(), "Starting Point input field is not visible!");
    Assert.assertTrue(destination.isDisplayed(), "Destination input field is not visible!");
    Assert.assertTrue(directionsButton.isDisplayed(), "Directions button is not visible!");
    System.out.println("All essential UI elements are visible.\n");
}
@Test
public void testInputFields() {
    System.out.println("Testing Input Fields");
    startingPoint.sendKeys("A");
    destination.sendKeys("B");
    Assert.assertEquals(startingPoint.getAttribute("value"), "A", "Starting Point input failed!");
    Assert.assertEquals(destination.getAttribute("value"), "B", "Destination input failed!");
    System.out.println("Input fields validated.");
}

  @Test
public void testComponentRendering() {
    System.out.println("Testing Steps and Components Rendering...");
    startingPoint.sendKeys("archway");
    destination.sendKeys("green park");
    directionsButton.click();
    WebElement startPlace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("start")));
    WebElement lineNumber = driver.findElement(By.className("lineNumber"));
    WebElement endPlace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("end")));
    Assert.assertTrue(startPlace.getText().contains(startingPoint.getText()), "StartPlace not rendered correctly!");
    Assert.assertFalse(lineNumber.getText().isEmpty(), "Line number is missing!");
    Assert.assertTrue(endPlace.getText().contains(destination.getText()), "EndPlace not rendered correctly!");
    System.out.println("Components rendered correctly.");
}

@Test
public void testEmptyStarting() {
    System.out.println("Testing for empty starting input...");
    directionsButton.click();
    WebElement totalTime = driver.findElement(By.className("Totaltime"));
    Assert.assertEquals(totalTime.getText(), "You didn't enter a starting point");
    System.out.println("Console log 'You didn't enter a starting point' validated successfully.\n");
}

@Test
public void testEmptyDestination() {
    System.out.println("Testing for empty destination input...");
    startingPoint.sendKeys("archway");
    directionsButton.click();
    WebElement totalTime = driver.findElement(By.className("Totaltime"));
    Assert.assertEquals(totalTime.getText(), "You didn't enter a destination point");
    System.out.println("Console log 'You didn't enter a destination point' validated successfully.\n");
}



@Test
public void testInvalidStartingPoint() {
    System.out.println("Testing Invalid Starting Point...");
    startingPoint.sendKeys("InvalidStart");
    destination.sendKeys("A");
    directionsButton.click();
    WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Totaltime")));
    Assert.assertTrue(errorMessage.getText().contains("There is no such a starting point in the map."),
        "Invalid starting point error not displayed!");
    System.out.println("Error handling validated.\n");
}

@Test
public void testInvalidDestination() {
    System.out.println("Testing Invalid Destination...");
    startingPoint.sendKeys("archway");
    destination.sendKeys("InvalidDestination");
    directionsButton.click();
    WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Totaltime")));
    Assert.assertTrue(errorMessage.getText().contains("There is no such a destination in the map."),
        "Invalid destination error not displayed!");
    System.out.println("Error handling validated.\n");
}


  @AfterMethod
  public void tearDown() {
    System.out.println("Test execution completed. Browser closed.");
    driver.quit();
  }
}
