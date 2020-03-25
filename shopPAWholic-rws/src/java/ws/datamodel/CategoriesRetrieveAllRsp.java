/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Category;
import java.util.List;

/**
 *
 * @author shizhan
 */
public class CategoriesRetrieveAllRsp
{
    private List<Category> categoryEntities;

    
    
    public CategoriesRetrieveAllRsp()
    {
    }
    
    
    
    public CategoriesRetrieveAllRsp(List<Category> categoryEntities)
    {
        this.categoryEntities = categoryEntities;
    }

    
    
    public List<Category> getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(List<Category> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }
}