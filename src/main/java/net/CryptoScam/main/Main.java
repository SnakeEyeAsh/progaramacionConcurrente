package net.CryptoScam.main;

import net.CryptoScam.Objetos.Cajero;
import net.CryptoScam.Objetos.CajeroCallable;
import net.CryptoScam.Objetos.CajeroRunable;
import net.CryptoScam.Objetos.CuentaCrypto;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        CuentaCrypto cuenta = new CuentaCrypto(10000.0);

        ejecutarCallable(cuenta);
        Queue<Integer> cola = cargar();
        System.out.println(cola.peek());


    }


    public static void ejecutarCallable(CuentaCrypto cuenta){
        //creamos los atributos
        Queue<Integer> cola = cargar();
        ExecutorService poolHilos = Executors.newFixedThreadPool(4);
        List<Future<String>> listado = new ArrayList<>();

        //creamos los hiloscon callable
        Callable<String> cajero1 = new CajeroCallable(cuenta, "Caixa", true, 550);
        Callable<String> cajero2 = new CajeroCallable(cuenta, "BBVA", false, 750);
        Runnable cajero3 = new CajeroRunable(cuenta, "Santander", false, 250);
        Runnable cajero4 = new CajeroRunable(cuenta, "Caixa 2", true, 500);

        listado.add(poolHilos.submit(cajero1));
        listado.add(poolHilos.submit(cajero2));
        poolHilos.submit(cajero3);
        poolHilos.submit(cajero4);


        try {
            if (poolHilos.awaitTermination(1, TimeUnit.MINUTES)) {

                System.out.println("Todos los hilos terminaron");

                for(int i = 0; i< listado.size();i++){

                    Future<String> datos = listado.get(i);
                    try {
                        String resultado = datos.get();
                        System.out.println(resultado);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("Los hilos aun no terminan");
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        poolHilos.shutdown();

        System.out.println("Dinero " + cuenta.consultar());
    }

    public static Queue<Integer> cargar(){
        Queue<Integer> cola = new ArrayDeque<>();
        try{

            File archivoXML =  new File("src/main/resources/numeros.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builer = factory.newDocumentBuilder();
            Document doc = builer.parse(archivoXML);

            //Normalizar documento
            doc.getDocumentElement().normalize();


            NodeList listaPrimos = doc.getElementsByTagName("primos");
            if(listaPrimos.getLength()>0){
                Element primo = (Element) listaPrimos.item(0);
                String contenido = primo.getTextContent();
                String[] listilla = contenido.split(",");

                for(String num:listilla){
                    cola.add(Integer.parseInt(num));
                    System.out.println(num);

                }
            }

        }catch(Exception e){
            e.getMessage();
        }
        return cola;
    }
}
