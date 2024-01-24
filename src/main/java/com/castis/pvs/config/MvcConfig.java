package com.castis.pvs.config;

import com.castis.external.common.util.CustomMessageSourceAccessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = { "com.castis" })
public class MvcConfig extends WebMvcConfigurerAdapter {

	private static final int BROWSER_CACHE_CONTROL = 604800;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(BROWSER_CACHE_CONTROL);
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(BROWSER_CACHE_CONTROL);
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(BROWSER_CACHE_CONTROL);
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(BROWSER_CACHE_CONTROL);
		registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(BROWSER_CACHE_CONTROL);
	}



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		registry.addInterceptor(localeInterceptor).addPathPatterns("/**");
	}

	@Bean
	public UrlBasedViewResolver jspViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		return viewResolver;
	}

	@Bean
	public LocaleResolver localeResolver(Environment env) {

		String languageCode = env.getProperty("system.language", "th");

		CookieLocaleResolver r = new CookieLocaleResolver();
		r.setDefaultLocale(new Locale(languageCode));
		r.setCookieName("localeInfo");

		// if set to -1, the cookie is deleted
		// when browser shuts down
		r.setCookieMaxAge(24 * 60 * 60);
		return r;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasenames("classpath:/i18n/messages", "classpath:/i18n/labels");
		bundle.setDefaultEncoding("UTF-8");
		return bundle;
	}

	@Bean
	public CustomMessageSourceAccessor wmSource(MessageSource messageSource) {
		return new CustomMessageSourceAccessor(messageSource);
	}


	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.put("org.springframework.security.access.AccessDeniedException",
				"common/exception/access_denied_popup");
		resolver.setExceptionMappings(mappings);
		return resolver;
	}


}
