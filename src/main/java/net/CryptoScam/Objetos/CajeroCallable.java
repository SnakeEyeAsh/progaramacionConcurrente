package net.CryptoScam.Objetos;

import java.util.concurrent.Callable;

public class CajeroCallable extends Cajero implements Callable<String> {

    public CajeroCallable(CuentaCrypto cuenta, String nombre, boolean robar, double cantidad) {
        super(cuenta, nombre, robar, cantidad);
    }

    @Override
    public String call() throws Exception {

        String resultado;
        boolean exito=false;

        if (robar) {
            exito = cuenta.retirar(cantidad,nombre);
            resultado = exito?"Se ha retirado correctamente "+cantidad:"Fallo al retirar "+cantidad+" eurosh ";
        }else{
            cuenta.ingresar(cantidad,nombre);
            resultado = "Deposito de "+cantidad+" exitosa";
        }
        return resultado;
    }
}
