package commonFunctions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ApplicationUtility {
	protected static WebDriver driver;
	static Properties properties;

	public static String getValueFromPropertyFile(final String key) {

		properties = new Properties();
		FileInputStream inputStream = null;

		try {

			inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/resources/Property.properties");
			// load a properties file from class path, inside static method

			properties.load(inputStream);

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties.getProperty(key);
	}

	public static void openBrowser() {
		try {
			driver = intilializeDriver();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(getValueFromPropertyFile("Url"));
		System.out.println("Driver is now initilized.");
		ImplicitWait(50);
	}

	public static WebDriver intilializeDriver() throws IOException {

		final String browserName = getValueFromPropertyFile("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/home/darshan/chromedriver_linux64/chromedriver");
			driver = new ChromeDriver();
			// execute in chrome driver
		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\testData\\geckodriver.exe");
			driver = new FirefoxDriver();
			// firefox code
		} else if (browserName.equals("edge")) {
			// IE code
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\testData\\iedriver.exe");
			driver = new InternetExplorerDriver();
		}

		driver.manage().window().maximize();
		ImplicitWait(30);
		return driver;
	}

	public static void ImplicitWait(Integer time) {

		// wait till the page is loaded
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public static void waitTime(Integer time) {

		// wait till the page is loaded
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
