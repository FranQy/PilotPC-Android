/**
 * 
 */
package com.example.socketclient;

import java.io.Serializable;

/**
 * @author Mateusz
 * Klasa przesy³ana tak jak TCP_Data, ale tylko przy ³¹czeniu
 */
public class Connect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Has³o do po³¹czenia (6 cyfr)
	 */
public String haslo;
/**
 * Nazwa telefonu/kompa (mo¿e dzia³aæ w dwie strony)
 */
public String nazwa;
/**
 * Wersja aplikacji/serwera (mo¿e dzia³aæ w dwie strony)
 */
public String wersja;

/**
 * informuje, czy kod zosta³ przyjêty
 * @author Mateusz
 *
 */
public enum Status {nieznanyBlad, zlyKod, kodZmieniony, ok };
public Status status;
}
