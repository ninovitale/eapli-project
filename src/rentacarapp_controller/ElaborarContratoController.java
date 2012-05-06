package rentacarapp_controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rentacarapp_model.*;
import terceiros.ADCC_AdapterFactory;
import terceiros.AutorizacaoDebitoCartaoCredito;
import terceiros.SistemaADCCAdapter;
import terceiros.SistemaAutorizacaoCartaoCredito;

public class ElaborarContratoController
{
    private Empresa m_empresa;
    private ContratoAluguer m_contratoAluguer;

    public ElaborarContratoController(Empresa empresa)
    {
        m_empresa = empresa;
    }
    
    public void iniciaContratoAluguer()
    {
        m_contratoAluguer = m_empresa.criaContratoAluguer();
    }

    public List<GrupoAutomovel> getCatalogoGrupoAutomovel()
    {
        return m_empresa.getCatalogoGrupoAutomovel();
    }

    public List<Automovel> getFrotaByGrupoAutomovel(GrupoAutomovel ga)
    {
        return m_empresa.getFrota().getFrotaByGrupoAutomovel(ga);
    }

    public void setAutomovel(Automovel auto)
    {
        m_contratoAluguer.setAutomovel(auto);
    }

    public void setDataDevolucaoPrevista(Date ddp)
    {
        m_contratoAluguer.setDataDevolucaoPrevista(ddp);
    }

    public Cliente getClienteContrato()
    {
        return this.m_contratoAluguer.getCliente();
    }

    public Cliente getClienteByID(String strID)
    {
        return m_empresa.getRegistoClientes().getClienteByID(strID);
    }

    public List<Cliente> getClienteByNome(String strNome)
    {
        return m_empresa.getRegistoClientes().getClientesByNome(strNome);
    }

    public void setCliente(Cliente cliente)
    {
        m_contratoAluguer.setCliente(cliente);
    }

    public List<ServicoAdicional> getCatalogoServicoAdicional()
    {
        return m_empresa.getCatalogoServicoAdicional();
    }

    public boolean addServicoAdicional(ServicoAdicional sa, int qtd)
    {
        return m_contratoAluguer.addServicoAdicional(sa,qtd);
    }

    public List<LocalDevolucao> getCatalogoLocalDevolucao()
    {
        return m_empresa.getCatalogoLocalDevolucao();
    }

    public void setLocalDevolucao(LocalDevolucao ld)
    {
        m_contratoAluguer.setLocalDevolucao(ld);
    }

    public float getValorTotalContrato()
    {
        return m_contratoAluguer.getValorTotal();
    }
    
    public void setPoliticaDesconto(int politicaDesconto) {
            this.m_contratoAluguer.setPoliticaDesconto(politicaDesconto);
    }

    public float getValorTotalContratoDesconto() {
        return m_contratoAluguer.getValorTotalDesconto();
    }

    public void addCondutorAutorizado(String strNome, String strEndereco, String strNumero, String strValidade)
    {
        m_contratoAluguer.addCondutorAutorizado(strNome, strEndereco, strNumero, strValidade);
    }

    public void setDadosCartaoCredito(String strNumero, String strValidade)
    {
        float fValorCaucao = m_contratoAluguer.getValorAutorizacaoDebito();
        
        ADCC_AdapterFactory adapterFactoryInstance = ADCC_AdapterFactory.getInstance();
        
        SistemaADCCAdapter adcc;
        boolean sistemaValido;
        do {
            sistemaValido = false;
            adcc = adapterFactoryInstance.getADCCAdapter();
            if (adcc == null) System.out.println("Sistema externo inválido.");
            else sistemaValido = true;
        } while (!sistemaValido);
        
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(m_contratoAluguer.getDataDevolucaoPrevista());
        dataCalendar.add(Calendar.DATE, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        adcc.getAutorizacao("EAPLI", strNumero, strValidade, fValorCaucao, sdf.format(dataCalendar.getTime()));
       
        AutorizacaoDebitoCartaoCredito adccAutorizacaoDebitoCartaoCredito = SistemaAutorizacaoCartaoCredito.autorizacaoDebito(strNumero, strValidade, fValorCaucao);
        
        m_contratoAluguer.setAutorizacaoCartaoCredito(adccAutorizacaoDebitoCartaoCredito);
        
        sdf = new SimpleDateFormat("MMMMM d, yyyy");
        System.out.format("\nAutorização de débito em cartão de crédito processado!\nValor da caução: €%.2f\nData limite de débito: %s\n\n", fValorCaucao, sdf.format(dataCalendar.getTime()));
    }

    public void terminaElaboracaoContratoAluguer()
    {
        m_empresa.addContratoAluguer(m_contratoAluguer);
        //Logger.getLogger("Rent-a-car").log(Level.WARNING, m_contratoAluguer.toString());
    }
}

