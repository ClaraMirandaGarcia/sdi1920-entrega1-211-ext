package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.utils.SeleniumUtils;

public class PO_RegisterView extends PO_NavView {

	public static void register(WebDriver driver, String emailp, String namep, String lastnamep, String passwordp,
			String passwordconfp) {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, emailp, namep, lastnamep, passwordp, passwordconfp);
		PO_View.checkElement(driver, "text", emailp);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	static public void fillForm(WebDriver driver, String emailp, String namep, String lastnamep, String passwordp,
			String passwordconfp) {
		WebElement email = driver.findElement(By.name("email"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement name = driver.findElement(By.name("name"));
		name.click();
		name.clear();
		name.sendKeys(namep);
		WebElement lastname = driver.findElement(By.name("lastName"));
		lastname.click();
		lastname.clear();
		lastname.sendKeys(lastnamep);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
		passwordConfirm.click();
		passwordConfirm.clear();
		passwordConfirm.sendKeys(passwordconfp);
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	public static void checkUserExist(WebDriver driver, String email, String password) {
		assertTrue(PO_HomeView.checkLogin(driver, "class", "btn btn-primary", "login", email, password, email));
		PO_PrivateView.clickOption(driver, "logout", "text", "You have been logged out successfully.");

	}

	public static void checkUserDoesNotExist(WebDriver driver, String email, String password) {
		String text = "Your username and password is invalid.";
		assertTrue(PO_HomeView.checkLogin(driver, "class", "btn btn-primary", "login", email, password, text));
	}

	public static void checkInvalidRegister(WebDriver driver, String email, String name, String lastname, String pass,
			String passconfirm) {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, email, name, lastname, pass, passconfirm);
		PO_RegisterView.checkUserDoesNotExist(driver, email, pass);
	}

	public static void checkInvalidRegisterSimple(WebDriver driver, String email, String name, String lastname,
			String pass, String passconfirm) {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, email, name, lastname, pass, passconfirm);
		// check in the same page
		PO_View.checkElement(driver, "id", "signupLabel");
	}

	public static int checkSearchForm(WebDriver driver, String text) {
		PO_HomeView.loginForm(driver, "class", "btn btn-primary", "login", "example2@gmail.com", "123456");
		PO_PrivateView.clickMenuOption(driver, "free", "//li[contains(@id, 'menu-users')]/a", "free",
				"//a[contains(@href,'user/list')]");
		WebElement searchBox = SeleniumUtils.EsperaCargaPagina(driver, "id", "searchInput", PO_View.getTimeout())
				.get(0);
		searchBox.click();
		searchBox.clear();
		searchBox.sendKeys(text);
		By boton = By.id("submitSearchButton");
		driver.findElement(boton).click();
		return PO_PrivateView.countInPagination(driver, "userRow");
	}
}