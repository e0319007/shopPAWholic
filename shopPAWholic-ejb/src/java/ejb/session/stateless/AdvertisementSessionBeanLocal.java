package ejb.session.stateless;

import entity.Advertisement;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;

@Local
public interface AdvertisementSessionBeanLocal {

    public void updateAdvertisement(Advertisement advertisement) throws InputDataValidationException;

    public void deleteAdvertisement(Long advertisementId) throws AdvertisementNotFoundException;

    public Advertisement retrieveAdvertisementById(Long id) throws AdvertisementNotFoundException;

    public List<Advertisement> retrieveAllAdvertisements();

    public List<String> retrieveAdvertisementImages();

    public List<Advertisement> retrieveAdvertisementsBySellerId(Long sellerId);

    public Map<String, Integer> retrieveTotalNumberOfAdvertisementsForDay();

    public Map<String, Integer> retrieveTotalNumberOfAdvertisementsForTheYear();

    public Advertisement createNewAdvertisement(Advertisement advertisement, Long sellerId, String ccNum) throws CreateNewAdvertisementException, InputDataValidationException;

}
