package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AdminUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    

    @PostConstruct
    public void postConstruct() {
        if (em.find(Admin.class, 1l) == null) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            adminSessionBeanLocal.createNewAdmin(new Admin("manager", "password"));
        } catch (AdminUsernameExistException | UnknownPersistenceException | InputDataValidationException ex) {
            ex.printStackTrace();
        }
    }

}
