package model;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    public int id;
    public Cliente cliente;
    public List<Item> itens = new ArrayList<>();
    public double total;
    public String status = "NOVO";

    public double calcularSubtotalItens() {
        return itens.stream().mapToDouble(Item::getSubtotal).sum();
    }
}