/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewListingException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.ListingSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UpdateListingException;

/**
 *
 * @author EileenLeong
 */
@Local
public interface ListingSessionBeanLocal {

    public Listing retrieveListingByListingId(Long listingId) throws ListingNotFoundException;

    public Listing retrieveListingBySkuCode(String skuCode) throws ListingNotFoundException;

    public List<Listing> retrieveAllListings();

    public List<Listing> searchListingsByName(String searchName);

    public List<Listing> filterListingsByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Listing> filterListingsByTags(List<Long> tagIds, String condition);
    
    public void updateListing(Listing listing, Long categoryId, List<Long> tagIds) throws ListingNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateListingException, InputDataValidationException;
    
    public void deleteListing(Long listingId) throws ListingNotFoundException;

    public Listing createNewListing(Listing newListing, Long categoryId, List<Long> tagIds, Long sellerId) throws InputDataValidationException, ListingSkuCodeExistException, CreateNewListingException;
}
