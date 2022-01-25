package fr.formation.banque.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({
	"fr.formation.banque.persistance"
})
@EnableTransactionManagement
@PropertySource({
	"classpath:/db.properties"
})
public class ConfigurationPersistance {

	// Injection des propriétés contenu dans jpa.properties
	@Autowired
	private Environment env;
	
	@Value("classpath:/fr/formation/banque/scripts/schema-banque-drop.sql")
    private Resource dropSchemaScript;

    @Value("classpath:/fr/formation/banque/scripts/schema-banque.sql")
    private Resource createSchemaScript;
	
    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource());
        
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dropSchemaScript);
        populator.addScript(createSchemaScript);
        
        initializer.setDatabasePopulator(populator);
        return initializer;
    }		
	
	@Bean("dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}
	
	@Bean("transactionManager")
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}

	@Bean("jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
}
