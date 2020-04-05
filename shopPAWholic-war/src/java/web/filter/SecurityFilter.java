package web.filter;

import entity.Admin;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    FilterConfig filterConfig;
    private static final String CONTEXT_ROOT = "/shopPAWholic-war";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }
        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");
        Boolean isResources = httpServletRequest.getRequestURI().startsWith(CONTEXT_ROOT + "/faces" + ResourceHandler.RESOURCE_IDENTIFIER);
        Boolean cssResources = httpServletRequest.getRequestURI().endsWith(".css");
        
        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
                Admin currentAdmin = (Admin) httpSession.getAttribute("currentAdmin");
                User currentUser = (User) httpSession.getAttribute("currentUser");
                
                if (checkAccessRight(requestServletPath, currentAdmin, currentUser)) {
                    chain.doFilter(request, response);
                } else {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT);
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    public Boolean checkAccessRight(String path, Admin currentAdmin, User currentUser) {
        if (currentAdmin instanceof Admin) {
            if (path.equals("/adminOperation/adminFilterListingsByCategory.xhtml")
                    || path.equals("/adminOperation/adminCategoryList.xhtml")
                    || path.equals("/adminOperation/adminCustomerList.xhtml")
                    || path.equals("/adminOperation/adminFilterListingsByCategory.xhtml")
                    || path.equals("/adminOperation/adminFilterListingsByTags.xhtml")
                    || path.equals("/adminOperation/adminHomepage.xhtml")
                    || path.equals("/adminOperation/adminListingList.xhtml")
                    || path.equals("/adminOperation/adminSearchListingsByName.xhtml")
                    || path.equals("/adminOperation/adminSellerList.xhtml")
                    || path.equals("/adminOperation/adminTagList.xhtml")
                    || path.equals("/adminOperation/adminUserList.xhtml")
                    ) {
                return true;
            } else {
                return false;
            }
        } else if (currentUser instanceof Customer) {
            if (path.equals("/customerOperation/customerEventpage.xhtml")
                    || path.equals("/customerOperation/customerForumpage.xhtml")
                    || path.equals("/customerOperation/customerHomepage.xhtml")
                    || path.equals("/customerOperation/customerOperation.xhtml")
                    || path.equals("/customerOperation/customerFilterListingsByTags.xhtml")
                    || path.equals("/customerOperation/customerProduct.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (currentUser instanceof Seller) {
            if (path.equals("/sellerOperation/sellerEditProfile.xhtml")
                    || path.equals("/sellerOperation/sellerHomepage.xhtml")
                    || path.equals("/sellerOperation/sellerOperation.xhtml")
                    || path.equals("/sellerOperation/sellerProfile.xhtml")
                    || path.equals("/sellerOperation/sellerSellListing.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (currentUser == null && currentAdmin == null) {
            return false;
        }
        return false;
    }

    public Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.startsWith("/javax.faces.resource")
                || path.matches(".*(css|jpg|png|js|map)")) {
            return true;
        } else {
            return false;
        }
    }
}
