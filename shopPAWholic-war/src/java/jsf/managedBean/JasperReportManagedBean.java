package jsf.managedBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@Named(value = "jasperReportManagedBean")
@RequestScoped
public class JasperReportManagedBean {

    @Resource(name = "shoppawholicDataSource")
    private DataSource shoppawholicDataSource;

    public JasperReportManagedBean() {
        System.out.println("********************************** JASPER REPORT IN");
    }

    public void generateReport(ActionEvent event) {
        try {
            System.out.println("********************************** JASPER REPORT IN");
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/allusersreport.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();

            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), shoppawholicDataSource.getConnection());
        } catch (IOException | JRException | SQLException ex) {
            Logger.getLogger(JasperReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
