package ua.com.foxminded.carmicroservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ua.com.foxminded.carmicroservice.dao.CarDAO;
import ua.com.foxminded.carmicroservice.dto.CarDTO;
import ua.com.foxminded.carmicroservice.models.Car;

@Service
@AllArgsConstructor
public class CarService {

	private CarDAO carDao;
	private ModelMapper modelMapper;

	public CarDTO getById(String carId) {

		Car car = carDao.findById(carId).orElse(null);

		return modelMapper.map(car, CarDTO.class);

	}

	public List<CarDTO> getAll() {

		List<Car> carList = carDao.findAll();

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getAllWithPaginationAndSorting(int offset, int pageSize, String field) {

		List<Car> carList = carDao
				.findAll(PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.ASC, field)))).getContent();

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();
	}

	public List<CarDTO> getByMakeName(String makeName) {

		List<Car> carList = carDao.getByMakeName(makeName);

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getByMakeNameWithPaginationAndSorting(String makeName, int offset, int pageSize, String field) {

		List<Car> carList = carDao.getByMakeName(makeName,
				PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.ASC, field)))).getContent();

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getByMakeNameAndModelName(String makeName, String modelName) {

		List<Car> carList = carDao.getByMakeNameAndModelName(makeName, modelName);

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getByMakeNameAndModelNameWithPaginationAndSorting(String makeName, String modelName, int offset,
			int pageSize, String field) {

		List<Car> carList = carDao.getByMakeNameAndModelName(makeName, modelName,
				PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.ASC, field)))).getContent();

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getByMakeNameAndModelNameAndYear(String makeName, String modelName, int year) {

		List<Car> carList = carDao.getByMakeNameAndModelNameAndYear(makeName, modelName, year);

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> getByMakeNameAndModelNameAndYearWithPaginationAndSorting(String makeName, String modelName,
			int year, int offset, int pageSize, String field) {

		List<Car> carList = carDao.getByMakeNameAndModelNameAndYear(makeName, modelName, year,
				PageRequest.of(offset, pageSize).withSort((Sort.by(Sort.Direction.ASC, field)))).getContent();

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> search(String makeName, String modelName, String categoryName, Integer minYear,
			Integer maxYear) {

		List<Car> carList;

		if (minYear == null && maxYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryName(makeName, modelName, categoryName);

		} else if (minYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearLessThanEqual(makeName, modelName,
					categoryName, maxYear.intValue());

		} else if (maxYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearGreaterThanEqual(makeName, modelName,
					categoryName, minYear.intValue());

		} else {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearBetween(makeName, modelName, categoryName,
					minYear, maxYear);
		}

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	public List<CarDTO> searchWithPaginationAndSorting(String makeName, String modelName, String categoryName,
			Integer minYear, Integer maxYear, int offset, int pageSize, String field) {

		List<Car> carList;
		PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, field));

		if (minYear == null && maxYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryName(makeName, modelName, categoryName, pageRequest).getContent();

		} else if (minYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearLessThanEqual(makeName, modelName,
					categoryName, maxYear.intValue(), pageRequest).getContent();

		} else if (maxYear == null) {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearGreaterThanEqual(makeName, modelName,
					categoryName, minYear.intValue(), pageRequest).getContent();

		} else {

			carList = carDao.getByMakeNameOrModelNameOrCategoryNameOrYearBetween(makeName, modelName, categoryName,
					minYear, maxYear, pageRequest).getContent();
		}

		return carList.stream().map(car -> modelMapper.map(car, CarDTO.class)).toList();

	}

	@Transactional
	public CarDTO save(CarDTO carDto) {

		Car savedCar = carDao.save(modelMapper.map(carDto, Car.class));

		return modelMapper.map(savedCar, CarDTO.class);

	}

	@Transactional
	public CarDTO update(String carId, Car car) {

		Car existingCar = carDao.findById(carId).orElse(null);
		existingCar.setMake(car.getMake());
		existingCar.setModel(car.getModel());
		existingCar.setYear(car.getYear());
		existingCar.setCategory(car.getCategory());
		return modelMapper.map(carDao.save(existingCar), CarDTO.class);

	}

	@Transactional
	public boolean delete(String carId) {

		try {

			carDao.deleteById(carId);
			return true;

		} catch (Exception e) {

			return false;

		}

	}

}