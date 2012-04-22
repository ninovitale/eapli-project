package rentacarapp_ui;

import rentacarapp_controller.ElaborarContratoController;
import rentacarapp_model.*;
import utils.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nuno Silva
 */
public class ElaborarContratoUI
{
    private Empresa m_empresa;
    private ElaborarContratoController m_controllerEC;

    public ElaborarContratoUI( Empresa empresa )
    {
        m_empresa = empresa;
        m_controllerEC = new ElaborarContratoController(m_empresa);
    }

    public void run()
    {
        iniciaContratoAluguer();

        GrupoAutomovel ga = seleccionaGrupoAutomovel();

        // especificar o automóvel
        Automovel auto = seleccionaAutomovel(ga);
        m_controllerEC.setAutomovel(auto);

        // especificar a data de devolução
        Date ddp = indicaDataDevolucaoPrevista();
        m_controllerEC.setDataDevolucaoPrevista(ddp);

        // especificar os serviços adicionais
        seleccionaServicosAdicionais();

        // especificar o local de devolução
        LocalDevolucao ld = seleccionaLocalDevolucao();
        m_controllerEC.setLocalDevolucao(ld);

        float fTotal = m_controllerEC.getValorTotalContrato();
        System.out.println("Valor total do contrato: " + fTotal);

        // especificar o cliente
        introduzCliente();

        introduzCondutoresAutorizados();

        introduzDadosCartaoCredito();

        imprimeAutorizacaoDebitoCartaoCredito();

        imprimeContrato();

        imprimeComprovativoEntrega();

        terminaElaboracaoContratoAluguer();
    }

    private void iniciaContratoAluguer()
    {
        m_controllerEC.iniciaContratoAluguer();
    }

    private GrupoAutomovel seleccionaGrupoAutomovel()
    {
        int index = 0;
        List<GrupoAutomovel> listGA = m_controllerEC.getCatalogoGrupoAutomovel();

        System.out.println("Catálogo Grupo Automóvel:");
        System.out.println("Grupo, Nº portas, Classe, Combustivel, AC(S/N)");
        for (Iterator<GrupoAutomovel> i = listGA.iterator(); i.hasNext();)
        {
            GrupoAutomovel ga = i.next();
            index++;

            System.out.println(index + ". " + ga.toString());
        }

        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 1 || nOpcao > listGA.size());

