package service;
import model.Pedido;

public class CalculadoraPedido {
    public void processar(Pedido pedido) {
        double subtotal = pedido.calcularSubtotalItens();
        double desconto = pedido.cliente.getStrategy().calcular(subtotal);
        double totalComDesconto = subtotal * (1 - desconto);

        double frete = 0;
        if (totalComDesconto < 100) frete = 25;
        else if (totalComDesconto < 300) frete = 15;

        pedido.total = totalComDesconto + frete;
    }
}