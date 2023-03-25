package ua.com.foxminded.carmicroservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ua.com.foxminded.carmicroservice.dto.CarDTO;
import ua.com.foxminded.carmicroservice.models.Car;
import ua.com.foxminded.carmicroservice.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping
	public ResponseEntity<List<CarDTO>> getAllCars() {

		List<CarDTO> cars = carService.getAll();
		return ResponseEntity.ok(cars);

	}

	@GetMapping("/page-sort/{offset}/{pageSize}/{field}")
	public ResponseEntity<List<CarDTO>> getAllCarsWithPaginationAndSorting(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {

		List<CarDTO> cars = carService.getAllWithPaginationAndSorting(offset, pageSize, field);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeName(@PathVariable String makeName) {

		List<CarDTO> cars = carService.getByMakeName(makeName);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}/page-sort/{offset}/{pageSize}/{field}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeNameWithPaginationAndSorting(@PathVariable String makeName,
			@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {

		List<CarDTO> cars = carService.getByMakeNameWithPaginationAndSorting(makeName, offset, pageSize, field);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}/model/{modelName}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeNameAndModelName(@PathVariable String makeName,
			@PathVariable String modelName) {

		List<CarDTO> cars = carService.getByMakeNameAndModelName(makeName, modelName);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}/model/{modelName}/page-sort/{offset}/{pageSize}/{field}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeNameAndModelNameWithPaginationAndSorting(
			@PathVariable String makeName, @PathVariable String modelName, @PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {

		List<CarDTO> cars = carService.getByMakeNameAndModelNameWithPaginationAndSorting(makeName, modelName, offset,
				pageSize, field);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}/model/{modelName}/year/{year}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeNameAndModelNameAndYear(@PathVariable String makeName,
			@PathVariable String modelName, @PathVariable int year) {

		List<CarDTO> cars = carService.getByMakeNameAndModelNameAndYear(makeName, modelName, year);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("make/{makeName}/model/{modelName}/year/{year}/page-sort/{offset}/{pageSize}/{field}")
	public ResponseEntity<List<CarDTO>> getCarsByMakeNameAndModelNameAndYearWithPaginationAndSorting(
			@PathVariable String makeName, @PathVariable String modelName, @PathVariable int year,
			@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {

		List<CarDTO> cars = carService.getByMakeNameAndModelNameAndYearWithPaginationAndSorting(makeName, modelName,
				year, offset, pageSize, field);
		return ResponseEntity.ok(cars);

	}

	@GetMapping("/search")
	public ResponseEntity<List<CarDTO>> searchCars(@RequestParam(required = false) String make,
			@RequestParam(required = false) String model, @RequestParam(required = false) String category,
			@RequestParam(required = false) Integer minYear, @RequestParam(required = false) Integer maxYear) {

		List<CarDTO> cars = carService.search(make, model, category, minYear, maxYear);

		return ResponseEntity.ok(cars);

	}

	@GetMapping("/search/page-sort")
	public ResponseEntity<List<CarDTO>> searchCarsWithPaginationAndSorting(@RequestParam(required = false) String make,
			@RequestParam(required = false) String model, @RequestParam(required = false) String category,
			@RequestParam(required = false) Integer minYear, @RequestParam(required = false) Integer maxYear,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "year") String field) {

		List<CarDTO> cars = carService.searchWithPaginationAndSorting(make, model, category, minYear, maxYear, offset,
				pageSize, field);

		return ResponseEntity.ok(cars);

	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable String id) {

		CarDTO car = carService.getById(id);
		return ResponseEntity.ok(car);

	}

	@PostMapping
	@Operation(summary = "Save car", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<CarDTO> saveCar(@RequestBody CarDTO car) {

		CarDTO savedCar = carService.save(car);
		return ResponseEntity.created(URI.create("/api/cars/" + savedCar.getId())).body(savedCar);

	}

	@PutMapping("/{id}")
	@Operation(summary = "Update car", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<CarDTO> updateCar(@PathVariable String id, @RequestBody Car car) {

		CarDTO updatedCar = carService.update(id, car);
		return ResponseEntity.ok(updatedCar);

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete car", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> deleteCarById(@PathVariable String id) {

		carService.delete(id);
		return ResponseEntity.noContent().build();

	}

}