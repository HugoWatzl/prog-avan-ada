package cleancode;

import java.util.List;

public class Pedido {

    public int id;
    public Cliente cliente;
    public List<Item> itens;
    public double total;
    public String status;

    public double calcularSubtotal() {
        double subTotal = 0;
        for (Item it : itens) {
            subTotal += it.Subtotal();
        }
        return subTotal;
    }


    public void imprimirResumo() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.nome);
        for (Item it : itens) {
            System.out.println(it.nome);
        }
        System.out.println("Total: " + total);
    }


}