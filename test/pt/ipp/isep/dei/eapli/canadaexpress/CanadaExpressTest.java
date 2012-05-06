package pt.ipp.isep.dei.eapli.canadaexpress;

/**
 *
 * @author HBK
 */
import java.text.DateFormat;
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
public class CanadaExpressTest
{
    CanadaExpress c;

    Pedido pParametrosValidos;
    Pedido pDataValidadeAnteriorAtual;
    Pedido pDataLimiteAnteriorAtual;

    public CanadaExpressTest() throws Exception
    {
        c = new CanadaExpress();

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");

        // pedido com todos os parâmetros válidos
        Date dataValidade = (Date) formatter.parse("2020-12-31");
        Date dataLimite = (Date) formatter.parse("2020-12-30");
        pParametrosValidos = new Pedido(dataValidade, "12345", 500.0f, dataLimite);

        // pedido com data limite anterior à atual
        dataValidade = (Date) formatter.parse("2020-12-31");
        dataLimite = (Date) formatter.parse("2012-01-15");
        pDataValidadeAnteriorAtual = new Pedido(dataValidade, "12345", 500.0f, dataLimite);

        // pedido com limite posterior à data de validade do cartão
        dataValidade = (Date) formatter.parse("2020-12-31");
        dataLimite = (Date) formatter.parse("2012-01-01");
        pDataLimiteAnteriorAtual = new Pedido(dataValidade, "12345", 500.0f, dataLimite);
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
    public void test() throws Exception
    {
        assertFalse( c.Init("asdasdsdas") );
        assertEquals("**Inválida**", c.ValidaPedido( pParametrosValidos ) );

        assertFalse( c.Init("") );
        assertEquals("**Inválida**", c.ValidaPedido( pParametrosValidos ) );

        assertTrue( c.Init("#CANADA#EXPRESS#EAPLI#") );
        assertEquals("**Inválida**", c.ValidaPedido( pDataValidadeAnteriorAtual ) );
        assertEquals("**Inválida**", c.ValidaPedido( pDataLimiteAnteriorAtual ) );
        assertEquals("Autorização Válida#12345#" +
                    pParametrosValidos.getDataLimite().getDay() + "/" + pParametrosValidos.getDataLimite().getMonth() +
                    "#500.0", c.ValidaPedido( pParametrosValidos ) );

        assertTrue( c.Finish() );
        assertEquals("**Inválida**", c.ValidaPedido( pParametrosValidos ) );
    }
}
