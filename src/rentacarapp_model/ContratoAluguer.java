package rentacarapp_model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import terceiros.AutorizacaoDebitoCartaoCredito;

public class ContratoAluguer
{

    Date m_dataInicioContrato;
    Automovel m_automovel;
    Date m_dataDevolucaoPrevista;
    List<LinhaServicoAdicional> m_listLinhaServicoAdicional;
    LocalDevolucao m_localDevolucao;
    Cliente m_cliente;
    List<CondutorAutorizado> m_listCondutorAutorizado;
    
    AutorizacaoDebitoCartaoCredito m_autorizacaoDebitoCartaoCredito;

    public ContratoAluguer()
    {        
        m_dataInicioContrato = getDataInicioContratoDefault();
        m_listLinhaServicoAdicional = new ArrayList<LinhaServicoAdicional>();
        m_listCondutorAutorizado = new ArrayList<CondutorAutorizado>();
    }

    private Date getDataInicioContratoDefault()
    {
        Calendar calUpdated = Calendar.getInstance();
        calUpdated.set(calUpdated.get(Calendar.YEAR), calUpdated.get(Calendar.MONTH), calUpdated.get(Calendar.DATE), 0, 0, 0);
        return calUpdated.getTime();
    }

    public void setAutomovel(Automovel a)
    {
        m_automovel = a;
    }

    public void setLocalDevolucao(LocalDevolucao ld)
    {
        m_localDevolucao = ld;
    }

    public void setDataDevolucaoPrevista(Date ddp)
    {
        m_dataDevolucaoPrevista = ddp;
    }

    public void setCliente(Cliente cliente)
    {
        m_cliente = cliente;
    }
   

    public boolean addServicoAdicional(ServicoAdicional sa, int qtd)
    {
        boolean bRet = false;
        if (qtd > 0)
        {
            if (sa.isAcumulavel())
            {
                LinhaServicoAdicional lsa = new LinhaServicoAdicional(sa, qtd);
                m_listLinhaServicoAdicional.add(lsa);
                bRet = true;
            }
            else
            {
                if (qtd == 1)
                {
                    if (!existeLSA(sa))
                    {
                        LinhaServicoAdicional lsa = new LinhaServicoAdicional(sa, qtd);
                        m_listLinhaServicoAdicional.add(lsa);
                        bRet = true;
                    }
                }
            }
        }
        return bRet;

    }

    public Automovel getAutomovel()
    {
        return m_automovel;
    }

    public Date getDataDevolucaoPrevista()
    {
        return m_dataDevolucaoPrevista;
    }

    public List<LinhaServicoAdicional> getListLinhaServicoAdicional()
    {
        return m_listLinhaServicoAdicional;
    }

    public LocalDevolucao getLocalDevolucao()
    {
        return m_localDevolucao;
    }

    public float getValorTotal()
    {
        float valorAuto = this.m_automovel.getPrecoDiario();
        long nrDias = calculaNrDias();
        float valorLSA = 0;

        for(int i=0;i<m_listLinhaServicoAdicional.size();i++)
        {
            LinhaServicoAdicional lsa = m_listLinhaServicoAdicional.get(i);
            valorLSA += lsa.getValorLinha();
        }
        return (nrDias * (valorAuto+valorLSA));
    }

    public List<CondutorAutorizado> getListCondutorAutorizado()
    {
        return m_listCondutorAutorizado;
    }

    public void addCondutorAutorizado(String strNome, String strEndereco, String strNumero, String strValidade)
    {
        CondutorAutorizado ca = new CondutorAutorizado(strNome, strEndereco, strNumero, strValidade);

        m_listCondutorAutorizado.add(ca);
    }

    public float getValorAutorizacaoDebito()
    {
        float vTotal = getValorTotal();
        float valor1Dia = vTotal/calculaNrDias();
        float vADCC = vTotal * 0.25f;
        if (vADCC < valor1Dia)
            vADCC = valor1Dia;
        return vADCC;
    }

    public void setAutorizacaoCartaoCredito(AutorizacaoDebitoCartaoCredito adcc)
    {
        m_autorizacaoDebitoCartaoCredito = adcc;
    }

    public AutorizacaoDebitoCartaoCredito getAutorizacaoDebitoCartaoCredito()
    {
        return m_autorizacaoDebitoCartaoCredito;
    }

    @Override
    public String toString()
    {
        String str = "Automóvel: " + getAutomovel().toString() + "\n";
        str += "Data de devolução prevista: " + getDataDevolucaoPrevista().toString() + "\n";
        str += "Local de devolucao: " + getLocalDevolucao() + "\n";

        str += "Serviços Adicionais:\n";
        for (Iterator<LinhaServicoAdicional> i = getListLinhaServicoAdicional().iterator(); i.hasNext();)
        {
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
        for (Iterator<CondutorAutorizado> i = getListCondutorAutorizado().iterator(); i.hasNext();)
        {
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

    private long calculaNrDias()
    {
         long diasInicio = m_dataInicioContrato.getTime();
         long diasFim = m_dataDevolucaoPrevista.getTime();
         long UmDia = 24 * 60 * 60 * 1000; // Em milisegundos

         return Math.round((diasFim-diasInicio*1f)/UmDia)+1;

    }

    private boolean existeLSA(ServicoAdicional sa)
    {
        for (Iterator<LinhaServicoAdicional> i = getListLinhaServicoAdicional().iterator(); i.hasNext();)
        {
            LinhaServicoAdicional lsa = i.next();
            if (lsa.getServicoAdicional().equals(sa))
                return true;
        }
        return false;
    }

    public Cliente getCliente()
    {
        return this.m_cliente;
    }
}

