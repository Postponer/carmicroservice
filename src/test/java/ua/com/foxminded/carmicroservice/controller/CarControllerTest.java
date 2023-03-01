package ua.com.foxminded.carmicroservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carmicroservice.dto.CarDTO;
import ua.com.foxminded.carmicroservice.dto.CategoryDTO;
import ua.com.foxminded.carmicroservice.dto.MakeDTO;
import ua.com.foxminded.carmicroservice.dto.ModelDTO;
import ua.com.foxminded.carmicroservice.models.Car;
import ua.com.foxminded.carmicroservice.servicelayer.CarService;

@WebMvcTest(CarController.class)
@WithMockUser
class CarControllerTest {

	@MockBean
	private CarService carService;

	@Autowired
	private MockMvc mockMvc;
	
	List<CarDTO> createCarList() {

		CarDTO car1 = new CarDTO();
		car1.setId("1");
		car1.setMake(new MakeDTO(1l, "Toyota"));
		car1.setYear(2020);
		car1.setModel(new ModelDTO(1l, "Corolla"));
		car1.setCategory(new CategoryDTO(1l, "Sedan"));

		CarDTO car2 = new CarDTO();
		car2.setId("2");
		car2.setMake(new MakeDTO(2l, "Toyota"));
		car2.setYear(2020);
		car2.setModel(new ModelDTO(2l, "Corolla"));
		car2.setCategory(new CategoryDTO(2l, "Sedan"));

		return Arrays.asList(car1, car2);

	}

