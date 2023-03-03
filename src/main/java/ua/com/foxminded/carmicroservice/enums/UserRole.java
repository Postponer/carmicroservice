package ua.com.foxminded.carmicroservice.enums;

import static ua.com.foxminded.carmicroservice.enums.UserPermission.ADMIN_READ;
import static ua.com.foxminded.carmicroservice.enums.UserPermission.ADMIN_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

	ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE));

	private final Set<UserPermission> permissions;

	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

		Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;

	}

}