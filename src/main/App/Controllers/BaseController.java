package Controllers;

import Services.ServiceFactory;

public abstract class BaseController {
    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    protected ServiceFactory serviceFactory;
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
