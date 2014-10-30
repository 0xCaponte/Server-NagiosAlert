import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.logging.Logger;

/**
 * Fecha: 29/10/14
 * 
 * Clase: Key
 * 
 * Clase que implementa diversas funciones para generar y manipular las llaves RSA tanto publicas
 * como privadas.
 * 
 * @author caponte
 *
 */

public class key {

	private KeyPair kp;

	public key() {

		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			this.kp = kpg.genKeyPair();

		} catch (Exception e) {
			kp = null;
		}
	}
	
	public Key getPublica(){
		return kp.getPublic();
	}
	
	public Key getPrivada(){
		return kp.getPrivate();
	}
	
}
