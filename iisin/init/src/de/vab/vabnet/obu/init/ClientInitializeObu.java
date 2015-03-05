/**
 *
 */
package de.vab.vabnet.obu.init;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.vab.vabnet.common.ConstantsVABnet;
import de.vab.vabnet.common.ConstantsVABnet.EinsatzFall;
import de.vab.vabnet.obu.bean.ObuRemote;
import de.vab.vabnet.obu.domain.ObuGruppe;
import de.vab.vabnet.obu.domain.ObuParameter;
import de.vab.vabnet.obu.domain.OnBoardUnit;
import de.vab.vabnet.rmi.RmiResponse;
import de.vab.vabnet.stammdaten.bean.StammdatenRemote;
import de.vab.vabnet.stammdaten.domain.Unternehmen;

/**
 * @author moehle
 *
 * @version $Id$
 *
 */
public class ClientInitializeObu {

    private static String user;

    private static String password;

    /**
     *
     */
    public ClientInitializeObu() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) throws NamingException {

        login();
        InitialContext ctxFahrplan = getInitialContext();
        ObuRemote obuRemote = (ObuRemote) ctxFahrplan
                .lookup(ConstantsVABnet.contextRoot
                        + ObuRemote.JNDI_REMOTE_NAME);
        StammdatenRemote stammRemote = (StammdatenRemote) ctxFahrplan
                .lookup(ConstantsVABnet.contextRoot
                        + StammdatenRemote.JNDI_REMOTE_NAME);
        initObu(obuRemote, stammRemote);
    }

    private static void initObu(ObuRemote obuRemote, StammdatenRemote stammRemote) {

        RmiResponse res;
        ObuGruppe obuGruppe = new ObuGruppe();
        obuGruppe.setEinsatzfall(EinsatzFall.ALLGEMEIN);
        obuGruppe.setGruppenname("Tf");

        Unternehmen un = null;
        List<Unternehmen> unternehmen = stammRemote.getAllUnternehmen();
        for (Unternehmen u : unternehmen) {
            if (u.getIdvu().equals("PEG"))
                un = u;
        }
        obuGruppe.setUnternehmen(un);

        res = obuRemote.addObuGruppe(obuGruppe);
        if (res.isReturnCode() == false) {
            System.out.println(res.getMessage());
            return;
        }
        System.out.println(res.getObject());
        obuGruppe = (ObuGruppe) res.getObject();

        List<ObuGruppe> list = obuRemote.getAllObuGruppe();
        for (ObuGruppe o : list) {
            System.out.println(o);
        }

        OnBoardUnit obu = new OnBoardUnit();
        obu.setImei("35837501004580901");
        obu.setGruppe(obuGruppe);
        res = obuRemote.addOnBoardUnit(obu);
        System.out.println(res.getMessage());
        System.out.println(res.getObject());

    }

    private static void login() {
        user = "VABNET-intern";
        password = "VABNET-intern";
    }

    public static InitialContext getInitialContext() throws NamingException {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, user);
        env.setProperty(Context.SECURITY_CREDENTIALS, password);
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        return new InitialContext(env);
    }
}
