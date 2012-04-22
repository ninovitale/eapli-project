/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

/**
 *
 * @author Nuno Silva
 */
public class Automovel
{

    private String m_strMatricula;
    private GrupoAutomovel m_GA;
    private String m_strCor;

    public Automovel(String strMatricula, GrupoAutomovel ga, String strCor)
    {
        setMatricula(strMatricula);
        setGA(ga);
        setCor(strCor);
    }

    public GrupoAutomovel getGA()
    {
        return m_GA;
    }

    public void setGA(GrupoAutomovel GA)
    {
        this.m_GA = GA;
    }

    public String getCor()
    {
        return m_strCor;
    }

    public void setCor(String cor)
    {
        this.m_strCor = cor;
    }

    public String getMatricula()
    {
        return m_strMatricula;
    }

    public void setMatricula(String matricula)
    {
        this.m_strMatricula = matricula;
    }

    public float getPrecoDiario()
    {
        return this.m_GA.getPrecoDiario();
    }

    @Override
    public String toString()
    {
        return this.getMatricula() + ", " + this.getCor() + ", " + this.getGA().toString();
    }
}
