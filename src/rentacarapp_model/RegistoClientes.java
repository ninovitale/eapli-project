/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Paulo Maio
 */
public class RegistoClientes {

    List<Cliente> m_listCliente;

    public RegistoClientes()
    {
        m_listCliente = new ArrayList();
    }

    public Cliente getClienteByID(String strID)
    {
        for(Iterator<Cliente> i=m_listCliente.iterator();i.hasNext();)
        {
            Cliente cli = i.next();
            if (cli.getID().compareTo(strID)==0)
                return cli;
        }
        return null;
    }

    public List<Cliente> getClientesByNome(String strNome)
    {
        List<Cliente> listClientes = new ArrayList();

        for(Iterator<Cliente> i=m_listCliente.iterator();i.hasNext();)
        {
            Cliente cli = i.next();
            if (cli.getNome().contains(strNome))
                listClientes.add(cli);
        }
        return listClientes;
    }

    protected void fillInData()
    {
        Cliente c = new Cliente("1111","Jose Maria","Rua das Violetas","911234567","josemaria@isep.ipp.pt","");
        m_listCliente.add(c);

        c = new Cliente("2222","Maria Joaquina","Av. da Liberdade","939876543","mariajoaquina@isep.ipp.pt","");
        m_listCliente.add(c);

        c = new Cliente("333","Joao Silva","Praça da Republica","96918273","joaosilva@isep.ipp.pt","");
        m_listCliente.add(c);

    }

    public Cliente novoCliente()
    {
        return new Cliente();
    }

    public boolean existeClienteComID(String strID)
    {
        return (getClienteByID(strID)!=null);
    }

    public boolean add(Cliente cliente)
    {
        boolean bRet = false;
        if (!existeClienteComID(cliente.getID()))
        {
            this.m_listCliente.add(cliente);
            bRet = true;
        }
        return bRet;
    }

}
