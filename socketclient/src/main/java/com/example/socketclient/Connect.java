/**
 * 
 */
package com.example.socketclient;

import java.io.Serializable;

/**
 * @author Mateusz
 * Klasa przesy�ana tak jak TCP_Data, ale tylko przy ��czeniu
 */
public class Connect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Has�o do po��czenia (6 cyfr)
	 */
public String haslo;
/**
 * Nazwa telefonu/kompa (mo�e dzia�a� w dwie strony)
 */
public String nazwa;
/**
 * Wersja aplikacji/serwera (mo�e dzia�a� w dwie strony)
 */
public String wersja;

/**
 * informuje, czy kod zosta� przyj�ty
 * @author Mateusz
 *
 */
public enum Status {nieznanyBlad, zlyKod, kodZmieniony, ok };
public Status status;
}
