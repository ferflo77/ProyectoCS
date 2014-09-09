
import java.io.IOException;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.joseflores.db.MySQL;

@ManagedBean(name = "login")
@SessionScoped

public class Login {

    private MySQL conexionMySQL;
    private FacesContext contexto;
    private String usuarioIngresar;
    private String passwordIngresar;
    private boolean verificador;

    public Login(String usuarioIngresar, String passwordIngresar) {
        this.usuarioIngresar = usuarioIngresar;
        this.passwordIngresar = passwordIngresar;
    }

    public Login() {
    }

    public void acceder() {
        conexionMySQL = new MySQL("localhost", "root", "", 3306, "registro");
        conexionMySQL.conectar();
        ResultSet resultado = conexionMySQL.consulta("SELECT user_name, password FROM usuario where user_name = '" + getUsuarioIngresar() + "'");
        try {
            boolean acceso = false;
            while (resultado.next()) {
                if (resultado.getString("password").equals(getPasswordIngresar())) {
                    acceso = true;
                }
            }
            if (acceso) {
                String url = "/ProyectoCS/faces/Start.xhtml";
                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                try {
                    verificador = true;
                    ec.redirect(url);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                if (usuarioIngresar.equals("") && passwordIngresar.equals("")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Ingrese un usuario y contraseña correctos."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error:", "Ingrese datos correctamente."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsuarioIngresar() {
        return usuarioIngresar;
    }

    public void setUsuarioIngresar(String usuarioIngresar) {
        this.usuarioIngresar = usuarioIngresar;
    }

    public String getPasswordIngresar() {
        return passwordIngresar;
    }

    public void setPasswordIngresar(String passwordIngresar) {
        this.passwordIngresar = passwordIngresar;
    }

    public void verificarLogin() {
        String url = "/ProyectoCS/faces/Start.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        if (verificador == true) {
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void redireccionarIndex() {
        String url = "/ProyectoCS/faces/index.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        if (verificador == false) {
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void logout(){
        verificador=false;
        
        String url = "/ProyectoCS/faces/index.xhtml";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
