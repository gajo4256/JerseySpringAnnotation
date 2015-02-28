package com;

import com.example.config.SpringAnnotationConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * We need @Order annotation because sometimes custom WebApplicationInitializer and Jersey's SpringWebApplicationInitializer
 * will try to load, but not sure which context will get loaded first
*/
@Order(1)
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // needed for Spring annotation configuration (we don't have web.xml in this project)
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // main Spring configuration class
        context.register(SpringAnnotationConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        // we have to add this line, otherwise it will search for applicationContext.xml on classpath
        servletContext.setInitParameter("contextConfigLocation", "");

        // from here to bottom are parameters that should usually live in web.xml
        final ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", new ServletContainer());
        appServlet.setInitParameter("javax.ws.rs.Application", "com.example.config.JerseyConfig");
        appServlet.setLoadOnStartup(1);

        final Set<String> mappingConflicts = appServlet.addMapping("/api/*");

        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException("'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }
    }
}
