package com.authservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
	
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // Nombre base de tus archivos (messages.properties, messages_es.properties)
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); // Para desarrollo, recarga mensajes cada hora
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        // La forma preferida y no deprecada en Spring 6.0+ para establecer el nombre de la cookie.
        // Las propiedades como maxAge, path, etc., se gestionan internamente por CookieLocaleResolver
        // o se asumen sus valores por defecto (ej. maxAge por defecto suele ser -1 para cookie de sesión)
        // cuando se usa este constructor.
        // Si se requiere un control granular sobre estas propiedades (ej. un maxAge específico para persistencia),
        // se debería considerar implementar un LocaleResolver personalizado
        // o gestionar la cookie de localización a un nivel más bajo (ej. en un filtro o interceptor).
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver("language");
        cookieLocaleResolver.setDefaultLocale(new java.util.Locale("es", "ES")); // Idioma por defecto si no se especifica

        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // El parámetro de URL para cambiar el idioma (ej. ?lang=es)
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}