package tutorial.module.complex;

import adf.component.module.complex.Search;
import adf.agent.info.*;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import rescuecore2.worldmodel.EntityID;
import static rescuecore2.standard.entities.StandardEntityURN.*;
import rescuecore2.misc.*;
import java.util.*;

public class ClosestSearch extends Search
{
    private Random random = new Random();
    private Set<EntityID> candidates;
    private EntityID result;

    public ClosestSearch(
        AgentInfo ai, WorldInfo wi, ScenarioInfo si,
        ModuleManager mm, DevelopData dd)
    {
        super(ai, wi, si, mm, dd);

        Collection<EntityID> ids =
            worldInfo.getEntityIDsOfType(ROAD, HYDRANT);
        candidates = new HashSet<>(ids);
    }

    @Override
    public Search calc()
    {
        candidates.remove(agentInfo.getPosition());

        EntityID me = agentInfo.getID();
        Pair<EntityID, Double> closest = null;

        for (EntityID id : candidates)
        {
            double d = worldInfo.getDistance(me, id);

            if (closest == null || d < closest.second())
                closest = new Pair<>(id, d);
        }

        if (closest != null) result = closest.first();
        return this;
    }

    @Override
    public EntityID getTarget()
    {
        return result;
    }
}
