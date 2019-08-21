package agro.assignment;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;

public class gitSearch {
	public String baseUrl="https://www.github.com/",username,password,lang="Java";
	public  WebDriver driver;	
	public WebDriver getDriver() {
		return this.driver;
	}
	public void setDriver(WebDriver driver) {
		 this.driver = driver;
	}
@Test(dependsOnMethods={"agro.assignment.Setup.getInput"})
  public void SearchandPlaceOrder() {
	  driver.get(baseUrl);
	  WebDriverWait explicitwait=new  WebDriverWait(driver,30);
	  driver.findElement(By.xpath("//a[@class='HeaderMenu-link no-underline mr-3']")).click();
	  explicitwait.until(
              ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login_field']")));
	  driver.findElement(By.xpath("//input[@id='login_field']")).sendKeys(username);
	  driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password); 
	  driver.findElement(By.xpath("//input[@id='password']")).submit();
	  
	  explicitwait.until(
              ExpectedConditions.elementToBeClickable(By.xpath("//nav/a[contains(text(),'Explore')]")));
	  driver.findElement(By.xpath("//nav/a[contains(text(),'Explore')]")).click();
	  driver.findElement(By.xpath("//a[contains(text(),'Trending')][1]")).click();
	 
	  driver.findElement(By.xpath("//summary[@class='btn-link select-menu-button'][1]")).click();
	  driver.findElement(By.xpath("//input[@id='text-filter-field']")).sendKeys(lang);	
	  driver.findElement(By.xpath("//a[@class='select-menu-item ' ]//span[text()='"+lang+"']")).click();
	  List<WebElement> List =driver.findElements(By.xpath("//h1[@class='h3 lh-condensed']/a[1]"));
	  List<String> hyperlink=new ArrayList<String>();
	  for(int i=0;i<5;i++)
		  hyperlink.add(List.get(i).getText());
	  
    for(int i=0;i<5;i++)
	  {String expected ="Cannot fork because you own this repository and are not a member of any organizations.";
		  System.out.println("Reported at "+i+" "+hyperlink.get(i));
	      driver.get(baseUrl+hyperlink.get(i).replace(" ", ""));
	      driver.navigate().refresh();
	      explicitwait.until(
	              ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-sm btn-with-count']")));
	      
	      
	      driver.findElement(By.xpath("//button[@class='btn btn-sm btn-with-count']")).click();
	      Assert.assertEquals(driver.findElement(By.xpath("//span[@class='btn btn-sm btn-with-count disabled tooltipped tooltipped-sw']")).
	    		  getAttribute("aria-label").contains(expected) /*Expected value*/, true /*Actual Value*/, "Sucesssful");
	      
	      
	      explicitwait.until(
	              ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-sm btn-with-count js-toggler-target'])[2]")));
	      driver.findElement(By.xpath("(//button[@class='btn btn-sm btn-with-count js-toggler-target'])[2]")).click();
	      Assert.assertEquals(driver.findElement(By.xpath("(//button[@class='btn btn-sm btn-with-count js-toggler-target'])[2]")).
	    		  getAttribute("aria-label").contains("Unstar") /*Expected value*/, true /*Actual Value*/, "Sucesssful");
	  
	  }
    
  
	    
  }

 
@BeforeClass
  public void beforeTest() throws Exception {
	Constants obj=new Constants();
	System.out.println("in gitSearch : "+obj.repoName+" "+obj.username+" "+obj.password);
	  username=obj.username;
	  password=obj.password;
  	if(System.getProperty("os.name").toLowerCase().contains("nux")
  			||System.getProperty("os.name").toLowerCase().contains("nix")||
  			System.getProperty("os.name").toLowerCase().contains("aix"))
  	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+java.io.File.separatorChar+"Driver"+java.io.File.separatorChar+"chromedriver" );
      if(System.getProperty("os.name").toLowerCase().contains("win"))
      	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+java.io.File.separatorChar+"Driver"+java.io.File.separatorChar+"chromedriver.exe" );
  	ChromeOptions options = new ChromeOptions();
      options.addArguments("chrome.switches","--disable-extensions");
  	setDriver(new ChromeDriver());
  	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
  	driver.manage().window().maximize();
  }
  
  @AfterSuite
  public void afterTest() {
  	  driver.quit();
  }

}
