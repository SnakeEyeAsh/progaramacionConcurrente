package net.CryptoScam.Objetos;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CuentaCrypto {

    private double saldo;
    //Lock es para gestionar el acceso al recurso delicado compartido
    private final ReentrantLock lock = new ReentrantLock();

    public CuentaCrypto(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public boolean retirar(double cantidad, String cajero) {
        lock.lock();
        boolean respuesta;
        try {
            if (this.saldo >= cantidad) {
                this.saldo -= cantidad;
                Thread.sleep(1000);
                System.out.println("Retirado " + cantidad + " en cajero " + cajero);
            } else {
                System.out.println("Saldo insuficiente");
                respuesta = false;
            }
            respuesta = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            respuesta = false;
        } finally {
            lock.unlock();
        }
        System.out.println();
        return respuesta;
    }

    public boolean ingresar(double cantidad, String cajero) {
        lock.lock();
        boolean respuesta;
        try {

            this.saldo -= cantidad;
            Thread.sleep(1000);
            System.out.println("Ingresada " + cantidad + " en cajero " + cajero);

            respuesta = true;

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            respuesta = false;

        } finally {
            lock.unlock();
        }
        System.out.println();
        return respuesta;
    }
    public double consultar(){
        lock.lock();
        try{
            return this.saldo;
        }finally {
            lock.unlock();
        }
    }
}
