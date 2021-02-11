package com.itechart.pathfinder;

import com.itechart.pathfinder.exception.CityNotFoundException;
import com.itechart.pathfinder.exception.PathNotFoundException;
import com.itechart.pathfinder.model.AddDistanceRequest;
import com.itechart.pathfinder.model.Path;
import com.itechart.pathfinder.service.PathfindingService;
import com.itechart.pathfinder.service.RouteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RouteController {

    PathfindingService pathfindingService;
    RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<Void> upsertRoute(@RequestBody AddDistanceRequest request) {
        routeService.upsertRoute(request.getCityNameFrom(), request.getCityNameTo(), request.getDistance());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/calculate")
    public List<Path> getDistance(@RequestParam String fromCity, @RequestParam String toCity) {
        return pathfindingService.getPath(fromCity, toCity);
    }

}
