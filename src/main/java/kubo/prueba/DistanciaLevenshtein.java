package kubo.prueba;

public class DistanciaLevenshtein {

	private String palabra1;
	private String palabra2;
	private int distancia;
	private int[][] matriz;

	public void setWords(String palabra1, String palabra2) {
		this.palabra1 = palabra1.toLowerCase();
		this.palabra2 = palabra2.toLowerCase();
		algoritmoLevenshtein();
	}

	public int getDistancia() {
		return distancia;
	}

	public double getSimilitud() {
		double longitud = palabra1.length() > palabra2.length() ? palabra1.length() : palabra2.length();
		return 1 - (distancia / longitud);
	}

	private void algoritmoLevenshtein() {
		matriz = new int[palabra1.length() + 1][palabra2.length() + 1];
		for (int i = 0; i <= palabra1.length(); i++) {
			matriz[i][0] = i;
		}
		for (int j = 0; j <= palabra2.length(); j++) {
			matriz[0][j] = j;
		}
		for (int i = 1; i < matriz.length; i++) {
			for (int j = 1; j < matriz[i].length; j++) {
				if (palabra1.charAt(i - 1) == palabra2.charAt(j - 1)) {
					matriz[i][j] = matriz[i - 1][j - 1];
				} else {
					int min = Integer.MAX_VALUE;
					if ((matriz[i - 1][j]) + 1 < min) {
						min = (matriz[i - 1][j]) + 1;
					}
					if ((matriz[i][j - 1]) + 1 < min) {
						min = (matriz[i][j - 1]) + 1;
					}
					if ((matriz[i - 1][j - 1]) + 1 < min) {
						min = (matriz[i - 1][j - 1]) + 1;
					}
					matriz[i][j] = min;
				}
			}
		}
		distancia = matriz[palabra1.length()][palabra2.length()];
	}

}