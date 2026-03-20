package cleancode;

import java.util.List;

public class Relatorio {

    public void gerar(List<Pedido> ps) {
        System.out.println("======= RELATORIO =======");

        int qtd = 0;
        double soma = 0;
        int cancelados = 0;
        int comuns = 0;
        int premiums = 0;
        int vips = 0;

        for (int i = 0; i < ps.size(); i++) {
            Pedido p = ps.get(i);
            qtd++;

            soma = soma + p.total;

            if (p.status.equals("CANCELADO")) {
                cancelados++;
            }

            if (p.cliente.tipo == 1) {
                comuns++;
            } else if (p.cliente.tipo == 2) {
                premiums++;
            } else if (p.cliente.tipo == 3) {
                vips++;
            }

            System.out.println("Pedido " + p.id + " - " + p.cliente.nome + " - " + p.total + " - " + p.status);

            for (int j = 0; j < p.itens.size(); j++) {
                Item it = p.itens.get(j);
                System.out.println("   item: " + it.nome + " qtd:" + it.qtd + " preco:" + it.preco);
            }
        }

        System.out.println("--------------------");
        System.out.println("qtd pedidos: " + qtd);
        System.out.println("valor total: " + soma);
        System.out.println("cancelados: " + cancelados);
        System.out.println("clientes comuns: " + comuns);
        System.out.println("clientes premium: " + premiums);
        System.out.println("clientes vip: " + vips);

        if (qtd > 0) {
            System.out.println("media: " + (soma / qtd));
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