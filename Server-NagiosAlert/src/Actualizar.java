
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
		Collections.sort(h, host.hostName);
		Collections.sort(s, servicio.servicioName);

		// Creamos el hashmap
		HashMap<host, ArrayList<servicio>> mapa = new HashMap<host, ArrayList<servicio>>();

		for (host t1 : h) {
			ArrayList<servicio> temporal = new ArrayList<servicio>();

			for (servicio t2 : s) {

				if (t1.getNombre().equals(t2.getHost())) {
					temporal.add(t2);
				}
			}

			mapa.put(t1, temporal);
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
