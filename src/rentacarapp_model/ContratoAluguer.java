package rentacarapp_model;

import helpers.dataHelper;
import java.util.*;
import terceiros.AutorizacaoDebitoCartaoCredito;

public class ContratoAluguer {

    Date m_dataInicioContrato;
    Automovel m_automovel;
    Date m_dataDevolucaoPrevista;
    List<LinhaServicoAdicional> m_listLinhaServicoAdicional;
    LocalDevolucao m_localDevolucao;
    Cliente m_cliente;
    List<CondutorAutorizado> m_listCondutorAutorizado;
    AutorizacaoDebitoCartaoCredito m_autorizacaoDebitoCartaoCredito;
    
    private int m_politicaDesconto;

    public ContratoAluguer() {
        m_dataInicioContrato = getDataInicioContratoDefault();
        m_listLinhaServicoAdicional = new ArrayList<LinhaServicoAdicional>();
        m_listCondutorAutorizado = new ArrayList<CondutorAutorizado>();
        m_politicaDesconto = -1; // não há desconto até ser indicado o contrário
    }

    private Date getDataInicioContratoDefault() {
        Calendar calUpdated = Calendar.getInstance();
        calUpdated.set(calUpdated.get(Calendar.YEAR), calUpdated.get(Calendar.MONTH), calUpdated.get(Calendar.DATE), 0, 0, 0);
        return calUpdated.getTime();
    }

    public void setAutomovel(Automovel a) {
        m_automovel = a;
    }

    public void setLocalDevolucao(LocalDevolucao ld) {
        m_localDevolucao = ld;
    }

    public void setDataDevolucaoPrevista(Date ddp) {
        m_dataDevolucaoPrevista = ddp;
    }

    public void setCliente(Cliente cliente) {
        m_cliente = cliente;
    }

    public boolean addServicoAdicional(ServicoAdicional sa, int qtd) {
        boolean bRet = false;
        if (qtd > 0) {
            if (sa.isAcumulavel()) {
                LinhaServicoAdicional lsa = new LinhaServicoAdicional(sa, qtd);
                m_listLinhaServicoAdicional.add(lsa);
                bRet = true;
            } else {
                if (qtd == 1) {
                    if (!existeLSA(sa)) {
                        LinhaServicoAdicional lsa = new LinhaServicoAdicional(sa, qtd);
                        m_listLinhaServicoAdicional.add(lsa);
                        bRet = true;
                    }
                }
            }
        }
        return bRet;

    }

    public Automovel getAutomovel() {
        return m_automovel;
    }

    public Date getDataDevolucaoPrevista() {
        return m_dataDevolucaoPrevista;
    }

    public List<LinhaServicoAdicional> getListLinhaServicoAdicional() {
        return m_listLinhaServicoAdicional;
    }

    public LocalDevolucao getLocalDevolucao() {
        return m_localDevolucao;
    }

    /*
     * Calcula a duração do contrato (para efeitos de estratégias de cálculo)
     */
    public int getDiasDuracaoContrato() {
        return calculaNrDias();
    }

    public float getValorTotal() {
        // valor diário do automóvel
        float valorAutomovel = this.m_automovel.getPrecoDiario();

        // valor total dos serviços adicionais
        float valorServicosAdicionais = this.getValorTotalServicoAdicional();

        return (valorAutomovel * this.getDiasDuracaoContrato()) + valorServicosAdicionais;
    }

    /*
     * Calcula o valor total dos servicos adicionais contratados
     */
    public float getValorTotalServicoAdicional() {
        float valorLSA = 0;

        for (int i = 0; i < m_listLinhaServicoAdicional.size(); i++) {
            LinhaServicoAdicional lsa = m_listLinhaServicoAdicional.get(i);
            valorLSA += lsa.getValorLinha();
        }

        return valorLSA;
    }

    public List<CondutorAutorizado> getListCondutorAutorizado() {
        return m_listCondutorAutorizado;
    }

    public void addCondutorAutorizado(String strNome, String strEndereco, String strNumero, String strValidade) {
        CondutorAutorizado ca = new CondutorAutorizado(strNome, strEndereco, strNumero, strValidade);

        m_listCondutorAutorizado.add(ca);
    }

    public float getValorAutorizacaoDebito() {
        float vTotal = getValorTotalDesconto();
        float valor1Dia = (float) vTotal / calculaNrDias();
        float vADCC = vTotal * 0.25f;
        if (vADCC < valor1Dia) {
            vADCC = valor1Dia;
        }
        return vADCC;
    }

