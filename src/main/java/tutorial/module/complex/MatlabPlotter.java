package tutorial.module.complex;

import adf.agent.info.*;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.standard.entities.*;
import static rescuecore2.standard.entities.StandardEntityURN.*;
import java.util.*;
import java.util.concurrent.Future;
import com.mathworks.engine.MatlabEngine;

public class MatlabPlotter
{
    private static MatlabPlotter instance;

    private WorldInfo worldinfo;
    private MatlabEngine matlab;

    public static MatlabPlotter getInstance(WorldInfo wi)
    {
        if (instance == null) instance = new MatlabPlotter(wi);
        return instance;
    }

    public MatlabPlotter(WorldInfo wi)
    {
        worldinfo  = wi;
        matlab = connect();
        shareMapData();
    }

    private MatlabEngine connect()
    {
        try
        {
            return MatlabEngine.startMatlab();
        }
        catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private void shareMapData()
    {
        List<EntityID> ids =
            new ArrayList<>(worldinfo.getEntityIDsOfType(ROAD, HYDRANT));

        double[][] arg1 = convertToArray(ids);
        double[][] arg2 = convertToEdges(ids);

        try
        {
            matlab.feval(0, "addpath", "matlab-scripts");
            matlab.feval(0, "set_map", (Object)arg1, (Object)arg2);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void highlight(EntityID id)
    {
        int arg = id.getValue();
        try
        {
            matlab.feval(0, "highlight_map", arg);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private double[][] convertToArray(List<EntityID> areas)
    {
        int n = areas.size();
        double[][] ret = new double[n][3];
        for (int i=0; i<n; ++i)
        {
            EntityID id = areas.get(i);
            Area area = (Area)worldinfo.getEntity(id);
            ret[i][0] = (double)id.getValue();
            ret[i][1] = (double)area.getX();
            ret[i][2] = (double)area.getY();
        }
        return ret;
    }

    private double[][] convertToEdges(List<EntityID> areas)
    {
        int n = areas.size();
        double[][] ret = new double[n][n];
        for (int i=0; i<n; ++i) for (int j=0; j<n; ++j)
        {
            EntityID id1 = areas.get(i);
            EntityID id2 = areas.get(j);
            Area area1 = (Area)worldinfo.getEntity(id1);
            ret[i][j] = area1.getNeighbours().contains(id2) ? 1.0 : 0.0;
        }
        return ret;
    }
}
