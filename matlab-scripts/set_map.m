function set_map(areas, edges)
    id = areas(:,1);
    cx = areas(:,2);
    cy = areas(:,3);
    nodes = table(id, cx, cy, 'VariableNames', {'ID', 'X', 'Y'});

    global map handler
    map = graph(edges, nodes);
    handler = plot(map, 'XData', map.Nodes.X, 'YData', map.Nodes.Y);
end
