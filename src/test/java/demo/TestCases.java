package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.Wrapper;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

         @Test
         public void testCase01(){
                System.out.println("Start Test Case: testCase01");
           try {
                driver.get("https://www.youtube.com");
                Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");
                WebElement about = driver.findElement(By.xpath("//a[text()='About']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", about);
                about.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='lb-font-display-1 lb-font-weight-700 lb-font-color-text-primary lb-font--no-crop']")));
                String titleText = title.getText();
                WebElement heading1 = driver.findElement(By.xpath("//p[@class='lb-font-display-3 lb-font-color-text-primary']"));
                String text = heading1.getText();
                System.out.println(titleText);
                System.out.println(text);
              } catch (Exception e) {
                e.printStackTrace();
             }

                System.out.println("End Test Case: testCase01");
                

         }

         @Test
         public void testCase02() throws InterruptedException{
                System.out.println("Start Test Case: testCase02");
                driver.get("https://www.youtube.com");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement movies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='Movies']")));
                movies.click();
               
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='title']")));
                Wrappers wr = new Wrappers();
                WebElement rightArrow = wr.findRightArrow("Top selling", driver);
                while(rightArrow.isDisplayed()){
                
                        rightArrow.click();
                }
                List<WebElement> movieBlocks = driver.findElements(By.xpath("//span[@id='title' and text()='Top selling']//ancestor::div[@id='contents']//div[@id='items']/ytd-grid-movie-renderer"));
                
                Thread.sleep(3000);
                WebElement lastMovieThumb = movieBlocks.get(movieBlocks.size()-1);
                WebElement rating = lastMovieThumb.findElement(By.xpath("(.//p[@class='style-scope ytd-badge-supported-renderer'])[2]"));
                String rateString = rating.getText();
                SoftAssert sft = new SoftAssert();
                sft.assertEquals(rateString, "A");
                System.out.println(rateString);
                WebElement categoryElem = lastMovieThumb.findElement(By.xpath(".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
                String category = categoryElem.getText().replaceAll("[^a-zA-Z]","");
                sft.assertTrue(!category.isEmpty());
                System.out.println(category);
                System.out.println("End Test Case: testCase02");
         }

         @Test
         public void testCase03(){
                System.out.println("Start Test Case: testCase03");
                driver.get("https://www.youtube.com");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement musicElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='Music']")));
                musicElement.click();
                Wrappers wr= new Wrappers();
                WebElement rightArrow = wr.findRightArrow("India's Biggest Hits", driver);
                while(rightArrow.isDisplayed()){
                        rightArrow.click();
                }
                List<WebElement> tracks = driver.findElements(By.xpath("//span[@id='title' and text()=\"India's Biggest Hits\"]//ancestor::div[@id='dismissible']//div[@id='items']/ytd-compact-station-renderer"));

                WebElement lastTrackCollection = tracks.get(tracks.size()-1);
                String trackCount = lastTrackCollection.findElement(By.xpath(".//p[@id='video-count-text']")).getText().replaceAll("[^0-9]", "");
                SoftAssert sft = new SoftAssert();
                sft.assertEquals(trackCount, "50");
                System.out.println(trackCount);
                System.out.println("End Test Case: testCase03");
         }

         @Test
         public void testCase04(){
                System.out.println("Start Test Case: testCase04");
                driver.get("https://www.youtube.com");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement musicElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='News']")));
                musicElement.click();
               
                List<WebElement> news = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@id='title' and text()='Latest news posts']//ancestor::div[@id='dismissible']//ytd-rich-item-renderer")));
                       int likes=0;
                     int count=0;
                     while(count<=2){
                        WebElement newsElement1 = news.get(count);
                        String header1  = newsElement1.findElement(By.xpath(".//span[@class='style-scope ytd-post-renderer']")).getText(); 
                        System.out.println("Header: "+header1);
                        String body1 = newsElement1.findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']")).getText();
                        System.out.println("Body: "+body1);
                        String likesString = newsElement1.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText();
                        if(likesString==null || likesString == ""){
                                likes = likes+0;
                              
                        }
                        else if(likesString.contains("K")){
                                int index = likesString.indexOf("K");
                                String numberCount = likesString.substring(0, index-1);
                                int actualCount = Integer.parseInt(numberCount)*1000;
                                likes = likes+actualCount;
                                actualCount=0;
                        }
                        else if(likesString.contains("M")){
                                int index = likesString.indexOf("M");
                                String numberCount = likesString.substring(0, index-1);
                                int actualCount = Integer.parseInt(numberCount)*1000000;
                                likes = likes+actualCount;
                                actualCount=0;
                        }
                        else if(likesString!=null){
                        int likescount = Integer.parseInt(likesString);
                        likes = likes+likescount;
                        likescount=0;
                        }
                        count++;
                       
         }
         System.out.println("Total likes count = " +likes);
                
                System.out.println("End Test Case: testCase04");
         }


         @Test(dataProvider="excelData",dataProviderClass = ExcelDataProvider.class)
         public void testCase05(String data){
                System.out.println("Start Test Case: testCase05");
                driver.get("https://www.youtube.com");

                WebElement searchBar = driver.findElement(By.xpath("//input[@id='search']"));
                WebElement searchButton = driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
                
                Wrappers wr = new Wrappers();
                wr.search(searchBar, searchButton, data, driver);
                
                List<WebElement> views = driver.findElements(By.xpath("//div[contains(@class,'text-wrapper style-scope ytd-video-renderer')]//span[contains(@class,'ytd-video-meta-block')][1]"));

                JavascriptExecutor js = (JavascriptExecutor) driver;
                int viewCount =0;
                for(WebElement elem: views){
                        while(viewCount<=10000000){
                        js.executeScript("arguments[0].scrollIntoView();", elem);
                         String view=elem.getText();
                         if(view.contains("M")){
                                int index = view.indexOf("K");
                                String numberCount = view.substring(0, index-1);
                                int actualCount = Integer.parseInt(numberCount)*1000000;
                                viewCount = viewCount+actualCount;
                                actualCount=0;
                        }
                         else if(view.contains("K")){
                                int index = view.indexOf("K");
                                String numberCount = view.substring(0, index-1);
                                int actualCount = Integer.parseInt(numberCount)*1000;
                                viewCount = viewCount+actualCount;
                                actualCount=0;
                        }
                }
                 break;        
                }
                
                wr.search(searchBar, searchButton, data, driver);
                List<WebElement> views2 = driver.findElements(By.xpath("//div[contains(@class,'text-wrapper style-scope ytd-video-renderer')]//span[contains(@class,'ytd-video-meta-block')][1]"));

                viewCount =0;
                for(WebElement elem: views2){
                        while(viewCount<=10000000){
                                js.executeScript("arguments[0].scrollIntoView();", elem);
                                 String view=elem.getText();
                                 if(view.contains("M")){
                                        int index = view.indexOf("K");
                                        String numberCount = view.substring(0, index-1);
                                        int actualCount = Integer.parseInt(numberCount)*1000000;
                                        viewCount = viewCount+actualCount;
                                        actualCount=0;
                                }
                                 else if(view.contains("K")){
                                        int index = view.indexOf("K");
                                        String numberCount = view.substring(0, index-1);
                                        int actualCount = Integer.parseInt(numberCount)*1000;
                                        viewCount = viewCount+actualCount;
                                        actualCount=0;
                                }
                        }
                         break;        
                         
                }
                

                wr.search(searchBar, searchButton, data, driver);
                viewCount =0;
                List<WebElement> views3 = driver.findElements(By.xpath("//div[contains(@class,'text-wrapper style-scope ytd-video-renderer')]//span[contains(@class,'ytd-video-meta-block')][1]"));
                for(WebElement elem: views3){
                        while(viewCount<=10000000){
                                js.executeScript("arguments[0].scrollIntoView();", elem);
                                 String view=elem.getText();
                                 if(view.contains("M")){
                                        int index = view.indexOf("K");
                                        String numberCount = view.substring(0, index-1);
                                        int actualCount = Integer.parseInt(numberCount)*1000000;
                                        viewCount = viewCount+actualCount;
                                        actualCount=0;
                                }
                                 else if(view.contains("K")){
                                        int index = view.indexOf("K");
                                        String numberCount = view.substring(0, index-1);
                                        int actualCount = Integer.parseInt(numberCount)*1000;
                                        viewCount = viewCount+actualCount;
                                        actualCount=0;
                                }
                        }
                         break;        
                }
                




                System.out.println("End Test Case: testCase05");
         }
        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}