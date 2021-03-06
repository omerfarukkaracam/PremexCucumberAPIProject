package com.tasks.pages;

import com.tasks.utilities.BrowserUtils;
import com.tasks.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AboutPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'About')]")
    public WebElement aboutModule;

//    @FindBy(xpath = "//a[contains(text(),'Locations')]")
//    public WebElement locationsSubModule;

    @FindBy(css = ".location__marker")
    public List<WebElement> locations;

    public void clickSubModule(String subModule) {
        String locator = "//a[contains(text(),'" + subModule + "')]";
        WebElement subModuleElement = Driver.get().findElement(By.xpath(locator));
        BrowserUtils.clickWithJS(subModuleElement);
    }

    public String getOfficeLocation(String officeLocation) throws IOException {
        //Actions actions = new Actions(Driver.get()); //kullanilmamais
        String locationAdres = null;
        WebElement addressElement = null;
        for (int i = 0; i < locations.size(); i++) {
            BrowserUtils.clickWithJS(locations.get(i)); // tek tek hepsine click yapiyor.
            String locator = "//li[@id='desc0_" + i + "']/h3";
            WebElement locationName = Driver.get().findElement(By.xpath(locator));
            String locatorAddress = "desc0_" + i;
            addressElement = Driver.get().findElement(By.id(locatorAddress));
            BrowserUtils.waitForVisibility(locationName, 10);
            if (locationName.getText().contains(officeLocation)) {
                ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(false);", addressElement); //true altini gosteriyor, false ustunue gidiyor
                BrowserUtils.waitFor(3);
                File scrFile = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.FILE);
                String projectPath = System.getProperty("user.dir");
                String relativePath = "/target/capture/";
                String filePath = projectPath + relativePath;
                FileUtils.copyFile(scrFile, new File(filePath + "officelocation.png"));

                locationAdres = locationName.getText();
                break;
            }
        }
        System.out.println("address of location = " + addressElement.getText());
        return locationAdres;
    }

    public String getOffice(String str) throws IOException {

        String locationAdress = null;
        for (int i = 0; i < locations.size(); i++) {
            BrowserUtils.clickWithJS(locations.get(i));
            //String locator = "//li[@id='desc0_" + i + "']/h3";

           // WebElement locationName = Driver.get().findElement(By.xpath(locator));
            String locatorAddress = "desc0_" + i;
            WebElement adressElement = Driver.get().findElement(By.xpath(locatorAddress));
            BrowserUtils.waitForVisibility(adressElement, 10);

            if (adressElement.getText().contains(str)) {

                ((JavascriptExecutor) Driver.get()).executeAsyncScript("arguments[0].scrollIntoView(false);", adressElement);
                BrowserUtils.waitFor(3);
                File scrFile = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.FILE);
                String projPath = "user/dir";
                String relPath = "target/capture";
                String filePath = projPath + relPath;
                FileUtils.copyFile(scrFile, new File(filePath + "officelocation.png"));

                locationAdress = adressElement.getText();

            }
        }
        return locationAdress;
    }


}
