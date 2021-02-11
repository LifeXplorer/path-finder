package com.itechart.pathfinder;

import com.itechart.pathfinder.dto.Path;
import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.entity.Route;
import com.itechart.pathfinder.exception.CityNotFoundException;
import com.itechart.pathfinder.exception.PathNotFoundException;
import com.itechart.pathfinder.repository.CityRepository;
import com.itechart.pathfinder.repository.RouteRepository;
import com.itechart.pathfinder.service.PathfindingService;
import com.itechart.pathfinder.service.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PathfinderApplicationTests {

	private static final String FROM_CITY_NAME = "A";
	private static final String TO_CITY_NAME = "F";
	private static final double DEFAULT_DISTANCE = 2.0;

	@Autowired
	CityRepository cityRepository;
	@Autowired
	RouteRepository routeRepository;
	@Autowired
	RouteService routeService;
	@Autowired
	PathfindingService pathfindingService;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void cleanDatabase() {
		routeRepository.deleteAll();
		cityRepository.deleteAll();
	}

	@Test
	void addsRouteAndCities() {
		routeService.upsertRoute(FROM_CITY_NAME, TO_CITY_NAME, DEFAULT_DISTANCE);
		Optional<City> cityFrom = cityRepository.getByName(FROM_CITY_NAME);
		Optional<City> cityTo = cityRepository.getByName(TO_CITY_NAME);
		assertTrue(cityFrom.isPresent() && cityTo.isPresent());
		Optional<Route> route = routeRepository.getByFromCityAndToCity(cityFrom.get(), cityTo.get());
		assertTrue(route.isPresent());
	}

	@Test
	void updatesRoute() {
		routeService.upsertRoute(FROM_CITY_NAME, TO_CITY_NAME, DEFAULT_DISTANCE);
		Optional<City> cityFrom = cityRepository.getByName(FROM_CITY_NAME);
		Optional<City> cityTo = cityRepository.getByName(TO_CITY_NAME);
		assertTrue(cityFrom.isPresent() && cityTo.isPresent());
		Optional<Route> route = routeRepository.getByFromCityAndToCity(cityFrom.get(), cityTo.get());
		assertTrue(route.isPresent() && DEFAULT_DISTANCE == route.get().getDistance());

		routeService.upsertRoute(FROM_CITY_NAME, TO_CITY_NAME, 3.0);
		Optional<Route> updatedRoute = routeRepository.getByFromCityAndToCity(cityFrom.get(), cityTo.get());
		assertTrue(updatedRoute.isPresent() && 3.0 == updatedRoute.get().getDistance());
	}

	@Test
	void computesAllPossiblePaths() {
		initRoutes();
		List<Path> paths = pathfindingService.getPath(FROM_CITY_NAME, TO_CITY_NAME);
		assertEquals(paths.size(), 7);
		double allPathsDistance = paths.stream()
				.map(Path::getDistance)
				.mapToDouble(Double::doubleValue)
				.sum();
		assertEquals(allPathsDistance, 158);
	}

	@Test
	void computesSamePathsWhenReversed() {
		initRoutes();
		double allPathsDistance = pathfindingService.getPath(FROM_CITY_NAME, TO_CITY_NAME).stream()
				.map(Path::getDistance)
				.mapToDouble(Double::doubleValue)
				.sum();
		double allPathsReversedDistance = pathfindingService.getPath(TO_CITY_NAME, FROM_CITY_NAME).stream()
				.map(Path::getDistance)
				.mapToDouble(Double::doubleValue)
				.sum();
		assertEquals(allPathsDistance, allPathsReversedDistance);
	}

	@Test
	void failsOnNotFoundCity() {
		initRoutes();
		Assertions.assertThrows(CityNotFoundException.class, () -> pathfindingService.getPath("Z", TO_CITY_NAME));
	}

	@Test
	void failsOnNotFoundRoute() {
		initRoutes();
		Assertions.assertThrows(PathNotFoundException.class, () -> pathfindingService.getPath(FROM_CITY_NAME, "K"));
	}

	private void initRoutes() {
		routeService.upsertRoute("A", "D", 2.0);
		routeService.upsertRoute("A", "C", 5.0);
		routeService.upsertRoute("B", "D", 4.0);
		routeService.upsertRoute("B", "F", 8.0);
		routeService.upsertRoute("C", "G", 4.0);
		routeService.upsertRoute("D", "E", 3.0);
		routeService.upsertRoute("D", "H", 7.0);
		routeService.upsertRoute("E", "G", 5.0);
		routeService.upsertRoute("E", "H", 2.0);
		routeService.upsertRoute("F", "H", 6.0);
		routeService.upsertRoute("K", "M", 5.0);
	}

}
