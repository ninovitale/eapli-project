/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

/**
 *
 * @author Nuno Silva
 */
public class LocalDevolucao
{
    private String m_strLocal;

    public LocalDevolucao(String strLocal)
    {
        m_strLocal = strLocal;
    }

    @Override
    public String toString()
    {
        return m_strLocal;
    }
}
