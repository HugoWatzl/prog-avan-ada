package cleancode;

import java.util.List;

public class Relatorio {

    public void gerar(List<Pedido> pedidos) {
        System.out.println("======= RELATORIO =======");

        int quantidade = 0;
        double soma = 0;
        int cancelados = 0;
        int comuns = 0;
        int premiums = 0;
        int vips = 0;

        for (Pedido p : pedidos) {
            quantidade++;
            soma += p.total;

            if (p.status.equals("CANCELADO")) {
                cancelados++;
            }


            switch (p.cliente.tipo) {
                case 1:
                    comuns++;
                    break;
                case 2:
                    premiums++;
                    break;
                case 3:
                    vips++;
                    break;
            }


            System.out.println("Pedido " + p.id + " - " + p.cliente.nome +  " - subtotal: " + p.calcularSubtotal() + " - total: " + p.total + " - " + p.status);

            for (Item it : p.itens) {
                System.out.println("item: " + it.nome + " quantidade: " + it.qtd + " preco: " + it.preco + " subtotal: " + it.Subtotal());
            }
        }

        System.out.println("--------------------");
        System.out.println("quantidade pedidos: " + quantidade);
        System.out.println("valor total: " + soma);
        System.out.println("cancelados: " + cancelados);
        System.out.println("clientes comuns: " + comuns);
        System.out.println("clientes premium: " + premiums);
        System.out.println("clientes vip: " + vips);

        if (quantidade > 0) {
            System.out.println("media: " + (soma / quantidade));
        } else {
            System.out.println("media: 0");
        }

        if (soma > 1000) {
            System.out.println("resultado muito bom");
        } else if (soma > 500) {
            System.out.println("resultado ok");
        } else {
            System.out.println("resultado fraco");
        }
    }
}