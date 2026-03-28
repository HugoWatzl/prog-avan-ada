package main;
import repository.*;
import service.*;
import view.Sistema;

public class Main {
    public static void main(String[] args) {

        PedidoRepository repository = new Db();
        CalculadoraPedido calculadora = new CalculadoraPedido();

        Sistema s = new Sistema(repository, calculadora);
        s.run();
    }
}