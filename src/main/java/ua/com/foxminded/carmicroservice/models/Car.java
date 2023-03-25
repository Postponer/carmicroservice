package ua.com.foxminded.carmicroservice.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Car {

	@Id
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned", strategy = "assigned")
	@Column(name = "ID")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "make_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Make make;

	@Column(name = "YEAR")
	private int year;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "model_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Model model;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;

}
