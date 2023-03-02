package ua.com.foxminded.carmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.com.foxminded.carmicroservice.dao.UserDAO;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

	}

}