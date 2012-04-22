package rentacarapp_model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empresa
{
    private List<ContratoAluguer> m_listRegistoContratoAluguer;
    private List<GrupoAutomovel> m_listCatalogoGrupoAutomovel;
    private Frota m_frota;
    private RegistoClientes m_registoClientes;
    private List<ServicoAdicional> m_listCatalogoServicoAdicional;
    private List<LocalDevolucao> m_listCatalogoLocalDevolucao;

    public Empresa()
    {
        m_listCatalogoGrupoAutomovel = new ArrayList<GrupoAutomovel>();
        m_frota = new Frota();
        m_registoClientes = new RegistoClientes();
        m_listRegistoContratoAluguer = new ArrayList<ContratoAluguer>();
        m_listCatalogoServicoAdicional = new ArrayList<ServicoAdicional>();
        m_listCatalogoLocalDevolucao = new ArrayList<LocalDevolucao>();

        fillInData();
    }

    public List<GrupoAutomovel> getCatalogoGrupoAutomovel()
    {
        return m_listCatalogoGrupoAutomovel;
    }

    public RegistoClientes getRegistoClientes()
    {
        return m_registoClientes;
    }

    public Frota getFrota()
    {
        return m_frota;
    }

    public List<ContratoAluguer> getRegistoContratoAluguer()
    {
        return m_listRegistoContratoAluguer;
    }

    public List<ServicoAdicional> getCatalogoServicoAdicional()
    {
        return m_listCatalogoServicoAdicional;
    }

    public List<LocalDevolucao> getCatalogoLocalDevolucao()
    {
        return m_listCatalogoLocalDevolucao;
    }

    public ContratoAluguer criaContratoAluguer()
    {
        return new ContratoAluguer();
    }

    public void addContratoAluguer(ContratoAluguer ca)
    {
        m_listRegistoContratoAluguer.add(ca);

        Logger.getLogger("Rent-a-car").log(Level.INFO, "Contrato Aluguer adicionado ao registo." );
    }

    public final void fillInData()
    {
        fillInCatalogoGrupoAutomovel();
        fillInCatalogoLocalDevolucao();
        fillInCatalogoServicoAdicional();

        m_frota.fillInData(m_listCatalogoGrupoAutomovel);
        m_registoClientes.fillInData();
    }

    private void fillInCatalogoGrupoAutomovel()
    {
        GrupoAutomovel ga = new GrupoAutomovel("Grupo A", 3, "utilitário", "gasolina", false, 37.69f);
        m_listCatalogoGrupoAutomovel.add(ga);

        ga = new GrupoAutomovel("Grupo B", 5, "utilitário", "gasolina", false, 48.79f);
        m_listCatalogoGrupoAutomovel.add(ga);

        ga = new GrupoAutomovel("Grupo C", 5, "familiar compacto", "gasolina", true, 59.39f);
        m_listCatalogoGrupoAutomovel.add(ga);
    }

    private void fillInCatalogoLocalDevolucao()
    {
        LocalDevolucao ld = new LocalDevolucao("Hilton, Lisbon");
        m_listCatalogoLocalDevolucao.add(ld);

        ld = new LocalDevolucao("Hilton, Paris");
        m_listCatalogoLocalDevolucao.add(ld);

        ld = new LocalDevolucao("Hilton, Madrid");
        m_listCatalogoLocalDevolucao.add(ld);
    }

    private void fillInCatalogoServicoAdicional()
    {
        ServicoAdicional sa = new ServicoAdicional("cadeira de criança", 5.99f,true);
        m_listCatalogoServicoAdicional.add(sa);

        sa = new ServicoAdicional("GPS", 10.99f,false);
        m_listCatalogoServicoAdicional.add(sa);

        sa = new ServicoAdicional("Seguro contra todos os riscos", 7.49f, false);
        m_listCatalogoServicoAdicional.add(sa);
    }
}