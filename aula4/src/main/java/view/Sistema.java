package view;

import model.*;
import repository.PedidoRepository;
import service.*;
import java.util.Scanner;

public class Sistema {
    private Scanner sc = new Scanner(System.in);
    private PedidoRepository repository;
    private CalculadoraPedido calculadora;

    public Sistema(PedidoRepository repository, CalculadoraPedido calculadora) {
        this.repository = repository;
        this.calculadora = calculadora;
    }

    public void run() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("erro");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1 -> novoPedido();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> relelatorio();
                case 5 -> cancelar();
                case 0 -> System.out.println("fim");
                default -> System.out.println("opcao invalida");
            }
        }
    }

    private void novoPedido() {
        System.out.println("Nome cliente:");
        String nome = sc.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipo = 1;
        try { tipo = Integer.parseInt(sc.nextLine()); } catch (Exception e) { System.out.println("tipo errado, vai comum"); }

        EstrategiaDesconto strategy = switch (tipo) {
            case 2 -> new EstrategiaDescontoPremium();
            case 3 -> new EstrategiaDescontoVip();
            default -> new EstrategiaDescontoComum();
        };

        Cliente cliente = new Cliente(repository.buscarTodos().size() + 1, nome, strategy);
        Pedido pedido = new Pedido();
        pedido.id = repository.buscarTodos().size() + 1;
        pedido.cliente = cliente;

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            Item item = new Item();
            System.out.println("Nome item:");
            item.nome = sc.nextLine();
            System.out.println("Preco item:");
            item.preco = Double.parseDouble(sc.nextLine());
            System.out.println("Qtd:");
            item.qtd = Integer.parseInt(sc.nextLine());
            pedido.itens.add(item);

            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }

        calculadora.processar(pedido);
        repository.salvar(pedido);

        System.out.println("Pedido criado com sucesso. Total: " + pedido.total);
        if (pedido.total > 500) System.out.println("Pedido importante!!!");
    }

    private void listar() {
        var pedidos = repository.buscarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("sem pedidos");
        } else {
            for (Pedido p : pedidos) {
                System.out.println("ID: " + p.id + " | Cliente: " + p.cliente.nome + " | Total: " + p.total + " | Status: " + p.status);
            }
        }
    }

    private void buscar() {
        System.out.println("Digite o id:");
        int id = Integer.parseInt(sc.nextLine());
        Pedido p = repository.buscarPorId(id);
        if (p != null) {
            System.out.println("Pedido encontrado. Total: " + p.total + " Status: " + p.status);
        } else {
            System.out.println("nao achou");
        }
    }


    public void relelatorio() {
        Relatorio r = new Relatorio();
        r.gerar(repository.buscarTodos());
    }

    private void cancelar() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(sc.nextLine());
        Pedido p = repository.buscarPorId(id);
        if (p != null) {
            p.status = "CANCELADO";
            System.out.println("Pedido cancelado !!!");
        } else {
            System.out.println("pedido nao existe");
        }
    }
}