package ua.com.foxminded.carmicroservice.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.foxminded.carmicroservice.enums.UserRole;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ACCOUNT_NON_EXPIRED")
	private boolean accountNonExpired;

	@Column(name = "ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked;

	@Column(name = "CREDENTIALS_NON_EXPIRED")
	private boolean credentialsNonExpired;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return role.getGrantedAuthorities();

	}

	@Override
	public boolean isAccountNonExpired() {

		return accountNonExpired;

	}

	@Override
	public boolean isAccountNonLocked() {

		return accountNonLocked;

	}

	@Override
	public boolean isCredentialsNonExpired() {

		return credentialsNonExpired;

	}

	@Override
	public boolean isEnabled() {

		return enabled;

	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;

	}

}