        return listGA.get(nOpcao - 1);
    }

    private Automovel seleccionaAutomovel(GrupoAutomovel ga)
    {
        int index = 0;
        List<Automovel> listFrotaByGA = m_controllerEC.getFrotaByGrupoAutomovel(ga);

        System.out.println("Automóveis do Grupo Automóvel " + ga.getNome());

        for (Iterator<Automovel> i = listFrotaByGA.iterator(); i.hasNext();)
        {
            Automovel auto = i.next();
            index++;

            System.out.println(index + ". " + auto.toString());
        }

        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 1 || nOpcao > listFrotaByGA.size());

        return listFrotaByGA.get(nOpcao - 1);
    }

    private Date indicaDataDevolucaoPrevista()
    {
        Date today = new Date();
        Date ddp;

        do
        {
            ddp = Utils.readDateFromConsole("Introduza a data de devolução prevista (dd-mm-aaaa): ");
        } while (ddp.before(today));

        return ddp;
    }

    private void seleccionaServicosAdicionais()
    {
        ServicoAdicional sa;
        do
        {
            sa = seleccionaServicoAdicional();
            if (sa != null)
            {
                int qtd = 1;

                if (sa.isAcumulavel())
                {
                    do
                    {
                        qtd = Utils.readIntegetFromConsole("Quantidade:");
                    }while(qtd<1);
                }
                if (!m_controllerEC.addServicoAdicional(sa,qtd))
                    System.out.println("Erro: Regra de dominio violada. Servico Adicional não registado.");
            }
        } while (sa != null);
    }

    private ServicoAdicional seleccionaServicoAdicional()
    {
        int index = 0;
        List<ServicoAdicional> listServicoAdicional = m_controllerEC.getCatalogoServicoAdicional();

        System.out.println("Serviços Adicionais:");

        for (Iterator<ServicoAdicional> i = listServicoAdicional.iterator(); i.hasNext();)
        {
            ServicoAdicional sa = i.next();
            index++;

            System.out.println(index + ". " + sa.toString());
        }
        System.out.println("0. mais nenhum");

        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > listServicoAdicional.size());

        if (nOpcao == 0)
        {
            return null;
        } else
        {
            return listServicoAdicional.get(nOpcao - 1);
        }
    }

    private LocalDevolucao seleccionaLocalDevolucao()
    {
        int index = 0;
        List<LocalDevolucao> listLD = m_controllerEC.getCatalogoLocalDevolucao();

        System.out.println("Catálogo Local de Devolução:");
        for (Iterator<LocalDevolucao> i = listLD.iterator(); i.hasNext();)
        {
            LocalDevolucao ld = i.next();
            index++;

            System.out.println(index + ". " + ld.toString());
        }

        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 1 || nOpcao > listLD.size());

        return listLD.get(nOpcao - 1);
    }

    private void introduzCliente()
    {
        Cliente c = null;
        int nOpcao=1;
        while (c==null)
        {
            do
            {
                System.out.println("1 - Pesquisar Cliente por BI/Passaporte");
                System.out.println("2 - Pesquisar Cliente por Nome");
                System.out.println("3 - Registar Cliente");
                nOpcao = Utils.readIntegetFromConsole("Introduza opção: ");
            } while (nOpcao < 1 || nOpcao > 3);
            switch(nOpcao)
            {
                case 1: c=pesquisaClienteByID();
                    break;
                case 2: c=pesquisaClienteByNome();
                    break;
                case 3: c=registarCliente();
                    break;
            }
        }
        if (nOpcao != 3)
        {
            boolean alterar = Utils.readBooleanFromConsole("Deseja alterar os dados actuais do cliente seleccionado? (s/n)");
            if (alterar)
            {
                System.out.println("Chamar a opção de alteração de dados do cliente");
            }
        }
        m_controllerEC.setCliente(c);
    }

    private Cliente registarCliente()
    {
        RegistarClienteUI registarClienteUI = new RegistarClienteUI(this.m_empresa);
        return registarClienteUI.run();
    }

    private Cliente pesquisaClienteByID()
    {
        boolean bContinuar = true;
        Cliente c = null;
        do{
            String strPesq = Utils.readLineFromConsole("Introduza o BI/Passaporte do cliente: ");
            c = m_controllerEC.getClienteByID(strPesq);
            if ( c == null)
            {
                System.out.println("Cliente não encontrado.");
                bContinuar = Utils.readBooleanFromConsole("Pesquisar novamente pelo ID? (s/n)");
            }
            else
            {
                System.out.println("Cliente encontrado:");
                System.out.println(c.toString());
                bContinuar = !Utils.readBooleanFromConsole("O cliente encontrado é o pretendido? (s/n)");
            }
        }while(bContinuar);
        return c;
    }

    private Cliente pesquisaClienteByNome()
    {
        int nOpcao=0;
        int index = 0;
        Cliente c = null;
        do{
            String strPesq = Utils.readLineFromConsole("Introduza o Nome (parcial) do cliente: ");
            List<Cliente> listaC = m_controllerEC.getClienteByNome(strPesq);
            index = 0;
            System.out.println("0. Pesquisar novamente");
            for (Iterator<Cliente> i = listaC.iterator(); i.hasNext();)
            {
                Cliente cliente = i.next();
                index++;
                System.out.println(index + ". " + cliente.toString());
            }
            System.out.println(++index + ". Voltar." );
            do
            {
                nOpcao = Utils.readIntegetFromConsole("Introduza opção: ");
            } while (nOpcao < 0 || nOpcao > index);
            if ((nOpcao > 0) && (nOpcao < index))
                c = listaC.get(nOpcao -1);
        }while((c==null) && (nOpcao<index));
        return c;
    }

    private void introduzCondutoresAutorizados()
    {
        String strResposta;
        boolean bPrimeiro=true;
        boolean bLeNome=true;
        String strNome="";
        String strEndereco="";
        String strNumero="";
        String strValidade="";
        do
        {
            bLeNome=true;
            if (bPrimeiro)
            {
                if (Utils.readBooleanFromConsole("O cliente seleccionado é um condutor autorizado?(s/n)"))
                {
                    Cliente c = m_controllerEC.getClienteContrato();
                    strNome = c.getNome();
                    strEndereco = c.getEndereco();
                    bLeNome=false;
                }
            }
            if (bLeNome)
            {
                strNome = Utils.readLineFromConsole("Introduza Nome do condutor: ");
                strEndereco = Utils.readLineFromConsole("Introduza Endereço do condutor: ");
            }
            strNumero = Utils.readLineFromConsole("Introduza Número da carta de condução: ");
            strValidade = Utils.readLineFromConsole("Introduza Validade da carta de condução: ");

            m_controllerEC.addCondutorAutorizado(strNome,strEndereco,strNumero,strValidade);

            bPrimeiro = false;
            strResposta = Utils.readLineFromConsole("Introduzir outro condutor autorizado (S/N)? ");
        } while (strResposta.equalsIgnoreCase("S"));
    }

    private void introduzDadosCartaoCredito()
    {
        String strNumero = Utils.readLineFromConsole("Introduza Número do cartão de crédito: ");
        String strValidade = Utils.readLineFromConsole("Introduza Validade do cartão de crédito: ");
        m_controllerEC.setDadosCartaoCredito(strNumero,strValidade);
    }

    private void imprimeAutorizacaoDebitoCartaoCredito()
    {
        System.out.println("Imprimindo autorização de débito em cartão de crédito...");
        
        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Autorização de débito em cartão de crédito não impresso. É necessário clarificar processo com o cliente.");
    }

    private void imprimeContrato()
    {
        System.out.println("Imprimindo 2 cópias do Contrato de Aluguer...");

        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Contrato de Aluguer não impresso. É necessário clarificar processo com o cliente.");
    }

    private void imprimeComprovativoEntrega()
    {
        System.out.println("Imprimindo 2 cópias do comprovativo de entrega...");

        Logger.getLogger("Rent-a-car").log(Level.WARNING, "Comprovativo de entrega não impresso. É necessário clarificar processo com o cliente.");
    }

    private void terminaElaboracaoContratoAluguer()
    {
        m_controllerEC.terminaElaboracaoContratoAluguer();
    }
}