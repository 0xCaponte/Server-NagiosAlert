
import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class Actualizar
 */
@WebServlet("/Actualizar")
public class Actualizar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private class envio{
		
		host h;
		ArrayList<servicio> s;
		
		private envio(host h, ArrayList<servicio> s){
			
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
		ArrayList<host> h = parser.parseHosts();
		ArrayList<servicio> s = parser.parseServicios();
		
		
//TEMPORAL!!!
		host h1 = new host("Zeus", h.get(0).getStatus(), h.get(0).getRevision(), h.get(0).getDuracion());
		h.add(h1);
		
		servicio s1 = new servicio("Prueba", "Zeus", s.get(0).getStatus(), s.get(0).getRevision(), s.get(0).getDuracion(), "Solo para ver");
		s.add(s1);
//FIN TEMPORAL
		
		Collections.sort(h, host.hostName);
		Collections.sort(s, servicio.servicioName);
		//String json = "";
		// Creamos el hashmap
		HashMap<String, envio> mapa = new HashMap<String, envio>();

		for (host t1 : h) {
			ArrayList<servicio> temporal = new ArrayList<servicio>();

			for (servicio t2 : s) {

				//json = new Gson().toJson(t1);
				if (t1.getNombre().equals(t2.getHost())) {
					temporal.add(t2);
				}
			}
			
			envio e = new envio(t1,temporal);
			
			mapa.put(t1.getNombre(), e);
		}

		String json = new Gson().toJson(mapa);
		out.print(json);

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
