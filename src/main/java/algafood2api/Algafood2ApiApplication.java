package algafood2api;

import algafood2api.infrastructure.repository.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class Algafood2ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Algafood2ApiApplication.class, args);
	}

}
