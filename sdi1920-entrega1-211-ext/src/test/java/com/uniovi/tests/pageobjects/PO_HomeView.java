package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_HomeView extends PO_NavView {

	static public void checkSignup(WebDriver driver, int language) {
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("Signup.register", language), getTimeout());
	}
	
	static public void checkWelcome(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("Welcome.message", language), getTimeout());
	}
	
	static public void checkLogin(WebDriver driver, int language) {
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("Nav.login", language), getTimeout());
	}
	
	static public void checkUserList(WebDriver driver, int language) {
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("Nav.login", language), getTimeout());
	}

	static public void checkWelcomeChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkWelcome(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
	}
	
	static public void checkSignupChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {		
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkSignup(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkSignup(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}
	
	static public void checkLoginChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {		
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkLogin(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkLogin(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	
	static public void checkUserListChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {		
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		
		PO_HomeView.checkUserList(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkUserList(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	static public void loginForm(WebDriver driver, String classStr,String buttonName, String loginText, String emailp, String passwordp) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, loginText, classStr, buttonName);
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, emailp, passwordp);
		PO_View.checkElement(driver, "text", emailp);
	}
	
	static public boolean checkLogin(WebDriver driver, String classStr,String buttonName, String loginText, String emailp, String passwordp, String textToCheck) {
		
		PO_HomeView.clickOption(driver, loginText, classStr, buttonName);
		PO_LoginView.fillForm(driver, emailp, passwordp);
		List<WebElement> list = PO_View.checkElement(driver, "text", textToCheck);		
		if (list.size() == 0) {
			return false;
		}
		return true;
	}

}
