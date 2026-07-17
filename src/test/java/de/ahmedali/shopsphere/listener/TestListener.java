package de.ahmedali.shopsphere.listener;

import de.ahmedali.shopsphere.core.DriverFabrik;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestListener implements ITestListener, ISuiteListener {
    private final AtomicInteger bestanden = new AtomicInteger();
    private final AtomicInteger fehlgeschlagen = new AtomicInteger();
    private final AtomicInteger uebersprungen = new AtomicInteger();
    private final List<String> fehlerliste = new ArrayList<>();

    @Override
    public void onTestSuccess(ITestResult result) {
        bestanden.incrementAndGet();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        fehlgeschlagen.incrementAndGet();
        String methode = result.getMethod().getMethodName();
        fehlerliste.add(methode + bekannteFehlerId(methode));
        screenshotSpeichern(methode);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        uebersprungen.incrementAndGet();
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            Files.createDirectories(Path.of("reports"));
            StringBuilder bericht = new StringBuilder();
            bericht.append("# Testlauf-Zusammenfassung\\n\\n")
                    .append("- Suite: ").append(suite.getName()).append("\\n")
                    .append("- Zeitpunkt: ").append(LocalDateTime.now()).append("\\n")
                    .append("- Bestanden: ").append(bestanden.get()).append("\\n")
                    .append("- Fehlgeschlagen: ").append(fehlgeschlagen.get()).append("\\n")
                    .append("- Übersprungen: ").append(uebersprungen.get()).append("\\n\\n")
                    .append("## Fehlgeschlagene Tests\\n");
            if (fehlerliste.isEmpty()) {
                bericht.append("Keine.\\n");
            } else {
                for (String fehler : fehlerliste) bericht.append("- ").append(fehler).append("\\n");
            }
            Files.writeString(Path.of("reports", "Testlauf_Zusammenfassung.md"), bericht.toString());
        } catch (Exception ignoriert) {
        }
    }

    private void screenshotSpeichern(String testname) {
        WebDriver driver = DriverFabrik.holen();
        if (!(driver instanceof TakesScreenshot)) return;
        try {
            Files.createDirectories(Path.of("screenshots"));
            String zeit = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            File quelle = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(quelle.toPath(), Path.of("screenshots", testname + "-" + zeit + ".png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignoriert) {
        }
    }

    private String bekannteFehlerId(String methode) {
        return switch (methode) {
            case "kategoriefilterElektronik" -> " (BUG-001)";
            case "kategoriefilterSport" -> " (BUG-002)";
            case "preissortierungAufsteigend" -> " (BUG-003)";
            case "preissortierungAbsteigend" -> " (BUG-004)";
            case "bewertungssortierungAbsteigend" -> " (BUG-005)";
            default -> "";
        };
    }
}
