package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasisSeite {
    protected final WebDriver driver;
    private final WebDriverWait warten;

    protected BasisSeite(WebDriver driver) {
        this.driver = driver;
        this.warten = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    protected WebElement sichtbar(By locator) {
        return warten.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement klickbar(By locator) {
        return warten.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void klicken(By locator) {
        klicken(klickbar(locator));
    }

    protected void klicken(WebElement element) {
        WebElement ziel = element;

        for (int versuch = 1; versuch <= 2; versuch++) {
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                        ziel
                );

                wartenKurz(150);
                warten.until(ExpectedConditions.elementToBeClickable(ziel)).click();
                pause();
                return;
            } catch (ElementClickInterceptedException exception) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        ziel
                );
                pause();
                return;
            } catch (StaleElementReferenceException exception) {
                if (versuch == 2) {
                    throw exception;
                }
                wartenKurz(150);
            }
        }
    }

    protected void eingeben(By locator, String wert) {
        WebElement element = sichtbar(locator);
        element.clear();
        element.sendKeys(wert);
        pause();
    }

    protected String text(By locator) {
        return sichtbar(locator).getText();
    }

    protected List<WebElement> alle(By locator) {
        return driver.findElements(locator);
    }

    protected void pause() {
        long slowMo = Long.parseLong(System.getProperty("slowMo", "0"));
        wartenKurz(slowMo);
    }

    private void wartenKurz(long millisekunden) {
        if (millisekunden <= 0) {
            return;
        }

        try {
            Thread.sleep(millisekunden);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
