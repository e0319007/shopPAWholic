package ejb.session.stateless;

import entity.Seller;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.SellerNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Local
public interface SellerSessionBeanLocal {

    public Long createNewSeller(Seller newSeller) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Seller> retrieveAllSellers();

    public Map<String, Integer> retrieveTotalNumberOfSellersForTheYear();

    public void updateSeller(Seller seller) throws SellerNotFoundException, InputDataValidationException;

    public Seller retrieveSellerByUsername(String username) throws SellerNotFoundException;

    public Seller retrieveSellerById(Long sellerId) throws SellerNotFoundException;
}
