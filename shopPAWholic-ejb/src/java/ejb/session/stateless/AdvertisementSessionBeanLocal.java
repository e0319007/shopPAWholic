/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Advertisement;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Local
public interface AdvertisementSessionBeanLocal {

    public Advertisement createNewAdvertisement(Advertisement advertisement) throws CreateNewAdvertisementException, InputDataValidationException;

    public void updateAdvertisement(Advertisement advertisement) throws InputDataValidationException;

    public void deleteAdvertisement(Long advertisementId) throws AdvertisementNotFoundException;

    public Advertisement retrieveAdvertisementById(Long id) throws AdvertisementNotFoundException;

    public List<Advertisement> retrieveAllAdvertisements();
    
}
