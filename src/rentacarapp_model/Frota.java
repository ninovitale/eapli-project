package rentacarapp_model;

import java.util.*;

public class Frota
{
    List<Automovel> m_listAutomovel;

    public Frota()
    {
        m_listAutomovel = new ArrayList();
    }

    public List<Automovel> getFrotaByGrupoAutomovel(GrupoAutomovel ga)
    {
        List<Automovel> listFrotaByGA = new ArrayList();

        for(Iterator<Automovel> i=m_listAutomovel.iterator();i.hasNext();)
        {
            Automovel auto = i.next();
            if( auto.getGA()==ga )
            {
                listFrotaByGA.add(auto);
            }
        }
        return listFrotaByGA;
    }

    protected void fillInData(List<GrupoAutomovel> lCGA)
    {
        GrupoAutomovel ga = (GrupoAutomovel) lCGA.get(0);

        Automovel auto = new Automovel("22-23-QA", ga, "azul");
        m_listAutomovel.add(auto);

        auto = new Automovel("24-25-QB", ga, "cinzento");
        m_listAutomovel.add(auto);

        ga = lCGA.get(2);

        auto = new Automovel("22-23-QC", ga, "branco");
        m_listAutomovel.add(auto);
    }
}