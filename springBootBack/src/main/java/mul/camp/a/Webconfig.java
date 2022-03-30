package mul.camp.a;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//설정이라서 자동으로 실행
@Configuration
public class Webconfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		//WebMvcConfigurer.super.addCorsMappings(registry);
		
		//registry.addMapping("/**").allowedOrigins("http://localhost:8090");	//특정 url만 허용
		registry.addMapping("/**").allowedOrigins("*");	//모든 url 허용
	}

}
