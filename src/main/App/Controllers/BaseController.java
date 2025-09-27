package Controllers;

import Models.Session;
import Services.ServiceFactory;

public abstract class BaseController {
    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    protected ServiceFactory serviceFactory;
    private  Session session;
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    public void initData(Session session) {};
}