	@Test
	void testGetAllCars() throws Exception {

		List<CarDTO> cars = createCarList();
		Mockito.when(carService.getAll()).thenReturn(cars);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cars")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].make.name", is("Toyota")))
				.andExpect(jsonPath("$[0].year", is(2020)))
				.andExpect(jsonPath("$[0].model.name", is("Corolla")))
				.andExpect(jsonPath("$[0].category.name", is("Sedan")))
				.andExpect(jsonPath("$[1].id", is("2")))
				.andExpect(jsonPath("$[1].make.name", is("Toyota")))
				.andExpect(jsonPath("$[1].year", is(2020)))
				.andExpect(jsonPath("$[1].model.name", is("Corolla")))
				.andExpect(jsonPath("$[1].category.name", is("Sedan")));

	}

	@Test
	void testGetAllCarsWithPaginationAndSorting() throws Exception {

		List<CarDTO> cars = createCarList();
		Mockito.when(carService.getAllWithPaginationAndSorting(0, 2, "make")).thenReturn(cars);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/page-sort/0/2/make"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].make.name", is("Toyota")))
				.andExpect(jsonPath("$[0].year", is(2020)))
				.andExpect(jsonPath("$[0].model.name", is("Corolla")))
				.andExpect(jsonPath("$[0].category.name", is("Sedan")))
				.andExpect(jsonPath("$[1].id", is("2")))
				.andExpect(jsonPath("$[1].make.name", is("Toyota")))
				.andExpect(jsonPath("$[1].year", is(2020)))
				.andExpect(jsonPath("$[1].model.name", is("Corolla")))
				.andExpect(jsonPath("$[1].category.name", is("Sedan")));
		;

		Mockito.verify(carService, Mockito.times(1)).getAllWithPaginationAndSorting(0, 2, "make");
		Mockito.verifyNoMoreInteractions(carService);

	}

	@Test
	void testGetCarsByMakeName() throws Exception {

		List<CarDTO> cars = createCarList();
		Mockito.when(carService.getByMakeName("Toyota")).thenReturn(cars);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/Toyota"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].make.name", is("Toyota")))
				.andExpect(jsonPath("$[0].year", is(2020)))
				.andExpect(jsonPath("$[0].model.name", is("Corolla")))
				.andExpect(jsonPath("$[0].category.name", is("Sedan")))
				.andExpect(jsonPath("$[1].id", is("2")))
				.andExpect(jsonPath("$[1].make.name", is("Toyota")))
				.andExpect(jsonPath("$[1].year", is(2020)))
				.andExpect(jsonPath("$[1].model.name", is("Corolla")))
				.andExpect(jsonPath("$[1].category.name", is("Sedan")));

		Mockito.verify(carService, Mockito.times(1)).getByMakeName("Toyota");
		Mockito.verifyNoMoreInteractions(carService);

	}

	@Test
	void testGetCarsByMakeNameWithPaginationAndSorting() throws Exception {

		List<CarDTO> cars = createCarList();
		Mockito.when(carService.getByMakeNameWithPaginationAndSorting("Toyota", 0, 2, "year")).thenReturn(cars);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/Toyota/page-sort/0/2/year"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("1")))
				.andExpect(jsonPath("$[0].make.name", is("Toyota")))
				.andExpect(jsonPath("$[0].year", is(2020)))
				.andExpect(jsonPath("$[0].model.name", is("Corolla")))
				.andExpect(jsonPath("$[0].category.name", is("Sedan")))
				.andExpect(jsonPath("$[1].id", is("2")))
				.andExpect(jsonPath("$[1].make.name", is("Toyota")))
				.andExpect(jsonPath("$[1].year", is(2020)))
				.andExpect(jsonPath("$[1].model.name", is("Corolla")))
				.andExpect(jsonPath("$[1].category.name", is("Sedan")));

		Mockito.verify(carService, Mockito.times(1)).getByMakeNameWithPaginationAndSorting("Toyota", 0, 2, "year");
		Mockito.verifyNoMoreInteractions(carService);

	}
	
	@Test
	void testGetCarsByMakeNameAndModelName() throws Exception {
		
	    List<CarDTO> cars = createCarList();
	    String makeName = "Toyota";
	    String modelName = "Corolla";
	    Mockito.when(carService.getByMakeNameAndModelName(makeName, modelName)).thenReturn(cars);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/" + makeName + "/model/" + modelName))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is("1")))
	            .andExpect(jsonPath("$[0].make.name", is(makeName)))
	            .andExpect(jsonPath("$[0].model.name", is(modelName)))
	            .andExpect(jsonPath("$[0].year", is(2020)))
	            .andExpect(jsonPath("$[0].category.name", is("Sedan")))
	            .andExpect(jsonPath("$[1].id", is("2")))
	            .andExpect(jsonPath("$[1].make.name", is(makeName)))
	            .andExpect(jsonPath("$[1].model.name", is(modelName)))
	            .andExpect(jsonPath("$[1].year", is(2020)))
	            .andExpect(jsonPath("$[1].category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).getByMakeNameAndModelName(makeName, modelName);
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testGetCarsByMakeNameAndModelNameWithPaginationAndSorting() throws Exception {
		
	    List<CarDTO> cars = createCarList();
	    Mockito.when(carService.getByMakeNameAndModelNameWithPaginationAndSorting("Toyota", "Corolla", 0, 2, "year")).thenReturn(cars);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/Toyota/model/Corolla/page-sort/0/2/year"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is("1")))
	            .andExpect(jsonPath("$[0].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[0].year", is(2020)))
	            .andExpect(jsonPath("$[0].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[0].category.name", is("Sedan")))
	            .andExpect(jsonPath("$[1].id", is("2")))
	            .andExpect(jsonPath("$[1].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[1].year", is(2020)))
	            .andExpect(jsonPath("$[1].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[1].category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).getByMakeNameAndModelNameWithPaginationAndSorting("Toyota", "Corolla", 0, 2, "year");
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testGetCarsByMakeNameAndModelNameAndYear() throws Exception {
		
	    List<CarDTO> cars = createCarList();
	    Mockito.when(carService.getByMakeNameAndModelNameAndYear("Toyota", "Corolla", 2020)).thenReturn(cars);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/Toyota/model/Corolla/year/2020"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is("1")))
	            .andExpect(jsonPath("$[0].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[0].year", is(2020)))
	            .andExpect(jsonPath("$[0].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[0].category.name", is("Sedan")))
	            .andExpect(jsonPath("$[1].id", is("2")))
	            .andExpect(jsonPath("$[1].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[1].year", is(2020)))
	            .andExpect(jsonPath("$[1].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[1].category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).getByMakeNameAndModelNameAndYear("Toyota", "Corolla", 2020);
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testGetCarsByMakeNameAndModelNameAndYearWithPaginationAndSorting() throws Exception {
		
	    List<CarDTO> cars = createCarList();
	    Mockito.when(carService.getByMakeNameAndModelNameAndYearWithPaginationAndSorting("Toyota", "Corolla", 2020, 0, 2, "year")).thenReturn(cars);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/make/Toyota/model/Corolla/year/2020/page-sort/0/2/year"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is("1")))
	            .andExpect(jsonPath("$[0].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[0].year", is(2020)))
	            .andExpect(jsonPath("$[0].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[0].category.name", is("Sedan")))
	            .andExpect(jsonPath("$[1].id", is("2")))
	            .andExpect(jsonPath("$[1].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[1].year", is(2020)))
	            .andExpect(jsonPath("$[1].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[1].category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).getByMakeNameAndModelNameAndYearWithPaginationAndSorting("Toyota", "Corolla", 2020, 0, 2, "year");
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testSearchCars() throws Exception {
		
	    List<CarDTO> cars = createCarList();
	    Mockito.when(carService.search("Toyota", "Corolla", "Sedan", 2015, 2021)).thenReturn(cars);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/search")
	            .param("make", "Toyota")
	            .param("model", "Corolla")
	            .param("category", "Sedan")
	            .param("minYear", "2015")
	            .param("maxYear", "2021"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is("1")))
	            .andExpect(jsonPath("$[0].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[0].year", is(2020)))
	            .andExpect(jsonPath("$[0].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[0].category.name", is("Sedan")))
	            .andExpect(jsonPath("$[1].id", is("2")))
	            .andExpect(jsonPath("$[1].make.name", is("Toyota")))
	            .andExpect(jsonPath("$[1].year", is(2020)))
	            .andExpect(jsonPath("$[1].model.name", is("Corolla")))
	            .andExpect(jsonPath("$[1].category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).search("Toyota", "Corolla", "Sedan", 2015, 2021);
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testSearchCarsWithPaginationAndSorting() throws Exception {
		
		List<CarDTO> cars = createCarList();
		Mockito.when(carService.searchWithPaginationAndSorting("Toyota", "Corolla", "Sedan", 2015, 2021, 0, 2, "year")).thenReturn(cars);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/search/page-sort")
				.param("make", "Toyota")
				.param("model", "Corolla")
				.param("category", "Sedan")
				.param("minYear", "2015")
				.param("maxYear", "2021")
				.param("offset", "0")
				.param("pageSize", "2")
				.param("field", "year"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is("1")))
		.andExpect(jsonPath("$[0].make.name", is("Toyota")))
		.andExpect(jsonPath("$[0].year", is(2020)))
		.andExpect(jsonPath("$[0].model.name", is("Corolla")))
		.andExpect(jsonPath("$[0].category.name", is("Sedan")))
		.andExpect(jsonPath("$[1].id", is("2")))
		.andExpect(jsonPath("$[1].make.name", is("Toyota")))
		.andExpect(jsonPath("$[1].year", is(2020)))
		.andExpect(jsonPath("$[1].model.name", is("Corolla")))
		.andExpect(jsonPath("$[1].category.name", is("Sedan")));
		
		Mockito.verify(carService, Mockito.times(1)).searchWithPaginationAndSorting("Toyota", "Corolla", "Sedan", 2015, 2021, 0, 2, "year");
		Mockito.verifyNoMoreInteractions(carService);
		
	}
	
	@Test
	void testGetCarById() throws Exception {
		
	    CarDTO car = new CarDTO();
	    car.setId("1");
	    car.setMake(new MakeDTO(1l, "Toyota"));
	    car.setModel(new ModelDTO(1l, "Corolla"));
	    car.setYear(2020);
	    car.setCategory(new CategoryDTO(1l, "Sedan"));

	    Mockito.when(carService.getById("1")).thenReturn(car);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/1"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.id", is("1")))
	            .andExpect(jsonPath("$.make.name", is("Toyota")))
	            .andExpect(jsonPath("$.model.name", is("Corolla")))
	            .andExpect(jsonPath("$.year", is(2020)))
	            .andExpect(jsonPath("$.category.name", is("Sedan")));

	    Mockito.verify(carService, Mockito.times(1)).getById("1");
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testSaveCar() throws Exception {
		
	    CarDTO car = new CarDTO();
	    car.setMake(new MakeDTO(1l, "Toyota"));
	    car.setModel(new ModelDTO(1l, "Corolla"));
	    car.setCategory(new CategoryDTO(1l, "Sedan"));
	    car.setYear(2020);

	    Mockito.when(carService.save(Mockito.any(CarDTO.class))).thenReturn(car);

	    String carJson = new ObjectMapper().writeValueAsString(car);

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(carJson)
	            .with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password")))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.id", is(notNullValue())))
	            .andExpect(jsonPath("$.make.name", is("Toyota")))
	            .andExpect(jsonPath("$.model.name", is("Corolla")))
	            .andExpect(jsonPath("$.category.name", is("Sedan")))
	            .andExpect(jsonPath("$.year", is(2020)));

	    Mockito.verify(carService, Mockito.times(1)).save(Mockito.any(CarDTO.class));
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testUpdateCar() throws Exception {
		
	    String carId = "1";
	    CarDTO updatedCar = new CarDTO();
	    updatedCar.setId(carId);
	    updatedCar.setMake(new MakeDTO(1l, "Toyota"));
	    updatedCar.setModel(new ModelDTO(1l, "Corolla"));
	    updatedCar.setCategory(new CategoryDTO(1l, "Sedan"));
	    updatedCar.setYear(2020);

	    Mockito.when(carService.update(Mockito.eq(carId), Mockito.any(Car.class))).thenReturn(updatedCar);

	    String carJson = new ObjectMapper().writeValueAsString(updatedCar);
	    mockMvc.perform(MockMvcRequestBuilders.put("/api/cars/{id}", carId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(carJson))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.id", is(carId)))
	            .andExpect(jsonPath("$.make.name", is("Toyota")))
	            .andExpect(jsonPath("$.model.name", is("Corolla")))
	            .andExpect(jsonPath("$.category.name", is("Sedan")))
	            .andExpect(jsonPath("$.year", is(2020)));

	    Mockito.verify(carService, Mockito.times(1)).update(Mockito.eq(carId), Mockito.any(Car.class));
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}
	
	@Test
	void testDeleteCarById() throws Exception {
		
	    mockMvc.perform(MockMvcRequestBuilders.delete("/api/cars/1"))
	            .andExpect(MockMvcResultMatchers.status().isNoContent());

	    Mockito.verify(carService, Mockito.times(1)).delete("1");
	    Mockito.verifyNoMoreInteractions(carService);
	    
	}

}