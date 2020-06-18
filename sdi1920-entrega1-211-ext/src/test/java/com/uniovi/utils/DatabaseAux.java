package com.uniovi.utils;


import com.uniovi.services.InsertSampleData;

public class DatabaseAux {
	
	static InsertSampleData auxDatabaseAccess = new InsertSampleData();

	/**
	 * Borra un usuario de la base de datos	 
	 */
	static public void deleteUser(String email) {
		auxDatabaseAccess.deleteUser(email);
	}

	
}
