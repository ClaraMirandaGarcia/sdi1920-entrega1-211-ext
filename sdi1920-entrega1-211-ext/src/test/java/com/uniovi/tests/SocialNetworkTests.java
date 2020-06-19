
package com.uniovi.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.services.InsertSampleData;
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
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-database')]/a", "free",
				"//a[contains(@href,'database/clear')]");
		driver.quit();
		
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
		 PO_RegisterView.checkInvalidRegisterSimple(driver, "", "nameEx", "lastNameEx",
		 "123456", "123456");
		// nombre vacio
		PO_RegisterView.checkInvalidRegister(driver, "invalidEmail@gmail.com", "", "suchlastname", "100%secure",
				"100%secure");
		// apellidos vacios
		PO_RegisterView.checkInvalidRegister(driver, "invalidEmail@gmail.com", "bestname3", "", "100%secure",
				"100%secure");
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
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");

	}

	// [Prueba6] Inicio de sesión con datos válidos
	// (usuario estándar).
	@Test
	public void PR06() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");

	}

	// [Prueba7] Inicio de sesión con datos inválidos
	// (usuario estándar, campo email y contraseña vacíos).
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		SeleniumUtils.textoNoPresentePagina(driver, "Your username and password is invalid.");
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
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// [Prueba10] Comprobar que el
	// botón cerrar sesión no está visible si el usuario no está autenticado
	@Test
	public void PR10() {

		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_View.checkElement(driver, "text", "Desconectar");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

	}

	// [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos
	// los que existen en el sistema
	@Test
	public void PR11() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");
		assertEquals(5, PO_PrivateView.countInPagination(driver, "userRow"));
	}

	@Test
	public void PR12() {
		assertEquals(5, PO_RegisterView.checkSearchForm(driver, ""));
	}

	@Test
	public void PR13() {
		assertEquals(0, PO_RegisterView.checkSearchForm(driver, "shouldNotAppear"));
	}

	@Test
	public void PR14() {
		assertEquals(1, PO_RegisterView.checkSearchForm(driver, "Name1"));
	}

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

		assertEquals(3, PO_PrivateView.countInPaginationAux(driver, "invitationRow"));

		// Accepting 1 -> no me localiza ese elemento :)
		List<WebElement> aux2 = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Name1')]/following-sibling::*/a[contains(@href, 'friend/add/')]");
		aux2.get(0).click();

		// Check 2 invitations
		SeleniumUtils.textoNoPresentePagina(driver, "Name1");
		PO_View.checkElement(driver, "text", "Name5");
		PO_View.checkElement(driver, "text", "Name6");

		assertEquals(2, PO_PrivateView.countInPagination(driver, "invitationRow"));
