package com.itechart.pathfinder;

import com.itechart.pathfinder.dto.AddDistanceRequest;
import com.itechart.pathfinder.dto.Path;
import com.itechart.pathfinder.service.PathfindingService;
import com.itechart.pathfinder.service.RouteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RouteController {

    PathfindingService pathfindingService;
    RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<Void> upsertRoute(@Valid @RequestBody AddDistanceRequest request) {
        routeService.upsertRoute(request.getCityNameFrom(), request.getCityNameTo(), request.getDistance());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/calculate")
    public List<Path> getDistance(@RequestParam String fromCity, @RequestParam String toCity) {
        return pathfindingService.getPath(fromCity, toCity);
    }

}
