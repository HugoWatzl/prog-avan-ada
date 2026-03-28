package model;

public class Item {
    public String nome;
    public double preco;
    public int qtd;

    public double getSubtotal() {
        return preco * qtd;
    }
}