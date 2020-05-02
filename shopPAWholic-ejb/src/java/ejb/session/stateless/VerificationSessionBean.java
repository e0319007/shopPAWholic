package ejb.session.stateless;

import entity.Verification;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewVerificationException;
import util.exception.InputDataValidationException;
import util.exception.VerificationNotFoundException;

@Stateless
public class VerificationSessionBean implements VerificationSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public VerificationSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Verification createNewVerification(Verification newVerification) throws InputDataValidationException, CreateNewVerificationException {
        Set<ConstraintViolation<Verification>> constraintViolations = validator.validate(newVerification);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newVerification);
                em.flush();
                return newVerification;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null
                        && ex.getCause().getCause() != null
                        && ex.getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                    throw new CreateNewVerificationException("Verification is already exist.");
                } else {
                    throw new CreateNewVerificationException("An unexpected error has occured: " + ex.getMessage());
                }
            } catch (Exception ex) {
                throw new CreateNewVerificationException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Verification retrieveVerificationByVerificationId(Long verificationId) throws VerificationNotFoundException {
        Verification verification = em.find(Verification.class, verificationId);

        if (verification != null) {
            return verification;
        } else {
            throw new VerificationNotFoundException("Verification Id " + verificationId + " does not exist!");
        }
    }

    @Override
    public List<Verification> retrieveAllVerifications() {
        Query query = em.createQuery("SELECT v FROM Verification v");
        List<Verification> verifications = query.getResultList();
        return verifications;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Verification>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
    
}
