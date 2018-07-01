package ml.darklyn.RUVaiAbrir.config;

import javax.annotation.PostConstruct;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.user.roles.RoleRepository;

@Repository
public class JdbcRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;		
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@PostConstruct
	private void createIfDoesntExists() {
		if (roleRepository.count() == 0) {
			createRoles();
		}
	}
	
	public void createRoles() {
		jdbcTemplate.update("INSERT INTO tb_roles (name) VALUES ('ROLE_ADMIN')");
		jdbcTemplate.update("INSERT INTO tb_roles (name) VALUES ('ROLE_EMPLOYEE')");
		jdbcTemplate.update("INSERT INTO tb_roles (name) VALUES ('ROLE_USER')");
	}
}
