package rentacarapp_model;

public class GrupoAutomovel
{

    private String m_strNome;
    private int m_nPortas;
    private String m_strClasse;
    private String m_strCombustivel;
    private boolean m_bArCondicionado;
    private float m_dPrecoDiario;

    public GrupoAutomovel(String strNome, int nPortas, String strClasse, String strCombustivel, boolean bArCondicionado, float dPrecoDiario)
    {
        setNome(strNome);
        setPortas(nPortas);
        setClasse(strClasse);
        setCombustivel(strCombustivel);
        setArCondicionado(bArCondicionado);
        setPrecoDiario(dPrecoDiario);
    }

    public float getPrecoDiario()
    {
        return this.m_dPrecoDiario;
    }

    public final void setPrecoDiario(float dPreco)
    {
        this.m_dPrecoDiario = dPreco;
    }

    public boolean isArCondicionado()
    {
        return this.m_bArCondicionado;
    }

    public final void setArCondicionado(boolean bArCondicionado)
    {
        this.m_bArCondicionado = bArCondicionado;
    }

    public int getPortas()
    {
        return m_nPortas;
    }

    public final void setPortas(int nPortas)
    {
        this.m_nPortas = nPortas;
    }

    public String getClasse()
    {
        return m_strClasse;
    }

    public final void setClasse(String strClasse)
    {
        this.m_strClasse = strClasse;
    }

    public String getCombustivel()
    {
        return m_strCombustivel;
    }

    public final void setCombustivel(String strCombustivel)
    {
        this.m_strCombustivel = strCombustivel;
    }

    public String getNome()
    {
        return m_strNome;
    }

    public final void setNome(String strNome)
    {
        this.m_strNome = strNome;
    }

    @Override
    public String toString()
    {
        return getNome() + ", " + getPortas() + ", " + getClasse() + ", " + getCombustivel() + ", " + (isArCondicionado() ? "sim" : "não") + ", " + "€" + getPrecoDiario();
    }
}