    public void setAutorizacaoCartaoCredito(AutorizacaoDebitoCartaoCredito adcc) {
        m_autorizacaoDebitoCartaoCredito = adcc;
    }

    public AutorizacaoDebitoCartaoCredito getAutorizacaoDebitoCartaoCredito() {
        return m_autorizacaoDebitoCartaoCredito;
    }

    @Override
    public String toString() {
        String str = "Automóvel: " + getAutomovel().toString() + "\n";
        str += "Data de devolução prevista: " + getDataDevolucaoPrevista().toString() + "\n";
        str += "Local de devolucao: " + getLocalDevolucao() + "\n";

        str += "Serviços Adicionais:\n";
        for (Iterator<LinhaServicoAdicional> i = getListLinhaServicoAdicional().iterator(); i.hasNext();) {
            LinhaServicoAdicional lsa = i.next();
            str += "\t" + lsa.toString() + "\n";
        }

        str += "Cliente:\n";
        str += "\tID: " + m_cliente.getID() + "\n";
        str += "\tNome: " + m_cliente.getNome() + "\n";
        str += "\tEndereço: " + m_cliente.getEndereco() + "\n";
        str += "\tEmail: " + m_cliente.getEmail() + "\n";
        str += "\tTelefone: " + m_cliente.getTelefone() + "\n";
        str += "\tObservações: " + m_cliente.getObservacoes() + "\n";

        str += "Condutores Autorizados:\n";
        for (Iterator<CondutorAutorizado> i = getListCondutorAutorizado().iterator(); i.hasNext();) {
            CondutorAutorizado ca = i.next();
            str += "\tNome: " + ca.getNome() + "\n";
            str += "\tEndereço: " + ca.getEndereco() + "\n";
            str += "\tNúmero de carta de condução: " + ca.getNumeroCartaConducao() + "\n";
            str += "\tValidade de carta de condução: " + ca.getValidadeCartaConducao() + "\n";
        }
        str += "\tValor Total Contrato: " + this.getValorTotal();
        str += "\n\tAutorização Débito em Cartão de Crédito:\n";
        str += "\tCartão nº: " + this.m_autorizacaoDebitoCartaoCredito.getNumeroCartao() + "\n";
        str += "\tValidade: " + this.m_autorizacaoDebitoCartaoCredito.getValidadeCartao() + "\n";
        str += "\tValor autorizado de débito: " + this.m_autorizacaoDebitoCartaoCredito.getValorAutorizado() + "\n";

        return str;
    }

    private int calculaNrDias() {
        return dataHelper.diferencaDatas(m_dataInicioContrato, m_dataDevolucaoPrevista);
    }

    private boolean existeLSA(ServicoAdicional sa) {
        for (Iterator<LinhaServicoAdicional> i = getListLinhaServicoAdicional().iterator(); i.hasNext();) {
            LinhaServicoAdicional lsa = i.next();
            if (lsa.getServicoAdicional().equals(sa)) {
                return true;
            }
        }
        return false;
    }

    public Cliente getCliente() {
        return this.m_cliente;
    }

    public Date getDataInicioContrato() {
        return m_dataInicioContrato;
    }

    public int getPoliticaDesconto() {
        return m_politicaDesconto;
    }

    public void setPoliticaDesconto(int m_politicaDesconto) {
        this.m_politicaDesconto = m_politicaDesconto;
    }
    
    public float getValorTotalDesconto() {
        if (m_politicaDesconto == -1) {
            return getValorTotal();
        }
        
        if (m_politicaDesconto == 0) {
            /*
             * calcula o desconto mais favorável ao cliente, de acordo com as
             * políticas de desconto
             */
            CalculoContrato_StrategyOptimized calculoContratoOptimized = new CalculoContrato_StrategyOptimized();
            calculoContratoOptimized.add(new CalculoContrato_Strategy1());
            calculoContratoOptimized.add(new CalculoContrato_Strategy2());

            return calculoContratoOptimized.getValorTotal(this);
        } else {
            /*
             * calcula o desconto definido pelo funcionário, caso seja
             * obrigatório escolher uma certa política de desconto
             */
            CalculoContratoFactory calcFact = CalculoContratoFactory.getInstance();
            CalculoContratoStrategy estrategiaCalculo = calcFact.getEstrategiaCalculo(m_politicaDesconto);
            return estrategiaCalculo.getValorTotal(this);
        }
    }
}
