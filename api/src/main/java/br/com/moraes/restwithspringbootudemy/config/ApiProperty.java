package br.com.moraes.restwithspringbootudemy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("api")
public class ApiProperty {

	private final Swagger swagger = new Swagger();
	
	private final Security security = new Security();
	
	private final File file = new File();
	
	@Getter
	@Setter
	public static class Swagger {
		private boolean show = Boolean.TRUE;
	}
	
	@Getter
	@Setter
	public static class Security {
		private String secretKey = "secret";
		private long validityInMilliseconds = 3600000;
	}

	@Getter
	@Setter
	public static class File {
		private String uploadDir = "/Users/sulei/OneDrive/Documentos/mada/cursos/restful-apis-do-0-a-nuvem-com-springboot-e-docker/files";
	}
}
