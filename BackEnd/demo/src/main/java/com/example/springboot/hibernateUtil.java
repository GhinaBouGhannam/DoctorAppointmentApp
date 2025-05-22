package com.example.springboot;

import com.example.springboot.model.Appointment;
import com.example.springboot.model.Doctor;
import com.example.springboot.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class hibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                //   settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/appointment_app");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                //settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.AUTO_CLOSE_SESSION, "drop");


                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Doctor.class);
                configuration.addAnnotatedClass(Appointment.class);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                try {

                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                    System.out.println(":::::::::::::::::::::::::::::");
                    System.out.println("DATABASE CONNECTED SUCCESSFULLY");
                    System.out.println(":::::::::::::::::::::::::::::");
                } catch (Exception e) {
                    System.out.println(":::::::::::::::::::::::::::::");
                    System.out.println(e.toString());
                    System.out.println(":::::::::::::::::::::::::::::");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
