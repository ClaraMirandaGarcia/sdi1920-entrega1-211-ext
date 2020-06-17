
package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_RegisterView;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkTests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\CMG\\Desktop\\Tercero\\2_Cuatrimestre\\SDI\\Laboratorio\\Material\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8080";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {

	}

	@AfterClass
	static public void end() {
		// PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login",
		// "admin1@admin.xd", "123456");
//		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'db-menu')]/a", "free",
//				"//a[contains(@href,'database/reset')]");
//		driver.quit();
	}

	// [Prueba1] Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		PO_RegisterView.checkUserDoesNotExist(driver, "nonExistantEmail@gmail.com");
		// PO_RegisterView.register(driver, "newEmail@whatever.xd", "bestname",
		// "suchlastname", "100%secure",
		// "100%secure");
		// PO_RegisterView.checkUserExist(driver, "newEmail@whatever.xd");
	}

}
