
package com.uniovi.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
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
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
//		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'db-menu')]/a", "free",
//				"//a[contains(@href,'database/reset')]");
//		driver.quit();
	}

//	// [Prueba1] Registro de Usuario con datos válidos
//	@Test
//	public void PR01() {
//		PO_RegisterView.checkUserDoesNotExist(driver, "nonExistantEmail@gmail.com", "123456");
//		PO_RegisterView.register(driver, "nonExistantEmail@gmail.com", "NameExample", "SurnameExample", "123456",
//				"123456");
//		PO_RegisterView.checkUserExist(driver, "nonExistantEmail@gmail.com", "123456");
//		//DatabaseAux.deleteUser("nonExistantEmail@gmail.com");
//	}
//
//	// Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
//	// apellidos vacíos).
//	@Test
//	public void PR02() {
//		// email vacio
//		// PO_RegisterView.checkInvalidRegister(driver, "", "nameEx", "lastNameEx",
//		// "123456", "123456");
//		// nombre vacio
//		PO_RegisterView.checkInvalidRegister(driver, "invalidEmail@gmail.com", "", "suchlastname", "100%secure",
//				"100%secure");
//		// apellidos vacios
//		PO_RegisterView.checkInvalidRegister(driver, "invalidEmail@gmail.com", "bestname3", "", "100%secure",
//				"100%secure");
//	}
//
//	// [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
//	// inválida).
//	@Test
//	public void PR03() {
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		PO_RegisterView.fillForm(driver, "newEmail03@whatever.xd", "bestname03", "suchlastname03", "0", "0");
//		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
//		PO_RegisterView.checkUserDoesNotExist(driver, "newEmail03@whatever.xd", "0");
//	}
//
//	// [Prueba4] Registro de Usuario con datos inválidos (email existente).
//	@Test
//	public void PR04() {
//		PO_RegisterView.checkUserExist(driver, "example1@gmail.com", "123456");
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// email vacio
//		PO_RegisterView.fillForm(driver, "example1@gmail.com", "emailcreated", "in", "sampledataservice",
//				"sampledataservice");
//		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
//	}
//
//	// [Prueba5]Inicio de sesión con
//	// datos válidos (administrador).
//	@Test
//	public void PR05() {
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
//
//	}
//
//	// [Prueba6] Inicio de sesión con datos válidos
//	// (usuario estándar).
//	@Test
//	public void PR06() {
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
//
//	}
//
//	// [Prueba7] Inicio de sesión con datos inválidos
//	// (usuario estándar, campo email y contraseña vacíos).
//	@Test
//	public void PR07() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "", "");
//		SeleniumUtils.textoNoPresentePagina(driver, "Your username and password is invalid.");
//		SeleniumUtils.textoNoPresentePagina(driver, "Usuarios");
//
//	}
//
//	// [Prueba8] Inicio de sesión con datos válidos (usuario estándar, email
//	// existente, pero contraseña incorrecta).
//	@Test
//	public void PR08() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "wrongEmail@email.com", "123456");
//		// Comprobamos que se notifica que la contraseña es incorrecta
//		PO_View.checkElement(driver, "text", "Your username and password is invalid.");
//
//	}
//
//	// [Prueba9] Hacer click en la opción de salir de sesión y comprobar que se
//	// redirige a la página de inicio de sesión (Login).
//	@Test
//	public void PR09() {
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
//		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
//	}
//
//	// [Prueba10] Comprobar que el
//	// botón cerrar sesión no está visible si el usuario no está autenticado
//	@Test
//	public void PR10() {
//
//		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
//		PO_View.checkElement(driver, "text", "Desconectar");
//		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
//		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
//
//	}
//
//	// [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos
//	// los que existen en el sistema
//	@Test
//	public void PR11() {
//		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
//		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
//				"//a[contains(@href,'user/list')]");
//		assertEquals(5, PO_PrivateView.countInPagination(driver, "userRow"));
//	}
//
//	@Test
//	public void PR12() {
//		assertEquals(5, PO_RegisterView.checkSearchForm(driver, ""));
//	}
//
//	@Test
//	public void PR13() {
//		assertEquals(0, PO_RegisterView.checkSearchForm(driver, "shouldNotAppear"));
//	}
//
//	@Test
//	public void PR14() {
//		assertEquals(1, PO_RegisterView.checkSearchForm(driver, "Name1"));
//	}

	@Test
	public void PR15() {
		// EXAMPLE1->EXAMPLE4
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-users')]/a", "free",
				"//a[contains(@href, 'user/list')]");
		List<WebElement> elementos = PO_HomeView.checkElement(driver, "free",
				"//td[contains(text(), 'example4@gmail.com')]/following-sibling::*/a[contains(@href, 'invitation/add/')]");
		elementos.get(0).click();
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example4@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-invitations')]/a", "free",
				"//a[contains(@href, '/invitation/list')]");
		PO_View.checkElement(driver, "text", "Name1");

	}

//[Prueba16] Desde el listado de usuarios de la aplicación,
	// enviar una invitación de amistad a un usuario al que ya le
	// habíamos enviado la invitación previamente.
	// No debería dejarnos enviar la invitación,
	// se podría ocultar el botón de enviar invitación o
	// notificar que ya había sido enviada previamente.

	@Test
	public void PR16() {
		// EXAMPLE1->EXAMPLE4
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-users')]/a", "free",
				"//a[contains(@href, 'user/list')]");

//		List<WebElement> elementos = PO_View.checkElement(driver, "free",
//				"//td[contains(text(), 'example4@gmail.com')]/following-sibling::*/a[contains(@href, 'invitation/add/')]");
//		assertTrue(elementos.size()==0);
		PO_View.checkElement(driver, "text", "Invitado");
	}

	// [Prueba17] Mostrar el listado de invitaciones de amistad recibidas.
	// Comprobar con un listado que contenga varias invitaciones recibidas.
	@Test
	public void PR17() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example4@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-invitations')]/a", "free",
				"//a[contains(@href, 'invitation/list')]");

		// Check 3 invitations
		PO_View.checkElement(driver, "text", "Name1");
		PO_View.checkElement(driver, "text", "Name5");
		PO_View.checkElement(driver, "text", "Name6");

		assertEquals(3, PO_PrivateView.countInPagination(driver, "invitationRow"));
//		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// [Prueba18] Sobre el listado de invitaciones recibidas. Hacer click en el
	// botón/enlace de una de ellas
	// y comprobar que dicha solicitud desaparece del listado de invitaciones
	@Test
	public void PR18() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example4@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-invitations')]/a", "free",
				"//a[contains(@href, 'invitation/list')]");

		// Check 3 invitations
		PO_View.checkElement(driver, "text", "Name1");
		PO_View.checkElement(driver, "text", "Name5");
		PO_View.checkElement(driver, "text", "Name6");

		assertEquals(3, PO_PrivateView.countInPagination(driver, "invitationRow"));

		// Accepting 1 -> no me localiza ese elemento :)
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Name1')]/following-sibling::*/a[contains(@href, 'friend/add/')]");
		elementos.get(0).click();

		// Check 2 invitations
		SeleniumUtils.textoNoPresentePagina(driver, "Name1");
		PO_View.checkElement(driver, "text", "Name5");
		PO_View.checkElement(driver, "text", "Name6");

		assertEquals(3, PO_PrivateView.countInPagination(driver, "invitationRow"));
//		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

}
