/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Verification;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewVerificationException;
import util.exception.InputDataValidationException;
import util.exception.VerificationNotFoundException;

/**
 *
 * @author yeeqinghew
 */
@Local
public interface VerificationSessionBeanLocal {

    public List<Verification> retrieveAllVerifications();

    public Verification retrieveVerificationByVerificationId(Long verificationId) throws VerificationNotFoundException;

    public Verification createNewVerification(Verification newVerification) throws InputDataValidationException, CreateNewVerificationException;
    
}
