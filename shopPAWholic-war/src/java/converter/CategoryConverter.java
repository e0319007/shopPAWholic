package converter;

import entity.Category;
import entity.Tag;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryConverter", forClass = Tag.class)

public class CategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$ con"+ context);
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$ com"+ component);
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$ val"+ value);
        if (value == null || value.length() == 0 || value.equals("null")) {
            return null;
        }

        try {
            Long objLong = Long.parseLong(value);
            List<Category> categories = (List<Category>) context.getExternalContext().getSessionMap().get("CategoryConverter_categories");
            
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^ categories" + categories );
            for (Category category : categories) {
                if (category.getCategoryId().equals(objLong)) {
                    return category;
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Please select a valid value");
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return "";
        }

        if (value instanceof Category) {
            Category category = (Category) value;

            try {
                return category.getCategoryId().toString();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
