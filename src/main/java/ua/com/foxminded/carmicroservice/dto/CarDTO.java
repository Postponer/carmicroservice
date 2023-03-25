package ua.com.foxminded.carmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
	
	private String id;
	
	private MakeDTO make;
	
	private int year;
	
	private ModelDTO model;
	
	private CategoryDTO category;

}