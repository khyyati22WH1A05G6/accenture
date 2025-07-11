package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.model.Registration;

@ApplicationScoped
public class RegistrationRepository implements PanacheRepository<Registration> {

    public void save(Registration registration) {
        persist(registration);
    }
}
