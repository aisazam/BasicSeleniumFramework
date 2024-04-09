package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import java.util.HashMap;
import java.util.Map;


public final class DriverManager {
	private static Logger logger = (Logger) LogManager.getLogger();
	private final String browser;
	private String downloadPath = "";

	public DriverManager(String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox")
				|| browser.equalsIgnoreCase("edge")) {
			this.browser = browser;
		} else {
			throw new Exception("El navegador " + browser + " no esta implementado");
		}
	}

	public DriverManager(String browser, String downloadPath) throws Exception {
		if (browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox")
				|| browser.equalsIgnoreCase("edge")) {
			this.browser = browser;
			this.downloadPath = downloadPath;
		} else {
			throw new Exception("El navegador " + browser + " no esta implementado");
		}
	}


	public WebDriver getDriver() throws Throwable {
		try {
			switch (browser.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					//chromeOptions.addArguments("--auto-open-devtools-for-tabs");
					chromeOptions.addArguments("incognito","--delete-all-cookies","--start-maximized");
					//chromeOptions.addArguments("--force-device-scale-factor=1.25");
					chromeOptions.setAcceptInsecureCerts(true);
					chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
					chromeOptions.setExperimentalOption("useAutomationExtension", false);
					Map<String, Object> prefs = new HashMap<>();
					prefs.put("credentials_enable_service", false);
					prefs.put("profile.password_manager_enabled", false);
					prefs.put("profile.default_content_settings.popups", 0);
					prefs.put("download.prompt_for_download", "false");
					prefs.put("download.default_directory", downloadPath);
					chromeOptions.setExperimentalOption("prefs", prefs);
					chromeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
					chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
					return new ChromeDriver(chromeOptions);
				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.addArguments("--start-maximized", "--delete-all-cookies", "--auto-open-devtools-for-tabs");
					firefoxOptions.addPreference("browser.download.folderList", 2);  // Use custom download directory
					firefoxOptions.addPreference("browser.download.dir", downloadPath);
					firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
					firefoxOptions.setAcceptInsecureCerts(true);
					firefoxOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
					firefoxOptions.setCapability(ChromeOptions.CAPABILITY, firefoxOptions);
					firefoxOptions.setCapability("acceptInsecureCerts", true);
					return new FirefoxDriver(firefoxOptions);
				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("start-maximized", "-inprivate", "--delete-all-cookies");
					edgeOptions.setAcceptInsecureCerts(true);
					edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
					edgeOptions.setExperimentalOption("useAutomationExtension", false);
					Map<String, Object> edgePrefs = new HashMap<>();
					edgePrefs.put("credentials_enable_service", false);
					edgePrefs.put("profile.password_manager_enabled", false);
					edgePrefs.put("profile.default_content_settings.popups", 0);
					edgePrefs.put("download.prompt_for_download", "false");
					edgePrefs.put("download.default_directory", downloadPath);
					edgeOptions.setExperimentalOption("prefs", edgePrefs);
					edgeOptions.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
					edgeOptions.setCapability(ChromeOptions.CAPABILITY, edgeOptions);
					return new EdgeDriver(edgeOptions);
				default:
					throw new Exception("El navegador " + browser + " no esta implementado");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
}
