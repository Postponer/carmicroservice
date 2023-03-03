package ua.com.foxminded.carmicroservice.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.carmicroservice.models.Car;

public interface CarDAO extends JpaRepository<Car, String> {

	List<Car> getByMakeName(String makeName);

	Page<Car> getByMakeName(String makeName, Pageable pageable);

	List<Car> getByMakeNameAndModelName(String makeName, String modelName);

	Page<Car> getByMakeNameAndModelName(String makeName, String modelName, Pageable pageable);

	List<Car> getByMakeNameAndModelNameAndYear(String makeName, String modelName, int year);

	Page<Car> getByMakeNameAndModelNameAndYear(String makeName, String modelName, int year, Pageable pageable);

	List<Car> getByMakeNameOrModelNameOrCategoryName(String makeName, String modelName, String categoryName);

	Page<Car> getByMakeNameOrModelNameOrCategoryName(String makeName, String modelName, String categoryName,
			Pageable pageable);

	List<Car> getByMakeNameOrModelNameOrCategoryNameOrYearLessThanEqual(String makeName, String modelName,
			String categoryName, int year);

	Page<Car> getByMakeNameOrModelNameOrCategoryNameOrYearLessThanEqual(String makeName, String modelName,
			String categoryName, int year, Pageable pageable);

	List<Car> getByMakeNameOrModelNameOrCategoryNameOrYearGreaterThanEqual(String makeName, String modelName,
			String categoryName, int year);

	Page<Car> getByMakeNameOrModelNameOrCategoryNameOrYearGreaterThanEqual(String makeName, String modelName,
			String categoryName, int year, Pageable pageable);

	List<Car> getByMakeNameOrModelNameOrCategoryNameOrYearBetween(String makeName, String modelName,
			String categoryName, int minYear, int maxYear);

	Page<Car> getByMakeNameOrModelNameOrCategoryNameOrYearBetween(String makeName, String modelName,
			String categoryName, int minYear, int maxYear, Pageable pageable);

}