
package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

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
		PO_RegisterView.checkUserDoesNotExist(driver, "nonExistantEmail@gmail.com", "123456");
		PO_RegisterView.register(driver, "nonExistantEmail@gmail.com", "NameExample", "SurnameExample", "123456",
				"123456");
		PO_RegisterView.checkUserExist(driver, "nonExistantEmail@gmail.com", "123456");
	}

	// Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void PR02() {
		// email vacio
		PO_RegisterView.checkInvalidRegister(driver, "", "bestname2", "suchlastname", "100%secure", "100%secure",
				"bestname2");
		// nombre vacio
		PO_RegisterView.checkInvalidRegister(driver, "newEmail2@whatever.xd", "", "suchlastname", "100%secure",
				"100%secure", "newEmail2@whatever.xd");
		// apellidos vacios
		PO_RegisterView.checkInvalidRegister(driver, "newEmail3@whatever.xd", "bestname3", "", "100%secure",
				"100%secure", "bestname3");
	}

	// [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "newEmail03@whatever.xd", "bestname03", "suchlastname03", "0", "0");
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		PO_RegisterView.checkUserDoesNotExist(driver, "newEmail03@whatever.xd", "0");
	}

	// [Prueba4] Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		PO_RegisterView.checkUserExist(driver, "example1@gmail.com", "123456");
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// email vacio
		PO_RegisterView.fillForm(driver, "example1@gmail.com", "emailcreated", "in", "sampledataservice",
				"sampledataservice");
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

	// [Prueba5]Inicio de sesión con
	// datos válidos (administrador).
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");

	}

	// [Prueba6] Inicio de sesión con datos válidos
	// (usuario estándar).
	@Test
	public void PR06() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");

	}

	// [Prueba7] Inicio de sesión con datos inválidos
	// (usuario estándar, campo email y contraseña vacíos).
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "Your username and password is invalid.");
		// El usuario no se loguea.
		SeleniumUtils.textoNoPresentePagina(driver, "Usuarios");

	}

	// [Prueba8] Inicio de sesión con datos válidos (usuario estándar, email
	// existente, pero contraseña incorrecta).
	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "wrongEmail@email.com", "123456");
		// Comprobamos que se notifica que la contraseña es incorrecta
		PO_View.checkElement(driver, "text", "Your username and password is invalid.");

	}

	// [Prueba9] Hacer click en la opción de salir de sesión y comprobar que se
	// redirige a la página de inicio de sesión (Login).
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");

		// Ahora nos desconectamos y comprobamos que se redirige a la página de inicio
		// de sesión.
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

		PO_View.checkElement(driver, "text", "Identificate");
	}

	// [Prueba10] Comprobar que el
	// botón cerrar sesión no está visible si el usuario no está autenticado
	@Test
	public void PR10() {

		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_View.checkElement(driver, "text", "Desconectar");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "Identificate");
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

}
