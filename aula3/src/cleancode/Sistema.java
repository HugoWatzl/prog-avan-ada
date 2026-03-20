package cleancode;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    public class Sistema {

        Scanner sc = new Scanner(System.in);
        List<Pedido> pedidos = new ArrayList<>();
        Db db = new Db();

        public void run() {
            int opicao = -1; //opicao

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
                }

                if (opicao == 1) {
                    novoPedido();
                } else if (opicao == 2) { // trasformar em switch
                    listar();
                } else if (opicao == 3) {
                    buscar();
                } else if (opicao == 4) {
                    rel();
                } else if (opicao == 5) {
                    cancelar();
                } else if (opicao == 0) {
                    System.out.println("fim");
                } else {
                    System.out.println("opcao invalida");
                }
            }
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

            double total = 0;
            for (int i = 0; i < novoPedido.itens.size(); i++) {
                total = total + (novoPedido.itens.get(i).preco * novoPedido.itens.get(i).qtd);
            }

            // regra de desconto
            if (novoCliente.tipo == 1) {
                if (total > 300) {
                    total = total - (total * 0.05);
                }
            } else if (novoCliente.tipo == 2) {
                if (total > 200) {
                    total = total - (total * 0.10);
                } else {
                    total = total - (total * 0.03);
                }
            } else if (novoCliente.tipo == 3) {
                total = total - (total * 0.15);
            } else {
                total = total;
            }

            // frete
            if (total < 100) {
                total = total + 25;
            } else if (total >= 100 && total < 300) {
                total = total + 15;
            } else {
                total = total + 0;
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
        }

        public void listar() {
            if (pedidos.size() == 0) {
                System.out.println("sem pedidos");
            } else {
                for (int i = 0; i < pedidos.size(); i++) {
                    Pedido p = pedidos.get(i);
                    System.out.println("---------------");
                    System.out.println("id: " + p.id);
                    System.out.println("cliente: " + p.cliente.nome);
                    System.out.println("email: " + p.cliente.email);
                    System.out.println("tipo: " + p.cliente.tipo);
                    System.out.println("status: " + p.status);
                    System.out.println("total: " + p.total);
                    System.out.println("itens:");
                    for (int j = 0; j < p.itens.size(); j++) {
                        Item it = p.itens.get(j);
                        System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
                    }
                }
            }
        }

        public void buscar() {
            System.out.println("Digite o id:");
            int id = Integer.parseInt(sc.nextLine());
            boolean achou = false;

            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                if (p.id == id) {
                    achou = true;
                    System.out.println("Pedido encontrado");
                    System.out.println("id: " + p.id);
                    System.out.println("cliente: " + p.cliente.nome);
                    System.out.println("status: " + p.status);
                    System.out.println("total: " + p.total);

                    double subtotal = 0;
                    for (int j = 0; j < p.itens.size(); j++) {
                        subtotal = subtotal + (p.itens.get(j).preco * p.itens.get(j).qtd);
                    }
                    System.out.println("subtotal calculado novamente: " + subtotal);

                    if (p.cliente.tipo == 1) {
                        System.out.println("cliente comum");
                    } else if (p.cliente.tipo == 2) {
                        System.out.println("cliente premium");
                    } else if (p.cliente.tipo == 3) {
                        System.out.println("cliente vip");
                    } else {
                        System.out.println("cliente desconhecido");
                    }

                    for (int j = 0; j < p.itens.size(); j++) {
                        Item it = p.itens.get(j);
                        System.out.println("item " + (j + 1) + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                    }
                }
            }

            if (achou == false) {
                System.out.println("nao achou");
            }
        }

        public void rel() {
            Relatorio r = new Relatorio();
            r.gerar(pedidos);
        }

        public void cancelar() {
            System.out.println("Digite id do pedido");
            int id = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < pedidos.size(); i++) {
                if (pedidos.get(i).id == id) {
                    if (pedidos.get(i).status.equals("CANCELADO")) {
                        System.out.println("ja cancelado");
                    } else {
                        pedidos.get(i).status = "CANCELADO";
                        System.out.println("cancelado");
                    }
                    return;
                }
            }

            System.out.println("pedido nao existe");
        }
    }


