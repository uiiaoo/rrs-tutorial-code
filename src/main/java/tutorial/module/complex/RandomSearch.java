package tutorial.module.complex;

import adf.component.module.complex.Search;
import adf.agent.info.*;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import rescuecore2.worldmodel.EntityID;
import static rescuecore2.standard.entities.StandardEntityURN.*;
import java.util.*;

public class RandomSearch extends Search
{
    private MatlabPlotter plotter;
    private Random random;
    private EntityID result;

    public RandomSearch(
        AgentInfo ai, WorldInfo wi, ScenarioInfo si,
        ModuleManager mm, DevelopData dd)
    {
        super(ai, wi, si, mm, dd);
        plotter = MatlabPlotter.getInstance(wi);
        random = new Random(ai.getID().getValue());
    }

    @Override
    public Search calc()
    {
        plotter.highlight(agentInfo.getPosition());
        return this;
    }

    @Override
    public EntityID getTarget()
    {
        return result;
    }
}
