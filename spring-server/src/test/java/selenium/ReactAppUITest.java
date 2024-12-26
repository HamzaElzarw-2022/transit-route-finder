package selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReactAppUITest {
   WebDriver driver;
   WebDriverWait wait;
   WebElement startingPoint;
   WebElement destination;
   WebElement directionsButton;

   public ReactAppUITest() {
   }

   @BeforeMethod
   public void setUp() {
      System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
      this.driver = new ChromeDriver();
      this.driver.manage().window().maximize();
      this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10L));
      this.driver.get("http://localhost:3000");
      System.out.println("Driver Loaded Successfully");
      this.startingPoint = this.driver.findElement(By.className("startingbox"));
      this.destination = this.driver.findElement(By.className("destbox"));
      this.directionsButton = this.driver.findElement(By.className("submitbutton"));
   }

   @Test
   public void testVisibilityOfElements() {
      System.out.println("Testing Visibility of Essential UI Elements...");
      WebElement mapImage = this.driver.findElement(By.className("mapImage"));
      Assert.assertTrue(mapImage.isDisplayed(), "Map image is not visible!");
      Assert.assertTrue(this.startingPoint.isDisplayed(), "Starting Point input field is not visible!");
      Assert.assertTrue(this.destination.isDisplayed(), "Destination input field is not visible!");
      Assert.assertTrue(this.directionsButton.isDisplayed(), "Directions button is not visible!");
      System.out.println("All essential UI elements are visible.\n");
   }

   @Test
   public void testInputFields() {
      System.out.println("Testing Input Fields");
      this.startingPoint.sendKeys(new CharSequence[]{"A"});
      this.destination.sendKeys(new CharSequence[]{"B"});
      Assert.assertEquals(this.startingPoint.getAttribute("value"), "A", "Starting Point input failed!");
      Assert.assertEquals(this.destination.getAttribute("value"), "B", "Destination input failed!");
      System.out.println("Input fields validated.");
   }

   @Test
   public void testComponentRendering() {
      System.out.println("Testing Steps and Components Rendering...");
      this.startingPoint.sendKeys(new CharSequence[]{"archway"});
      this.destination.sendKeys(new CharSequence[]{"green park"});
      this.directionsButton.click();
      WebElement startPlace = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("start")));
      WebElement lineNumber = this.driver.findElement(By.className("lineNumber"));
      WebElement endPlace = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("end")));
      Assert.assertTrue(startPlace.getText().contains(this.startingPoint.getText()), "StartPlace not rendered correctly!");
      Assert.assertFalse(lineNumber.getText().isEmpty(), "Line number is missing!");
      Assert.assertTrue(endPlace.getText().contains(this.destination.getText()), "EndPlace not rendered correctly!");
      System.out.println("Components rendered correctly.");
   }

   @Test
   public void testEmptyStarting() {
      System.out.println("Testing for empty starting input...");
      this.directionsButton.click();
      WebElement totalTime = this.driver.findElement(By.className("Totaltime"));
      Assert.assertEquals(totalTime.getText(), "You didn't enter a starting point");
      System.out.println("Console log 'You didn't enter a starting point' validated successfully.\n");
   }

   @Test
   public void testEmptyDestination() {
      System.out.println("Testing for empty destination input...");
      this.startingPoint.sendKeys(new CharSequence[]{"archway"});
      this.directionsButton.click();
      WebElement totalTime = this.driver.findElement(By.className("Totaltime"));
      Assert.assertEquals(totalTime.getText(), "You didn't enter a destination point");
      System.out.println("Console log 'You didn't enter a destination point' validated successfully.\n");
   }

   @Test
   public void testInvalidStartingPoint() {
      System.out.println("Testing Invalid Starting Point...");
      this.startingPoint.sendKeys(new CharSequence[]{"InvalidStart"});
      this.destination.sendKeys(new CharSequence[]{"A"});
      this.directionsButton.click();
      WebElement errorMessage = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Totaltime")));
      Assert.assertTrue(errorMessage.getText().contains("There is no such a starting point in the map."), "Invalid starting point error not displayed!");
      System.out.println("Error handling validated.\n");
   }

   @Test
   public void testInvalidDestination() {
      System.out.println("Testing Invalid Destination...");
      this.startingPoint.sendKeys(new CharSequence[]{"archway"});
      this.destination.sendKeys(new CharSequence[]{"InvalidDestination"});
      this.directionsButton.click();
      WebElement errorMessage = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Totaltime")));
      Assert.assertTrue(errorMessage.getText().contains("There is no such a destination in the map."), "Invalid destination error not displayed!");
      System.out.println("Error handling validated.\n");
   }

   @AfterMethod
   public void tearDown() {
      System.out.println("Test execution completed. Browser closed.");
      this.driver.quit();
   }
}
