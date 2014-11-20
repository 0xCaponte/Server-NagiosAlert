import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.runtime.Log;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Servlet implementation class Llaves, este servlet permitira el intercambio de las llaves
 * publicas del cliente y del servidor.
 */
@WebServlet("/Llaves")
public class Llaves extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected String cifrar(String texto, Key llave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String cifrado = "";

		// cifrar data
		byte[] bytescifrados = null;

		Cipher c = null;

		c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, llave);
		bytescifrados = c.doFinal(texto.getBytes());
		cifrado = new String(bytescifrados);
		return cifrado;
	}
	
	protected String decifrar(String texto, Key llave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String decifrado = "";

		// Encode the original data with RSA private key
		byte[] bytesdecifrados = null;

		Cipher c = Cipher.getInstance("RSA");
	    c.init(Cipher.DECRYPT_MODE, llave);
	    bytesdecifrados = c.doFinal(texto.getBytes());
		decifrado = new String(bytesdecifrados);
	    return decifrado;
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Llaves() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	// Aqui enviamos nuestra llave publica.
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		// ------ Se busca en un archivo o en su defecto se crea----------
		String json = "";
/*		json = new Gson().toJson(kp.getPublica());
		
		try {
		
			String t1 = cifrar("prueba", kp.getPublica());
		
			System.out.println(t1);
			String t2 = decifrar(t1, kp.getPrivada());
	
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		out.print("nada");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// Aqui recibimos la clave publica del usuario
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
