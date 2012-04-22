/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_controller;

import rentacarapp_model.Cliente;
import rentacarapp_model.Empresa;

/**
 *
 * @author Paulo Maio
 */
public class RegistarClienteController {

    private Empresa m_empresa;
    private Cliente m_cliente;

    public RegistarClienteController(Empresa empresa)
    {
        m_empresa = empresa;
    }

    public void iniciaRegistoNovoCliente()
    {
        m_cliente = m_empresa.getRegistoClientes().novoCliente();
    }

    public boolean terminaRegistoNovoCliente()
    {
        return m_empresa.getRegistoClientes().add(m_cliente);
    }

    public boolean existeClienteID(String strID)
    {
        return m_empresa.getRegistoClientes().existeClienteComID(strID);
    }

    public Cliente getClientebyID(String strID)
    {
        return m_empresa.getRegistoClientes().getClienteByID(strID);
    }

    public void setClienteID(String strID)
    {
        m_cliente.setID(strID);
    }

    public void setNomeCliente(String strNome)
    {
       m_cliente.setNome(strNome);
    }

    public void setEnderecoCliente(String strEndereco)
    {
        m_cliente.setEndereco(strEndereco);
    }

    public void setTelefoneCliente(String strTelefone)
    {
        m_cliente.setTelefone(strTelefone);
    }

    public void setEmailCliente(String strEmail)
    {
        m_cliente.setEmail(strEmail);
    }

    public void setObservacoesCliente(String strObservacoes)
    {
        m_cliente.setObservacoes(strObservacoes);
    }
}
