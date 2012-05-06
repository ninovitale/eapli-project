/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author HBK
 */
public class CalculoContrato_StrategyOptimizedTest {
    Empresa m_empresa;
    Cliente c;
    GrupoAutomovel ga;
    Automovel auto;
    ContratoAluguer contratoAluguer = new ContratoAluguer();
    ContratoAluguer contratoAnterior = new ContratoAluguer();

    public CalculoContrato_StrategyOptimizedTest() {   
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ga = new GrupoAutomovel("D", 5, "D", "500", true, 169.00F);
        auto = new Automovel("23-QQ-11", ga, "verde");
        c = new Cliente("123", "Joana", "Rua das Flores", "220001111", "joana@ola.pt", null);
        contratoAluguer.setCliente(c);
        contratoAluguer.setAutomovel(auto);
        contratoAluguer.setPoliticaDesconto(0); // para escolher a melhor política de desconto automaticamente
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class CalculoContrato_StrategyOptimized.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        CalculoContratoStrategy calcStrategy = null;
        CalculoContrato_StrategyOptimized instance = new CalculoContrato_StrategyOptimized();
        instance.add(calcStrategy);
    }

    /**
     * Test of remove method, of class CalculoContrato_StrategyOptimized.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        CalculoContratoStrategy calcStrategy = null;
        CalculoContrato_StrategyOptimized instance = new CalculoContrato_StrategyOptimized();
        instance.remove(calcStrategy);
    }

    /**
     * Test of getValorTotal method, of class CalculoContrato_StrategyOptimized.
     */
    @Test
    public void testGetValorTotal() {
        System.out.println("getValorTotal");
        CalculoContrato_StrategyOptimized instance = new CalculoContrato_StrategyOptimized();
        instance.add(new CalculoContrato_Strategy1());
        instance.add(new CalculoContrato_Strategy2());
        
        // testar a política 1
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7);
        contratoAluguer.setDataDevolucaoPrevista(calendar.getTime());
        
        ServicoAdicional sa = new ServicoAdicional("Cadeira de bébé", 50.00F, true);
        contratoAluguer.addServicoAdicional(sa, 2);
        
        float valorSemDesconto = contratoAluguer.getValorTotal();
        float valorComDesconto = instance.getValorTotal(contratoAluguer);
        
        assertEquals(1218.85F, valorComDesconto, 0);
        System.out.format("%nFoi aplicado o melhor desconto para este contrato aluguer!%n€%.2f (sem desconto); €%.2f (com desconto) (%d%% desconto total)%n", valorSemDesconto, valorComDesconto, ((int) (100.0 - ((valorComDesconto / valorSemDesconto) * 100.0))));
        
        
        // testar a política 2
        this.m_empresa = Empresa.instance();
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -200);
        this.contratoAnterior.m_dataInicioContrato = calendar.getTime();
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -50);
        contratoAnterior.setAutomovel(auto);
        contratoAnterior.setCliente(c);
        contratoAnterior.setDataDevolucaoPrevista(calendar.getTime());
        
        this.m_empresa.addContratoAluguer(contratoAnterior);
        
        valorSemDesconto = contratoAluguer.getValorTotal();
        valorComDesconto = instance.getValorTotal(contratoAluguer);
        
        assertEquals(1199.2F, valorComDesconto, 0);
        System.out.format("%nFoi aplicado o melhor desconto para este contrato aluguer!%n€%.2f (sem desconto); €%.2f (com desconto) (%d%% desconto total)%n", valorSemDesconto, valorComDesconto, ((int) (100.0 - ((valorComDesconto / valorSemDesconto) * 100.0))));

    }
}
