package tutorial.module.complex;

import adf.component.module.complex.BuildingDetector;
import adf.agent.info.*;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import rescuecore2.worldmodel.*;
import rescuecore2.standard.entities.*;
import static rescuecore2.standard.entities.StandardEntityURN.*;
import java.util.*;

public class ClosestBuildingDetector extends BuildingDetector
{
    private Random random = new Random();
    private EntityID result;

    public ClosestBuildingDetector(
        AgentInfo ai, WorldInfo wi, ScenarioInfo si,
        ModuleManager mm, DevelopData dd)
    {
        super(ai, wi, si, mm, dd);
    }

    @Override
    public BuildingDetector calc()
    {
        Collection<EntityID> changes =
            worldInfo.getChanged().getChangedEntities();

        List<EntityID> candidates = new LinkedList<>();
        for (EntityID id : changes)
        {
            StandardEntity entity = worldInfo.getEntity(id);
            if (entity.getStandardURN() != BUILDING) continue;

            Building building = (Building)entity;
            if (building.isOnFire()) candidates.add(id);
        }

        int n = candidates.size();
        result = n == 0 ? null : candidates.get(random.nextInt(n));

        return this;
    }

    @Override
    public EntityID getTarget()
    {
        return result;
    }
}
