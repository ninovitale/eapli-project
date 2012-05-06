package rentacarapp_ui;

import helpers.dataHelper;
import helpers.formatHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rentacarapp_controller.ElaborarContratoController;
import rentacarapp_model.*;
import utils.Utils;

/**
 *
 * @author Nuno Silva
 */


public class ElaborarContratoUI {
    private static Scanner in = new Scanner(System.in);
    
    private Empresa m_empresa;
    private ElaborarContratoController m_controllerEC;
    
    public ElaborarContratoUI(Empresa empresa) {
        m_empresa = empresa;
        m_controllerEC = new ElaborarContratoController(m_empresa);
    }

    public void run() throws ParseException {
        
        iniciaContratoAluguer();

        GrupoAutomovel ga = seleccionaGrupoAutomovel();

        // especificar o automóvel - verificar se o grupo automóvel contém automóveis
        Automovel auto = null;
        do {
            auto = seleccionaAutomovel(ga);
            if (auto == null) {
                ga = seleccionaGrupoAutomovel();
            }
        } while (auto == null);

        m_controllerEC.setAutomovel(auto);

        // especificar a data de devolução
        Date ddp = indicaDataDevolucaoPrevista();
        m_controllerEC.setDataDevolucaoPrevista(ddp);

        // especificar os serviços adicionais
        seleccionaServicosAdicionais();

        // especificar o local de devolução
        LocalDevolucao ld = seleccionaLocalDevolucao();
        m_controllerEC.setLocalDevolucao(ld);

        float valorContratoSemDesconto = m_controllerEC.getValorTotalContrato();
        System.out.format("Valor total do contrato (sem desconto): €%.2f%n", valorContratoSemDesconto);
        System.out.println("Prima enter para continuar...");
        in.nextLine();
        
        // especificar o cliente
        introduzCliente();
        
        // pede ao cliente a poltica de desconto
        int politicaDesconto = escolherPoliticaDesconto();
        // define a politica para futuros cálculos
        this.m_controllerEC.setPoliticaDesconto(politicaDesconto);
        // pede o valor total do contrato tendo em conta a política de desconto (seja qual for)
        float valorContratoComDesconto = m_controllerEC.getValorTotalContratoDesconto();

        if (valorContratoSemDesconto == valorContratoComDesconto) {
            if (politicaDesconto == -1) {
                System.out.format("%nNenhum desconto foi aplicado. O valor a pagar é: €%.2f%n%n", valorContratoComDesconto);
            } else {
                System.out.format("%nNão foi possível aplicar nenhum desconto a este contrato%n(ou escolheu uma política cujos condições de desconto não se aplicam a este cliente,%nou então o sistema detectou que não é elegível para qualquer desconto).%nO valor a pagar é: €%.2f%n%n", valorContratoComDesconto);
            }
        } else {
            System.out.format("%nValor total do contrato (com desconto): €%.2f (-%d%%)%n%n", valorContratoComDesconto, ((int) (100.0 - ((valorContratoComDesconto / valorContratoSemDesconto) * 100.0))));
        }

        introduzCondutoresAutorizados();

        introduzDadosCartaoCredito();

        imprimeAutorizacaoDebitoCartaoCredito();

        imprimeContrato();

        imprimeComprovativoEntrega();

        terminaElaboracaoContratoAluguer();
    }

    private void iniciaContratoAluguer() {
        m_controllerEC.iniciaContratoAluguer();
    }

    private GrupoAutomovel seleccionaGrupoAutomovel() {
        int index = 0;
        List<GrupoAutomovel> listGA = m_controllerEC.getCatalogoGrupoAutomovel();

        System.out.println("Catálogo Grupo Automóvel:");
        System.out.println("Grupo, Nº portas, Classe, Combustivel, AC(S/N), Preço diário");
        for (Iterator<GrupoAutomovel> i = listGA.iterator(); i.hasNext();) {
            GrupoAutomovel ga = i.next();
            index++;

            System.out.println(index + ". " + ga.toString());
        }

        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            try {
                nOpcao = new Integer(opcao);
            } catch (NumberFormatException nfe) {
                nOpcao = 0;
            }

            if (nOpcao < 1 || nOpcao > listGA.size()) {
                System.out.println("Opção inválida!");
            }
        } while (nOpcao < 1 || nOpcao > listGA.size());

