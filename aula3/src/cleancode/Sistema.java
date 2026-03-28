package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner sc = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db db = new Db();

    public void run() {
        int opicao = -1;

        while (opicao != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                opicao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("erro");
                opicao = -1;
                continue;
            }

            switch (opicao) {
                case 1:
                    novoPedido();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    relelatorio();
                    break;
                case 5:
                    cancelar();
                    break;
                case 0:
                    System.out.println("fim");
                    break;
                default:
                    System.out.println("opcao invalida");
                    break;
            }
        }
        sc.close();
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String nomeCliente = sc.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipoCliente = 0;
        try {
            tipoCliente = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            tipoCliente = 1;
        }

        Cliente novoCliente = new Cliente();
        novoCliente.id = pedidos.size() + 1;
        novoCliente.nome = nomeCliente;
        novoCliente.tipo = tipoCliente;
        novoCliente.email = nomeCliente.replace(" ", "").toLowerCase() + "@email.com";

        Pedido novoPedido = new Pedido();
        novoPedido.id = pedidos.size() + 1;
        novoPedido.cliente = novoCliente;
        novoPedido.status = "NOVO";
        novoPedido.itens = new ArrayList<>();

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String nomeItem = sc.nextLine();

            System.out.println("Preco item:");
            double precoItem = 0;
            try {
                precoItem = Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                precoItem = 0;
            }

            System.out.println("Qtd:");
            int quantidadeItens = 0;
            try {
                quantidadeItens = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                quantidadeItens = 1;
            }

            Item item = new Item();
            item.nome = nomeItem;
            item.preco = precoItem;
            item.qtd = quantidadeItens;
            novoPedido.itens.add(item);

            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }

        // usa o método da classe Pedido
        double total = novoPedido.calcularSubtotal();

        // regra de desconto
        double desconto = 0;

        switch (novoCliente.tipo) {
            case 1:
                if (total > 300) desconto = 0.05;
                break;
            case 2:
                desconto = (total > 200) ? 0.10 : 0.03;
                break;
            case 3:
                desconto = 0.15;
                break;
        }

        total *= (1 - desconto);

        // frete
        if (total < 100) {
            total += 25;
        } else if (total < 300) {
            total += 15;
        }

        novoPedido.total = total;
        pedidos.add(novoPedido);
        db.save(novoPedido);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + novoPedido.id);
        System.out.println("Cliente: " + novoPedido.cliente.nome);
        System.out.println("Total: " + novoPedido.total);

        if (novoPedido.total > 500) {
            System.out.println("Pedido importante!!!");
        }

        // opcional: usa o método de impressão do Pedido
        novoPedido.imprimirResumo();
    }

    public void listar() {
        if (pedidos.isEmpty()) {
            System.out.println("sem pedidos");
        } else {
            for (Pedido p : pedidos) {
                System.out.println("---------------");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("email: " + p.cliente.email);
                System.out.println("tipo: " + p.cliente.tipo);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);
                System.out.println("itens:");
                for (Item it : p.itens) {
                    System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Digite o id:");
        int id = Integer.parseInt(sc.nextLine());
        boolean achou = false;

        for (Pedido p : pedidos) {
            if (p.id == id) {
                achou = true;
                System.out.println("Pedido encontrado");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);

                double subtotal = 0;
                for (Item it : p.itens) {
                    subtotal += it.Subtotal();
                }
                System.out.println("subtotal calculado novamente: " + subtotal);

                switch (p.cliente.tipo) {
                    case 1:
                        System.out.println("cliente comum");
                        break;
                    case 2:
                        System.out.println("cliente premium");
                        break;
                    case 3:
                        System.out.println("cliente vip");
                        break;
                    default:
                        System.out.println("cliente desconhecido");
                        break;
                }

                int num = 1;
                for (Item it : p.itens) {
                    System.out.println("item " + num + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                    num++;
                }
            }
        }

        if (!achou) {
            System.out.println("nao achou");
        }
    }

    public void relelatorio() {
        Relatorio r = new Relatorio();
        r.gerar(pedidos);
    }

    public void cancelar() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(sc.nextLine());

        for (Pedido p : pedidos) {
            if (p.id == id) {
                if (p.status.equals("CANCELADO")) {
                    System.out.println("ja cancelado");
                } else {
                    p.status = "CANCELADO";
                    System.out.println("Pedido cancelado !!!");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }
}
