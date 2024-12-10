package hotketok.hotketok_server.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로에 대해 CORS 정책 적용
                        .allowedOriginPatterns("https://api.hotketok.store") // 특정 도메인 허용
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH") // 허용할 HTTP 메서드
                        .exposedHeaders("Authorization", "RefreshToken") // 클라이언트에서 접근 가능한 헤더
                        .allowCredentials(true); // 인증 정보 포함 허용
            }
        };
    }
}

