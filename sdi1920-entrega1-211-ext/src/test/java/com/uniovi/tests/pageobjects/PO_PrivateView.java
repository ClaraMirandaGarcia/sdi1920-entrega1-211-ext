package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.uniovi.utils.SeleniumUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddPost(WebDriver driver, String titleToFill, String textToFill) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);

		// Rellenemos el campo de descripción
		WebElement title = driver.findElement(By.name("title"));
		title.clear();
		title.sendKeys(titleToFill);

		WebElement text = driver.findElement(By.name("text"));
		text.click();
		text.clear();
		text.sendKeys(textToFill);

		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	public static void fillFormAddPostWithPhoto(WebDriver driver, String titleToFill, String textToFill) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);

		// Rellenemos el campo de descripción
		WebElement title = driver.findElement(By.name("title"));
		title.clear();
		title.sendKeys(titleToFill);

		WebElement text = driver.findElement(By.name("text"));
		text.click();
		text.clear();
		text.sendKeys(textToFill);

		URL url = ClassLoader.getSystemResource("static/img/test.jpg");

		try {
			File file = new File(url.toURI());
			WebElement image = driver.findElement(By.id("inputImage"));
			image.sendKeys(file.getAbsolutePath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	
	public static void clickMenuOption(final WebDriver driver, final String typeMenu, final String textMenu,
			final String typeOption, final String textOption) {
		PO_View.checkElement(driver, typeMenu, textMenu).get(0).click();
		PO_View.checkElement(driver, typeOption, textOption).get(0).click();
	}
	

	public static int countInPagination(WebDriver driver, String rowId) {
		int total = 0;
		do {
			try {
				total += SeleniumUtils.EsperaCargaPagina(driver, "id", rowId, PO_View.getTimeout()).size();
			} catch (final TimeoutException e) {
			}
			try {
				final List<WebElement> elementos = PO_View.checkElement(driver, "id", "page-item-next");
				assertTrue(elementos.size() == 1);
				elementos.get(0).click();
			} catch (final TimeoutException e) {
				break;
			}
		} while (true);
		PO_PrivateView.clickOption(driver, "logout", "id", "loginButton");
		return total;
	}

	public static Object countNoPagination(WebDriver driver, String rowId) {
		int total = 0;
		try {
			total += SeleniumUtils.EsperaCargaPagina(driver, "id", rowId, PO_View.getTimeout()).size();
		} catch (final TimeoutException e) {
		}
		PO_PrivateView.clickOption(driver, "logout", "id", "loginButton");
		return total;
	}

	public static void sendInvitation(WebDriver driver, String text, String name) {
		List<WebElement> elementos = PO_View.checkElement(driver, text, "//li[contains(@id,'menu-users')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, text, "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		elementos = PO_HomeView.checkElement(driver, "free",
				"//td[contains(text(), '" + name + "')]/following-sibling::*/a[contains(@href, 'invitation/add/')]");
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Invitaciones del usuario");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

}