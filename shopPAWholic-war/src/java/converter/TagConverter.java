package converter;

import entity.Tag;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "tagConverter", forClass = Tag.class)

public class TagConverter implements Converter 
{
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.length() == 0 || value.equals("null")) 
        {
            return null;
        }

        try
        {            
            Long objLong = Long.parseLong(value);
            
            List<Tag> tags = (List<Tag>)context.getExternalContext().getSessionMap().get("TagConverter_tags");
            
            for(Tag tag:tags)
            {
                if(tag.getTagId().equals(objLong))
                {
                    return tag;
                }
            }
        }
        catch(Exception ex)
        {
            throw new IllegalArgumentException("Please select a valid value");
        }
        
        return null;
    }

    
    
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null) 
        {
            return "";
        }
        
        if (value instanceof String)
        {
            return "";
        }

        
        
        if (value instanceof Tag)
        {            
            Tag tag = (Tag )value;                        
            
            try
            {
                return tag.getTagId().toString();
            }
            catch(Exception ex)
            {
                throw new IllegalArgumentException("Invalid value");
            }
        }
        else 
        {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
