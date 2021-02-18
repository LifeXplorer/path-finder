# Pathfinding demo application

Service can add distances between 2 arbitrary cities and calculate all possible paths between 2 saved cities.

### Points to consider

* City name is expected to be unique (For multiple cities with same name additional logics should be implemented)
* Within each path every single city can be visited only once to avoid infinite loops
* According to requirements paths are computed in all possible combinations of cities. For practical usage need
  implement limiting criteria
* Computation is made according to BFS in undirected weight graph algorithm
* Pathfinding contains 2 steps:
  1. All routes(graph) initialization(cached)
  2. Path computation from start city
* Concurrent writes do not break computation consistency. Other threads parallel updates will be visible on next query
* Application supports multi-instance shared cache implemented via hazelcast (embedded cache topology).
* For test routes visualisation refer to image(src/test/resources/test_routes_visualization.gif)
* Manual testing http queries for localhost attached(src/test/resources/http_queries_for_manual_testing.http)

