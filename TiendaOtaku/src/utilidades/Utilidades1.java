package utilidades;
import java.util.Scanner;

public class Utilidades1 {

    // Versión SIN rango
    public static int pedirEntero(String mensaje) {
        Scanner scan = new Scanner(System.in);
        int valor = 0;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje + ": ");
            try {
                valor = Integer.parseInt(scan.next());
            } catch (Exception e) {
                System.out.println("[ERROR] Valor no válido. Introduce un número entero.");
                error = true;
            }
        } while (error);

        return valor;
    }

    // Versión CON rango
    public static int pedirEntero(String mensaje, int min, int max) {
        Scanner scan = new Scanner(System.in);
        int valor = 0;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje + " (" + min + " - " + max + "): ");
            try {
                valor = Integer.parseInt(scan.next());
                if (valor < min || valor > max) {
                    System.out.println("[ERROR] Introduce un número entre " + min + " y " + max + ".");
                    error = true;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Valor no válido. Introduce un número entero.");
                error = true;
            }
        } while (error);

        return valor;
    }

    public static String pedirString(String mensaje) {
        Scanner scan = new Scanner(System.in);
        String valor = null;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje);
            try {
                valor = scan.nextLine();
            } catch (Exception e) {
                System.out.println("[ERROR] Valor incorrecto");
                error = true;
            }
        } while (error);

        return valor;
    }
    
    // Función para pedir double sin rango
    public static double pedirDouble(String mensaje) {
        Scanner scan = new Scanner(System.in);
        double valor = 0;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje + ": ");
            try {
                valor = Double.parseDouble(scan.next());
            } catch (Exception e) {
                System.out.println("[ERROR] Valor no válido. Introduce un número decimal.");
                error = true;
            }
        } while (error);

        return valor;
    }
    
    public static String pedirStringObligatorio(String mensaje) {
        Scanner scan = new Scanner(System.in);
        String valor;

        do {
            System.out.print(mensaje);
            valor = scan.nextLine().trim(); 

            if (valor.isEmpty()) {
                System.out.println("[ERROR] Debes ingresar un texto. No se permite dejarlo en blanco.");
            }
        } while (valor.isEmpty());

        return valor;
    }
    // Con rango
    public static int pedirEnteroObligatorio(String mensaje, int min, int max) {
        Scanner scan = new Scanner(System.in);
        int valor = 0;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje + " (" + min + " - " + max + "): ");
            String entrada = scan.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("[ERROR] No se permite dejar la entrada en blanco.");
                error = true;
                continue;
            }

            try {
                valor = Integer.parseInt(entrada);
                if (valor < min || valor > max) {
                    System.out.println("[ERROR] Introduce un número entre " + min + " y " + max + ".");
                    error = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Valor no válido. Introduce un número entero.");
                error = true;
            }
        } while (error);

        return valor;
    }
    
    
    // Sin rango
    public static int pedirEnteroObligatorioSinRango(String mensaje) {
        Scanner scan = new Scanner(System.in);
        int valor = 0;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje + ": ");
            String entrada = scan.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("[ERROR] Debes introducir un número entero. No se permite dejarlo en blanco.");
                error = true;
                continue;
            }

            try {
                valor = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Valor no válido. Introduce un número entero.");
                error = true;
            }
        } while (error);

        return valor;
    }


}