/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BilingDetail;
import javax.ejb.Local;
import util.exception.BilingDetailNotFoundException;
import util.exception.CreateNewBilingDetailException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Local
public interface BilingDetailSessionBeanLocal {

    public BilingDetail createNewBilingDetail(BilingDetail bilingDetail) throws CreateNewBilingDetailException, InputDataValidationException;

    public void updateBilingDetail(BilingDetail bilingDetail) throws InputDataValidationException;

    public BilingDetail getBilingDetailById(Long id) throws BilingDetailNotFoundException;
    
}
