package rentacarapp_model;

public class LinhaServicoAdicional
{
    private ServicoAdicional m_servicoAdicional;
    private int m_iQtd;

    public LinhaServicoAdicional(ServicoAdicional sa, int qtd)
    {
        m_servicoAdicional = sa;
        m_iQtd = qtd;
    }

    public ServicoAdicional getServicoAdicional()
    {
        return m_servicoAdicional;
    }

    public int getQuantidade()
    {
        return m_iQtd;
    }

    public float getValorLinha()
    {
        return m_servicoAdicional.getPrecoDiario()*m_iQtd;
    }

    @Override
    public String toString()
    {
        return String.format("%d X %s  = %.2f",m_iQtd, m_servicoAdicional.getDescricao(),getValorLinha());
    }
}

