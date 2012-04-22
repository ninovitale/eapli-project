/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

/**
 *
 * @author Nuno Silva
 */
public class ServicoAdicional
{
    private String m_strServicoAdicional;
    private float m_dPrecoDiario;
    private boolean m_bIsAcumulavel;
    
    public ServicoAdicional(String strServicoAdicional, float dPrecoDiario, boolean isAcumulavel)
    {
        m_strServicoAdicional = strServicoAdicional;
        m_dPrecoDiario = dPrecoDiario;
        m_bIsAcumulavel = isAcumulavel;

    }

    public boolean isAcumulavel()
    {
        return m_bIsAcumulavel;
    }

    public String getDescricao()
    {
        return m_strServicoAdicional;
    }

    public float getPrecoDiario()
    {
        return m_dPrecoDiario;
    }

    @Override
    public String toString()
    {
        return m_strServicoAdicional + " - " + String.format("%.2f", m_dPrecoDiario);
    }
}
