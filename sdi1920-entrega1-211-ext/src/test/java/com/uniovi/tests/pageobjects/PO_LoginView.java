package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {
	
	static public void fillForm(WebDriver driver,String emailp, String passwordp) {		
		
		WebElement name = driver.findElement(By.name("username"));
		name.click();
		name.clear();
		name.sendKeys(emailp);
		
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
