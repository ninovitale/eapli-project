/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ipp.isep.dei.eapli.visaolight;

import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Nuno Silva
 */
public class VisaoLightTest {

    public VisaoLightTest() {
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
    public void test() {
        // tudo ok
        assertNotNull( VisaoLight.getAutorizacaoDCC("1234", "2020-12-31", 500.0f, "2020-12-30") );
        // numero CC vazio
        assertNull( VisaoLight.getAutorizacaoDCC("", "2020-12-31", 500.0f, "2020-12-30") );
        // numero CC null
        assertNull( VisaoLight.getAutorizacaoDCC(null, "2020-12-31", 500.0f, "2020-12-30") );
        // valor menor que zero
        assertNull( VisaoLight.getAutorizacaoDCC("1234", "2020-12-31", -500.0f, "2020-12-30") );
        // valor igual zero
        assertNull( VisaoLight.getAutorizacaoDCC("1234", "2020-12-31", 0.0f, "2020-12-30") );
        // data limite anterior a atual
        assertNull( VisaoLight.getAutorizacaoDCC("1234", "2020-12-31", 500.0f, "2012-01-01") );
        // data validade anterior a data atual
        assertNull( VisaoLight.getAutorizacaoDCC("1234", "2012-01-31", 500.0f, "2021-12-31") );
        // data limite e data de validade anterior a data atual
        assertNull( VisaoLight.getAutorizacaoDCC("1234", "2012-01-01", 500.0f, "2012-01-31") );
    }
}