//		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// [Prueba17] Mostrar el listado de invitaciones de amistad recibidas.
	// Comprobar con un listado que contenga varias invitaciones recibidas.
	@Test
	public void PR19() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id,'menu-friends')]/a", "free",
				"//a[contains(@href, 'friend/list')]");

		// Check 4 friendship
		assertEquals(4, PO_PrivateView.countInPagination(driver, "friendRow"));
	}

	// [Prueba20] Visualizar al menos cuatro páginas en Español/Inglés/Español
	// (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	// Ejemplo, Página principal/Opciones Principales de Usuario/Listado de Usuarios
	@Test
	public void PR20() {

		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.checkWelcomeChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		PO_HomeView.checkSignupChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		PO_HomeView.checkLoginChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());

	}

	// [Prueba21] Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al formulario de login.
	@Test
	public void PR21() {
		driver.navigate().to(URL + "/user/list");
		PO_View.checkElement(driver, "id", "loginButton");
	}

	// [Prueba22] Intentar acceder sin estar autenticado a la opción de listado de
	// publicaciones de un usuario
	// estándar. Se deberá volver al formulario de login.
	@Test
	public void PR22() {
		driver.navigate().to(URL + "/post/listfor/example2@gmail.com");
		PO_View.checkElement(driver, "id", "loginButton");
	}

	// [Prueba23] Estando autenticado como usuario estándar intentar acceder a una
	// opción disponible solo para usuarios administradores
	// Se puede añadir una opción cualquiera en el
	// menú). Se deberá indicar un mensaje de acción prohibida.
	@Test
	public void PR23() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example3@gmail.com", "123456");
		driver.navigate().to(URL + "/user/delete");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Usuarios", PO_View.getTimeout());
	}

	// [Prueba24] Ir al formulario crear publicaciones,
	// rellenarla con datos válidos y pulsar el botón Submit.
	// Comprobar que la publicación sale en el listado de publicaciones de dicho
	// usuario.

	@Test
	public void PR24() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");
		PO_PrivateView.fillFormAddPost(driver, "TitleOfPost24", "TextOfTest24");
		PO_View.checkElement(driver, "text", "TitleOfPost24");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// [Prueba25] Ir al formulario de crear publicaciones, rellenarla con datos
	// inválidos (campo título vacío) y pulsar el botón Submit. Comprobar que se
	// muestra el
	// mensaje de campo obligatorio.
	@Test
	public void PR25() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");
		PO_PrivateView.fillFormAddPost(driver, "", "TextOfText25");
		PO_View.checkElement(driver, "text", "El campo título no puede estar vacío");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// [Prueba26] Mostrar el listado de publicaciones de un usuario y comprobar que
	// se muestran todas las que existen para dicho usuario.
	@Test
	public void PR26() {
		// Vamos al formulario de logueo.
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example3@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");
		PO_PrivateView.fillFormAddPost(driver, "TitleOfTest26-1", "TextOfTest26-1");
		PO_View.checkElement(driver, "text", "TitleOfTest26-1");

		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");

		PO_PrivateView.fillFormAddPost(driver, "TitleOfTest26-2", "TextOfTest26-2");
		PO_View.checkElement(driver, "text", "TitleOfTest26-1");
		PO_View.checkElement(driver, "text", "TitleOfTest26-2");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	@Test
	public void PR27() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-friends')]/a", "free",
				"//a[contains(@href,'friend/list')]");
		PO_PrivateView.clickOption(driver, "/post/postsOf/example6@gmail.com", "id", "emailOf");
		assertEquals(2, PO_PrivateView.countNoPagination(driver, "postForRow"));
	}

	@Test
	public void PR28() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		driver.navigate().to(URL + "/post/postsOf/example5@gmail.com");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "errorId", PO_View.getTimeout());
	}

	// [Prueba29] Desde el formulario de crear publicaciones, crear una
	// publicación con datos válidos y una foto adjunta.
	// Comprobar que en el listado de publicaciones aparece la foto
	// adjunta junto al resto de datos de la publicación.
	@Test
	public void PR29() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");
		PO_PrivateView.fillFormAddPostWithPhoto(driver, "TitleOfPostTest29", "TextOfTest29");
		PO_View.checkElement(driver, "text", "TitleOfPostTest29");
		PO_View.checkElement(driver, "class", "postImage");
	}

	// [Prueba30] Crear una publicación con datos válidos y sin una foto adjunta.
	// Comprobar que la publicación se ha creado con éxito, ya que la foto no es
	// obligatoria.
	@Test
	public void PR30() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-posts')]/a", "free",
				"//a[contains(@href,'/post/add')]");
		PO_PrivateView.fillFormAddPost(driver, "TitleOfPostTest30", "TextOfTest30");
		PO_View.checkElement(driver, "text", "TitleOfPostTest30");
	}

	// [Prueba31] Mostrar el listado de usuarios y comprobar
	// que se muestran todos los que existen en el sistema.
	@Test
	public void PR31() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");

		int numberOfUsers = PO_PrivateView.countInPaginationAux(driver, "userRow");
		assertEquals(9, numberOfUsers);
	}

	// [Prueba32] Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza
	// y dicho usuario desaparece.
	@Test
	public void PR32() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");
		List<WebElement> users = SeleniumUtils.EsperaCargaPagina(driver, "class", "emailData", PO_View.getTimeout());
		String email = users.get(0).getText();
		List<WebElement> checkBoxes = SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteBox",
				PO_View.getTimeout());
		checkBoxes.get(0).click();
		By boton = By.id("deleteSubmit");
		driver.findElement(boton).click();
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, email, PO_View.getTimeout());
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar
	// que la lista se actualiza
	// y dicho usuario desaparece.
	@Test
	public void PR33() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");
		List<WebElement> users = SeleniumUtils.EsperaCargaPagina(driver, "class", "emailData", PO_View.getTimeout());
		String email = users.get(users.size() - 1).getText();
		List<WebElement> checkBoxes = SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteBox",
				PO_View.getTimeout());
		checkBoxes.get(checkBoxes.size() - 1).click();
		By boton = By.id("deleteSubmit");
		driver.findElement(boton).click();
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, email, PO_View.getTimeout());
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se
	// actualiza y dichos
	// usuarios desaparecen.
	@Test
	public void PR34() {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "admin1@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");
		List<WebElement> users = SeleniumUtils.EsperaCargaPagina(driver, "class", "emailData", PO_View.getTimeout());
		int count = users.size();
		users = users.subList(0, 3);
		List<WebElement> checkBoxes = SeleniumUtils.EsperaCargaPagina(driver, "class", "deleteBox",
				PO_View.getTimeout());
		checkBoxes = checkBoxes.subList(0, 3);
		checkBoxes.get(checkBoxes.size() - 1).click();
		checkBoxes.get(checkBoxes.size() - 2).click();
		checkBoxes.get(checkBoxes.size() - 3).click();
		By boton = By.id("deleteSubmit");
		driver.findElement(boton).click();
		assertEquals(count - 3, PO_PrivateView.countNoPagination(driver, "userRow"));
	}
}