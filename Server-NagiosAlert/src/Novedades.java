import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

//Verifica si hay novedades en el estado de algun servicio.
public class Novedades implements Runnable {

	String apiKey = "AIzaSyAGTgLEl74YH2XNFrPiduLGBwi8TpA8pNA";
	int numOfRetries = 3;
	String registrationId = "";
	boolean primera = true;
	ArrayList<Servicio> s1, s2;
	ArrayList<Host> h1, h2;

	private Thread t;
	private String nombre;

	public Novedades(String n) {
		this.nombre = n;

	}

	@Override
	public void run() {
		// Aqui se revisa periodicamente si ha habido campos ene l status o la
		// cantidad de los host y servicios.
		while (true) {
			System.out.println("vuelta");

			if (primera) {

				System.out.println("primera");
				primera = false;
				h1 = Parser.parseHosts();
				s1 = Parser.parseServicios();

			} else {
				System.out.println("otras");

				h2 = h1;
				h1 = Parser.parseHosts();
				Collections.sort(h1, Host.hostName);
				Collections.sort(h2, Host.hostName);

				s2 = s1;
				s1 = Parser.parseServicios();

				Collections.sort(s1, Servicio.servicioName);
				Collections.sort(s2, Servicio.servicioName);

				boolean c1, c2;
				c1 = cambiosHost(h1, h2);
				c2 = cambiosServicios(s1, s2);

				if (c1 || c2) {
					notificar();
				}
			}

			try {
				Thread.sleep(5000);// 20 seg
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "novedades");
			t.start();
		}
	}

	public void notificar() {

		// Sender sender = new Sender(apiKey);
		// Message message = new Message.Builder().addData("message",
		// "Novedades")
		// .build();
		//
		// try {
		// Result result = sender.send(message, registrationId, numOfRetries);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private boolean cambiosServicios(ArrayList<Servicio> s1,
			ArrayList<Servicio> s2) {

		// Verificamos cantidad de host y de servicios
		if (s1.size() != s2.size()) {
			return true;
		}

		// Iteramos por los servicio

		Iterator it1 = s1.iterator();
		Iterator it2 = s2.iterator();

		while (it1.hasNext() && it2.hasNext()) {

			Servicio t1, t2;
			t1 = (Servicio) it1.next();
			t2 = (Servicio) it2.next();
			
			String n1 = t1.getNombre();
			String n2 = t2.getNombre();
			int st1 = t1.getStatus();
			int st2 = t2.getStatus();
			

			// if (t1.getDuracion() != t2.getDuracion()) {
			//
			//
			// }

			if (!n1.equals(n2)) {
				System.out.println("Novedad en Nombre Servicio!!");
				return true;
			} else if (st1 != st2) {
				System.out.println("Novedad en Status Servicio!!");
				return true;
			}
		}

		return false;
	}

	private boolean cambiosHost(ArrayList<Host> h1, ArrayList<Host> h2) {

		// Verificamos cantidad de host y de servicios
		if (h1.size() != h2.size()) {
			return true;
		}

		// Iteramos por los servicio
		Iterator it1 = h1.iterator();
		Iterator it2 = h2.iterator();

		while (it1.hasNext() && it2.hasNext()) {

			Host t1, t2;
			t1 = (Host) it1.next();
			t2 = (Host) it2.next();
			String n1 = t1.getNombre();
			String n2 = t2.getNombre();
			int s1 = t1.getStatus();
			int s2 = t2.getStatus();
			
			if (!n1.equals(n2)) {
				System.out.println("Novedad en Nombre Host!!");
				return true;
			} else if (s1 != s2) {
				System.out.println("Novedad en Status Host!!");
				return true;
			}

		}

		return false;
	}

	public static void main(String args[]) {
		Novedades obj = new Novedades("nagios");
		Thread tobj = new Thread(obj);
		tobj.start();
	}

}
