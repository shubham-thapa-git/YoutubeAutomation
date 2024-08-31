package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     

     public WebElement findRightArrow(String title,WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement rightArrow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='title' and text()=\""+title+"\"]//ancestor::div[@id='dismissible']//child::div[@id='right-arrow']//button[@aria-label='Next']")));
        
        
        if(rightArrow.isDisplayed()){
            return rightArrow;
        }
        else{
            return null;
        }
    }

    public void search(WebElement searchBar,WebElement searchButton,String text, WebDriver driver){
        searchBar.sendKeys(text);
        searchButton.click();
    }
}
