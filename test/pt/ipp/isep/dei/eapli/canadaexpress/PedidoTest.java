/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.eapli.canadaexpress;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Nuno Silva
 */
public class PedidoTest
{

    DateFormat formatter;

    public PedidoTest()
    {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Test
    public void testOK() throws ParseException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(dataValidade, "12345", 500.0f, dataLimite);

        assertEquals("12345", pedido.getNumCartao());
        assertEquals(500.0f, pedido.getValor(), 0.00001f);

        assertEquals(dataValidade, pedido.getDataValidade());
        assertEquals(dataLimite, pedido.getDataLimite());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testeValorInferiorZero() throws ParseException, IllegalArgumentException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(dataValidade, "12345", -500.0f, dataLimite);
    }

    @Test( expected=IllegalArgumentException.class)
    public void testeValorZero() throws ParseException, IllegalArgumentException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(dataValidade, "12345", 0.0f, dataLimite);
    }

    // pedido com data limite posterior � data de validade do cart�o
    @Test( expected=IllegalArgumentException.class )
    public void testeDataLimitePosteriorValidade() throws ParseException, IllegalArgumentException
    {
        Date dataValidade = (Date) formatter.parse("2020-12-31");
        Date dataLimite = (Date) formatter.parse("2021-01-15");
        Pedido pedido = new Pedido(dataValidade, "12345", 500.0f, dataLimite);
    }

    @Test( expected=IllegalArgumentException.class )
    public void testNumCCVazio() throws ParseException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(dataValidade, "", 500.0f, dataLimite);
    }

    @Test( expected=IllegalArgumentException.class )
    public void testNumCCNull() throws ParseException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(dataValidade, null, 500.0f, dataLimite);
    }

    @Test( expected=IllegalArgumentException.class )
    public void testDataValidadeNull() throws ParseException
    {
        Date dataLimite = (Date) formatter.parse("2012-05-15");
        Pedido pedido = new Pedido(null, "1234", 500.0f, dataLimite);
    }
    @Test( expected=IllegalArgumentException.class )
    public void testDataLimiteNull() throws ParseException
    {
        Date dataValidade = (Date) formatter.parse("2012-12-25");
        Pedido pedido = new Pedido(dataValidade, "1234", 500.0f, null);
    }
}
