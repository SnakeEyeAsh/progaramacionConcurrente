package net.CryptoScam.Objetos;

public class CajeroRunable extends Cajero implements Runnable{
    public CajeroRunable(CuentaCrypto cuenta, String nombre, boolean robar, double cantidad) {
        super(cuenta, nombre, robar, cantidad);
    }

    @Override
    public void run() {

        String resultado;
        boolean exito=false;

        if (robar) {
            exito = cuenta.retirar(cantidad,nombre);
            resultado = exito?"Se ha retirado correctamente "+cantidad:"Fallo al retirar "+cantidad+" eurosh ";
        }else{
            cuenta.ingresar(cantidad,nombre);
            resultado = "Deposito de "+cantidad+" exitosa en "+nombre;

        }
        System.out.println(resultado);
    }
}
