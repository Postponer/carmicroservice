package ua.com.foxminded.carmicroservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserPermission {

	ADMIN_READ("admin:read"), 
	ADMIN_WRITE("admin:write");
	
	private final String permission;

}
