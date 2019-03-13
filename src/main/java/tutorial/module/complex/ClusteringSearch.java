package tutorial.module.complex;

import adf.component.module.complex.Search;
import adf.agent.info.*;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.standard.entities.*;
import static rescuecore2.standard.entities.StandardEntityURN.*;
import java.util.*;
import com.mathworks.engine.MatlabEngine;

public class ClusteringSearch extends Search
{
    private Random random = new Random();
    private List<EntityID> candidates = new LinkedList<>();
    private EntityID result;

    private MatlabEngine matlab;

    public ClusteringSearch(
        AgentInfo ai, WorldInfo wi, ScenarioInfo si,
        ModuleManager mm, DevelopData dd)
    {
        super(ai, wi, si, mm, dd);
    }

    @Override
    public Search calc()
    {
        if (candidates.isEmpty()) initializeCandidates();

        int next = random.nextInt(candidates.size());
        result = candidates.get(next);

        return this;
    }

    @Override
    public EntityID getTarget()
    {
        return result;
    }

    @Override
    public Search preparate()
    {
        super.preparate();
        if (getCountPreparate() > 1) return this;

        return this;
    }

    private void initializeCandidates()
    {
    }

    private double[][] convertAreasToArray(List<EntityID> areas)
    {
        int n = areas.size();
        double[][] ret = new double[n][2];
        for (int i=0; i<n; ++i)
        {
            EntityID id = areas.get(i);
            Area area = (Area)worldInfo.getEntity(id);
            ret[i][0] = (double)area.getX();
            ret[i][1] = (double)area.getY();
        }
        return ret;
    }

    private double[][] convertAgentsToArray(List<EntityID> agents)
    {
        int n = agents.size();
        double[][] ret = new double[n][2];
        for (int i=0; i<n; ++i)
        {
            EntityID id = agents.get(i);
            Human agent = (Human)worldInfo.getEntity(id);
            ret[i][0] = (double)agent.getX();
            ret[i][1] = (double)agent.getY();
        }
        return ret;
    }
}
