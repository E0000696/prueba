package kubo.prueba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws Exception {

		switch (args[0].toLowerCase()) {
		case "add":
			add(args[1]);
			break; // optional

		case "list":
			System.out.println(list());
			break; // optional

		case "search":
			System.out.println(search(args[1]));
			break; // optional

		case "fuzzy-search":
			System.out.println(search(args[1]));
			break; // optional

		default:
			System.out.println("Opción no reconocida.");
		}

	}

	public static String add(String JSON) throws Exception {
		String resultado = "";
		JSONArray obj = null;
		JSONObject input = null;
		FileWriter file = null;
		try {
		if (checkFile() <= 0) {
			obj = new JSONArray();
			input = new JSONObject(JSON);
			obj.put(input);
		} else {
			obj = new JSONArray(list());
			input = new JSONObject(JSON);
			obj.put(input);

		}		
		
			file = new FileWriter("fuzzy-search.txt");
			file.write(obj.toString());
			System.out.println("Usuario agregado");
		} catch (Exception e) {
			System.out.println("Ocurrio un problema con su JSON de entrada, por favor verifique el formato");
		} finally {
			file.close();
		}

		return resultado;
	}

	public static String list() throws Exception {
		String resultado = "";
		boolean exists = true;
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader("fuzzy-search.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String allList = sb.toString();
			JSONArray jsonArr  = new JSONArray(allList);
		    JSONArray sortedJsonArray = new JSONArray();

		    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		    for (int i = 0; i < jsonArr.length(); i++) {
		        jsonValues.add(jsonArr.getJSONObject(i));
		    }
		    Collections.sort( jsonValues, new Comparator<JSONObject>() {

		        private static final String KEY_NAME = "name";

		        @Override
		        public int compare(JSONObject a, JSONObject b) {
		            String valA = new String();
		            String valB = new String();

		            try {
		                valA = (String) a.get(KEY_NAME);
		                valB = (String) b.get(KEY_NAME);
		            } 
		            catch (JSONException e) {
		                System.out.println(e);
		            }

		            return valA.compareTo(valB);
		        }
		    });

		    for (int i = 0; i < jsonArr.length(); i++) {
		        sortedJsonArray.put(jsonValues.get(i));
		    }
			resultado = sortedJsonArray.toString();
		} catch(FileNotFoundException e){
			exists = false;
			return "[]";
		}
		finally {
			if (exists)
			br.close();
		}
		return resultado;
	}

	public static int checkFile() throws Exception {
		int resultado = 0;
		BufferedReader br = null;
		boolean exists = true;
		try {
			br = new BufferedReader(new FileReader("fuzzy-search.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			resultado = sb.toString().length();
		} catch (FileNotFoundException e) {
			exists = false;
			return 0;
		} finally {
			if (exists)
				br.close();
		}
		return resultado;
	}

	public static String search(String entrada) throws Exception {
		JSONArray lista = new JSONArray(list());
		JSONObject input = new JSONObject(entrada);

		double similitudAnterior = -1;
		double similitudActual = -1;
		String resultado = "";
		String busqueda = "";
		String objetivo = "";
		for (int i = 0; i < lista.length(); i++) {
			JSONObject persona = lista.getJSONObject(i);
			busqueda = input.getString("search");
			objetivo = persona.getString("name");

			DistanciaLevenshtein ld = new DistanciaLevenshtein();
			ld.setWords(busqueda, objetivo);

			//Ver resultados
			//System.out.println(ld.getDistancia());
			//System.out.println(ld.getSimilitud() * 100 + " %");
			//aqui podria limitar por distancia o similitud
			similitudActual = ld.getSimilitud() * 100;

				resultado = similitudActual >= similitudAnterior ? objetivo : resultado;
				similitudAnterior = similitudActual >= similitudAnterior ? similitudActual : similitudAnterior;
				

		}
		if(similitudAnterior <=0){
			resultado ="Sin coincidencias";
		}
		
		if(!resultado.equals("Sin coincidencias")){
			JSONObject jsonSalida = new JSONObject();
			jsonSalida.append("name", resultado);
			resultado= jsonSalida.toString();
		}
		return resultado;
	}
}
