function highlight_map(id)
    global map handler
    highlight(handler, find(map.Nodes.ID == id), 'MarkerSize', 6, 'NodeColor', 'red');
end
