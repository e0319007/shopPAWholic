package ejb.session.stateless;

import entity.Listing;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewListingException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.ListingSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UpdateListingException;

@Local
public interface ListingSessionBeanLocal {

    public Listing retrieveListingByListingId(Long listingId) throws ListingNotFoundException;

    public Listing retrieveListingBySkuCode(String skuCode) throws ListingNotFoundException;

    public List<Listing> retrieveAllListings();

    public Map<String, Integer> retrieveTotalNumberOfListingsPerCategory();

    public Map<String, Integer> retrieveTotalNumberOfListingsForTheYear();

    public Map<String, Integer> retrieveTotalNumberOfListingsForDay();

    public List<Listing> searchListingsByName(String searchName);

    public List<Listing> filterListingsByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Listing> filterListingsByTags(List<Long> tagIds, String condition);

    public void updateListing(Listing listing, Long categoryId, List<Long> tagIds) throws ListingNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateListingException, InputDataValidationException;

    public void deleteListing(Long listingId) throws ListingNotFoundException;

    public Listing createNewListing(Listing newListing, Long categoryId, List<Long> tagIds, Long sellerId) throws InputDataValidationException, ListingSkuCodeExistException, CreateNewListingException;
}
