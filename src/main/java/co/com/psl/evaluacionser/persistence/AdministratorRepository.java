package co.com.psl.evaluacionser.persistence;

import co.com.psl.evaluacionser.service.dto.Administrator;

public interface AdministratorRepository {
    Administrator findAdministrator();

    Administrator updateAdministrator(Administrator administrator);
}
