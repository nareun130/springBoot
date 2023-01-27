package com.h6;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*스프링 웹 어플리케이션이 Tomcat에서 동작되기 위해서 Web.xml에 ApplicationContext가 필요
 *Apache Tomcat이 구동될 때 web.xml을 읽어 웹 애플리케이션을 구성하기 때문 
 *이런 web.xml설정을 WebApplicationInitializer 인터페이스를 구현하여 대신할 수 있게됨
 *이를 구현한 SpringBootServletInitializer를 상속받아 외부 Tomcat에서 스프링부트가 실행되도록 해줌.
*/
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		//
		return application.sources(MovieApplication.class);
	}

}
