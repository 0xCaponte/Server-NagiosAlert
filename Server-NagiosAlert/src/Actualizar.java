
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Actualizar, se encarga de enviar la informacion del estado de los host
 * a la aplicacion. 
 */
@WebServlet("/Actualizar")
public class Actualizar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	//Clase privada que permite enviar en un mismo objeto tanto un host como todos los serivcios 
	// asociados a el.
	private class envio{
		
		Host h;
		ArrayList<Servicio> s;
		
		private envio(Host h, ArrayList<Servicio> s){
			
			this.h = h;
			this.s = s;
		}
		
	}
	public Actualizar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		
		
		// ------------- FALTA AUTENTICAR LA PETICION ----------------------

		// Obtenemos la lista de hosts y servicios
		ArrayList<Host> h = Parser.parseHosts();
		ArrayList<Servicio> s = Parser.parseServicios();
		
		
//TEMPORAL -- SOLO PARA MANDAR MAS DE UN HOST!!!
		Host h1 = new Host("Zeus", h.get(0).getStatus(), h.get(0).getRevision(), h.get(0).getDuracion());
		h.add(h1);
		
		Servicio s1 = new Servicio("Prueba", "Zeus", s.get(0).getStatus(), s.get(0).getRevision(), s.get(0).getDuracion(), "Solo para ver");
		s.add(s1);
//FIN TEMPORAL -------
		
		Collections.sort(h, Host.hostName);
		Collections.sort(s, Servicio.servicioName);
	
		// Creamos el hashmap
		HashMap<String, envio> mapa = new HashMap<String, envio>();

		for (Host t1 : h) {
			ArrayList<Servicio> temporal = new ArrayList<Servicio>();

			for (Servicio t2 : s) {

				if (t1.getNombre().equals(t2.getHost())) {
					temporal.add(t2);
				}
			}
			
			envio e = new envio(t1,temporal);
			
			mapa.put(t1.getNombre(), e);
		}

		String json = new Gson().toJson(mapa);

		RSA rsa = new RSA();
		AES aes = new AES();
		
		try {
			KeyPair kp = rsa.generarLlaves_RSA();
				
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Aqui lo mandamos
		out.print(json);
		
		//Iniciamos proceso de enveloping
		
		byte[] en_text = null;
		byte[] en_key = null;
		try {
			
			//1) ciframos el texto con AES (simetrico)
			en_text = aes.cifrar_AES(json);
			
			//2) ciframos la lalve del AES (asimetrico)
			en_key = rsa.cifrar_RSA(aes.getKey());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.print("\n\n ---------------------- CIFRADO TEXTO CON AES -----------------");
		out.print(new String(en_text));
		out.print("\n\n ---------------------------- CIFRADO LLAVE CON RSA  -------------- \n ");
		out.print(new String(en_key));
		
		String des_text = null;
		String des_key = null;
		
		//1) desciframos la llave del AES con RSA  (asimetrico)
		des_key = rsa.descifrar_RSA(en_key);
		
		//2) desciframos el texto con AES (simetrico)
		try {
			des_text = aes.descifrar_AES(en_text, des_key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.print("\n\n ---------------------------- DESCIFRADO LLAVE CON RSA  -------------- \n ");
		out.print(new String(des_key));
		out.print("\n\n ---------------------- DESCIFRADO TEXTO CON AES -----------------");
		out.print(new String(des_text));
		
		


		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