        return listGA.get(nOpcao - 1);
    }

    private Automovel seleccionaAutomovel(GrupoAutomovel ga) {
        int index = 0;
        List<Automovel> listFrotaByGA = m_controllerEC.getFrotaByGrupoAutomovel(ga);

        if (listFrotaByGA.isEmpty()) {
            System.out.println("Este grupo automóvel não contém automóveis!");
            return null;
        }

        System.out.println("Automóveis do Grupo Automóvel " + ga.getNome());

        for (Iterator<Automovel> i = listFrotaByGA.iterator(); i.hasNext();) {
            Automovel auto = i.next();
            index++;

            System.out.println(index + ". " + auto.toString());
        }

        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            try {
                nOpcao = new Integer(opcao);
            } catch (NumberFormatException nfe) {
                nOpcao = 0;
            }

            if (nOpcao < 1 || nOpcao > listFrotaByGA.size()) {
                System.out.println("Opção inválida!");
            }
        } while (nOpcao < 1 || nOpcao > listFrotaByGA.size());

        return listFrotaByGA.get(nOpcao - 1);
    }

    private Date indicaDataDevolucaoPrevista() throws ParseException {
        Date today = new Date();
        Date ddp = new Date(0, 01, 01);
        String data;
        boolean validEntryDate;

        do {
            validEntryDate = false;
            data = Utils.readLineFromConsole("Introduza a data de devolução prevista (dd-mm-aaaa): ");
            if (!dataHelper.validaData(data)) {
                System.out.println("Data inválida, insira de novo!");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                ddp = dateFormat.parse(data);
                if (ddp.before(today)) {
                    System.out.println("Data inválida, insira uma data posterior a hoje!");
                } else validEntryDate = true;
            }
        } while (!validEntryDate);

        return ddp;
    }

    private void seleccionaServicosAdicionais() {
        ServicoAdicional sa;
        do {
            sa = seleccionaServicoAdicional();
            if (sa != null) {
                int qtd = 1;

                if (sa.isAcumulavel()) {
                    do {
                        try {
                            String quantidade = Utils.readLineFromConsole("Quantidade:");
                            qtd = Integer.parseInt(quantidade);
                        } catch (NumberFormatException nfe) {
                            qtd = -1;
                        }

                        if (qtd < 1) {
                            System.out.println("Quantidade inválida!");
                        }
                    } while (qtd < 1);
                }
                if (!m_controllerEC.addServicoAdicional(sa, qtd)) {
                    System.out.println("Erro: Regra de dominio violada. Servico Adicional não registado.");
                }
            }
        } while (sa != null);
    }

    private ServicoAdicional seleccionaServicoAdicional() {
        int index = 0;
        List<ServicoAdicional> listServicoAdicional = m_controllerEC.getCatalogoServicoAdicional();

        System.out.println("Serviços Adicionais:");

        for (Iterator<ServicoAdicional> i = listServicoAdicional.iterator(); i.hasNext();) {
            ServicoAdicional sa = i.next();
            index++;

            System.out.println(index + ". " + sa.toString());
        }
        System.out.println("0. mais nenhum");

        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            try {
                nOpcao = new Integer(opcao);
            } catch (NumberFormatException nfe) {
                nOpcao = -1;
            }

            if (nOpcao < 0 || nOpcao > listServicoAdicional.size()) {
                System.out.println("Opção inválida!");
            }
        } while (nOpcao < 0 || nOpcao > listServicoAdicional.size());

        if (nOpcao == 0) {
            return null;
        } else {
            return listServicoAdicional.get(nOpcao - 1);
        }
    }

    private LocalDevolucao seleccionaLocalDevolucao() {
        int index = 0;
        List<LocalDevolucao> listLD = m_controllerEC.getCatalogoLocalDevolucao();

        System.out.println("Catálogo Local de Devolução:");
        for (Iterator<LocalDevolucao> i = listLD.iterator(); i.hasNext();) {
            LocalDevolucao ld = i.next();
            index++;

            System.out.println(index + ". " + ld.toString());
        }

        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            try {
                nOpcao = new Integer(opcao);
            } catch (NumberFormatException nfe) {
                nOpcao = 0;
            }

            if (nOpcao < 1 || nOpcao > listLD.size()) {
                System.out.println("Opção inválida!");
            }
        } while (nOpcao < 1 || nOpcao > listLD.size());

        return listLD.get(nOpcao - 1);
    }

    private void introduzCliente() {
        Cliente c = null;
        int nOpcao = 1;
        while (c == null) {
            do {
                System.out.println("1 - Pesquisar Cliente por BI/Passaporte");
                System.out.println("2 - Pesquisar Cliente por Nome");
                System.out.println("3 - Registar Cliente");
                String opcao = Utils.readLineFromConsole("Introduza opção: ");
                try {
                    nOpcao = new Integer(opcao);
                } catch (NumberFormatException nfe) {
                    nOpcao = -1;
                }

                if (nOpcao < 1) {
                    System.out.println("Opção inválida!");
                }
            } while (nOpcao < 1 || nOpcao > 3);
            switch (nOpcao) {
                case 1:
                    c = pesquisaClienteByID();
                    break;
                case 2:
                    c = pesquisaClienteByNome();
                    break;
                case 3:
                    c = registarCliente();
                    break;
            }
        }
        if (nOpcao != 3) {
            boolean alterar = Utils.readBooleanFromConsole("Deseja alterar os dados actuais do cliente seleccionado? (s/n)");
            if (alterar) {
                System.out.println("Chamar a opção de alteração de dados do cliente");
            }
        }
        m_controllerEC.setCliente(c);
    }
    
    private int escolherPoliticaDesconto() {
        String strResposta;
        int politicaDesconto = -1;
        boolean chosen;

        do {
            chosen = false;
            strResposta = Utils.readLineFromConsole("\nDeseja aplicar desconto sobre este contrato? (S/N)");
            if (strResposta.equalsIgnoreCase("S") || strResposta.equalsIgnoreCase("N")) {
                chosen = true;
                // se não se aplica desconto
                if (strResposta.equalsIgnoreCase("N")) {
                    // então retorna o valor inicialmente atribuído (-1)
                    return politicaDesconto;
                }
            } else {
                System.out.println("Opção inválida!");
            }
        } while (!chosen);
        
        do {
                chosen = false;
                strResposta = Utils.readLineFromConsole("\nDeseja forçar uma dada política de desconto? Se escolher não, será aplicada a melhor política para o cliente actual. (S/N)");
                if((strResposta.equalsIgnoreCase("S")) || (strResposta.equalsIgnoreCase("N"))) {
                    chosen = true;
                    // se não for obrigatório, então escolhe a mais adequada ao cliente
                    if(strResposta.equalsIgnoreCase("N")) {
                        politicaDesconto = 0;
                    } else {
                        String opcao;
                        do {
                            // se for obrigatório ter certas políticas de desconto em vigor
                            System.out.println("\n1. Política de Desconto 1\n2. Política de Desconto 2");
                            opcao = Utils.readLineFromConsole("Qual a estratégia de cálculo que pretende aplicar?\n");
                            
                            try {
                                politicaDesconto = new Integer(opcao);
                            } catch (NumberFormatException nfe) {
                                politicaDesconto = -2;
                            }

                            if (politicaDesconto < 1 || politicaDesconto > 3) {
                                System.out.println("Política inválida (escolha 1, 2 ou 3)!");
                            }                            
                        } while (politicaDesconto < 1 && politicaDesconto > 3);
                    }
                } else {
                        System.out.println("Opção inválida!");
                }
        } while(!chosen);
        
        return politicaDesconto;
    }

    private Cliente registarCliente() {
        RegistarClienteUI registarClienteUI = new RegistarClienteUI(this.m_empresa);
        return registarClienteUI.run();
    }

    private Cliente pesquisaClienteByID() {
        boolean bContinuar;
        Cliente c = null;
        do {
            String strPesq = Utils.readLineFromConsole("Introduza o BI/Passaporte do cliente: ");
            c = m_controllerEC.getClienteByID(strPesq);
            if (c == null) {
                System.out.println("Cliente não encontrado.");
                bContinuar = Utils.readBooleanFromConsole("Pesquisar novamente pelo ID? (s/n)");
            } else {
                System.out.println("Cliente encontrado:");
                System.out.println(c.toString());
                bContinuar = !Utils.readBooleanFromConsole("O cliente encontrado é o pretendido? (s/n)");
            }
        } while (bContinuar);
        return c;
    }

    private Cliente pesquisaClienteByNome() {
        int nOpcao = 0;
        int index;
        Cliente c = null;
        do {
            String strPesq = Utils.readLineFromConsole("Introduza o Nome (parcial) do cliente: ");
            List<Cliente> listaC = m_controllerEC.getClienteByNome(strPesq);
            index = 0;
            System.out.println("0. Pesquisar novamente");
            for (Iterator<Cliente> i = listaC.iterator(); i.hasNext();) {
                Cliente cliente = i.next();
                index++;
                System.out.println(index + ". " + cliente.toString());
            }
            System.out.println(++index + ". Voltar.");
            do {
                String opcao = Utils.readLineFromConsole("Introduza opção: ");
            try {
                nOpcao = new Integer(opcao);
            } catch (NumberFormatException nfe) {
                nOpcao = -1;
            }

            if (nOpcao < 1 || nOpcao > index) {
                System.out.println("Opção inválida!");
            }
            } while (nOpcao < 0 || nOpcao > index);
            if ((nOpcao > 0) && (nOpcao < index)) {
                c = listaC.get(nOpcao - 1);
            }
        } while ((c == null) && (nOpcao < index));
        return c;
    }

    private void introduzCondutoresAutorizados() throws ParseException {
        String strResposta;
        boolean bPrimeiro = true;
        boolean bLeNome;
        String strNome = "";
        String strEndereco = "";
        String strNumero;
        String strValidade;
        do {
            bLeNome = true;
            if (bPrimeiro) {
                if (Utils.readBooleanFromConsole("O cliente seleccionado é um condutor autorizado?(s/n)")) {
                    Cliente c = m_controllerEC.getClienteContrato();
                    strNome = c.getNome();
                    strEndereco = c.getEndereco();
                    bLeNome = false;
                }
            }
            if (bLeNome) {
                do {
                    strNome = Utils.readLineFromConsole("Introduza Nome do condutor: ");
                    if (strNome.isEmpty()) {
                        System.out.println("O nome não pode ser vazio!");
                    }
                } while (strNome.isEmpty());

                do {
                    strEndereco = Utils.readLineFromConsole("Introduza endereço do condutor: ");
                    if (strEndereco.isEmpty()) {
                        System.out.println("O endereço não pode ser vazio!");
                    }
                } while (strEndereco.isEmpty());
            }
            
            boolean numValido;
            do {
                numValido = false;
                strNumero = Utils.readLineFromConsole("Introduza Número da carta de condução: ");
                if (strNumero.isEmpty()) {
                    System.out.println("O número não pode ser vazio.");
                } else {
                    numValido = true;
                }
            } while (!numValido);
            
            Date dataValidade;
            Date today = new Date();
            boolean cartaDataValida;
            do {
                cartaDataValida = false;
                strValidade = Utils.readLineFromConsole("Introduza Validade da carta de condução (dd-mm-aaaa): ");

                if (!dataHelper.validaData(strValidade)) {
                    System.out.println("Data inválida, insira de novo!");
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dataValidade = dateFormat.parse(strValidade);
                    if (dataValidade.before(today)) {
                        System.out.println("Data inválida, insira uma data posterior a hoje!");
                    } else cartaDataValida = true;
                }
            } while (!cartaDataValida);
            
            m_controllerEC.addCondutorAutorizado(strNome, strEndereco, strNumero, strValidade);

            bPrimeiro = false;
            strResposta = Utils.readLineFromConsole("Introduzir outro condutor autorizado (S/N)? ");
        } while (strResposta.equalsIgnoreCase("S"));
    }

    private void introduzDadosCartaoCredito() throws ParseException {
        boolean cartaoValido;
        String strNumero;
        
        do {
            cartaoValido = false;
            strNumero = Utils.readLineFromConsole("Introduza Número do cartão de crédito: ");
            if (strNumero.isEmpty() || !formatHelper.isNumber(strNumero) || strNumero.length() != 16) {
                System.out.println("O número não pode ser vazio e tem que ter 16 dígitos.");
            } else {
                cartaoValido = true;
            }
        } while (!cartaoValido);

        String strValidade;
        boolean dataValida;
        Date dataValidade;
        Date today = new Date();
        
        do {
            dataValida = false;
            strValidade = Utils.readLineFromConsole("Introduza Validade do cartão de crédito: ");
            if (!dataHelper.validaData(strValidade)) {
                System.out.println("Data inválida, insira de novo!");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dataValidade = dateFormat.parse(strValidade);
                if (dataValidade.before(today)) {
                    System.out.println("Data inválida, insira uma data posterior a hoje!");
                } else dataValida = true;
            }
        } while (!dataValida);

        m_controllerEC.setDadosCartaoCredito(strNumero, strValidade);
        System.out.println("Prima enter para imprimir a autorização de débito em cartão de crédito...");
        in.nextLine();
    }

    private void imprimeAutorizacaoDebitoCartaoCredito() {
        System.out.println("Imprimindo autorização de débito em cartão de crédito...");

        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Autorização de débito em cartão de crédito não impresso. É necessário clarificar processo com o cliente.");
    }

    private void imprimeContrato() {
        System.out.println("Imprimindo 2 cópias do Contrato de Aluguer...");

        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Contrato de Aluguer não impresso. É necessário clarificar processo com o cliente.");
    }

    private void imprimeComprovativoEntrega() {
        System.out.println("Imprimindo 2 cópias do comprovativo de entrega...");

        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Comprovativo de entrega não impresso. É necessário clarificar processo com o cliente.");
    }

    private void terminaElaboracaoContratoAluguer() {
        m_controllerEC.terminaElaboracaoContratoAluguer();
        in.nextLine();
    }

    
}