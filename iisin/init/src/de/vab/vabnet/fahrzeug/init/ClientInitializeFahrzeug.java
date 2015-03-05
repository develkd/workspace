/**
 *
 */
package de.vab.vabnet.fahrzeug.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.vab.vabnet.common.ConstantsVABnet;
import de.vab.vabnet.fahrzeug.bean.FahrzeugRemote;
import de.vab.vabnet.fahrzeug.domain.Fahrzeug;
import de.vab.vabnet.fahrzeug.domain.Fahrzeugtyp;
import de.vab.vabnet.fahrzeug.domain.FahrzeugtypFahrplan;
import de.vab.vabnet.rmi.RmiResponse;
import de.vab.vabnet.stammdaten.bean.StammdatenRemote;
import de.vab.vabnet.stammdaten.domain.Unternehmen;

/**
 * @author moehle
 *
 * @version $Id$
 *
 */
public class ClientInitializeFahrzeug {

    private static String user;

    private static String password;

    /**
     *
     */
    public ClientInitializeFahrzeug() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) throws NamingException {

        login();

        // lookup for SessionBean
        InitialContext ctx = getInitialContext();

        FahrzeugRemote fahrzeugRemote = (FahrzeugRemote)
            ctx.lookup(ConstantsVABnet.contextRoot + FahrzeugRemote.JNDI_REMOTE_NAME);
        StammdatenRemote stammRemote = (StammdatenRemote) ctx
        .lookup(ConstantsVABnet.contextRoot
                + StammdatenRemote.JNDI_REMOTE_NAME);

        initFahrzeug(fahrzeugRemote, stammRemote);
    }

    private static void initFahrzeug(FahrzeugRemote fahrzeugRemote, StammdatenRemote stammRemote) {

        RmiResponse res;

        Unternehmen un = null;
        List<Unternehmen> unternehmen = stammRemote.getAllUnternehmen();
        for (Unternehmen u : unternehmen) {
            if (u.getIdvu().equals("VGP"))
                un = u;
        }
        System.out.println("UNTERNEHMEN" + un);

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setBezeichnung("Bus");
        fahrzeugtyp.setUnternehmen(un);
        res = fahrzeugRemote.addFahrzeugtyp(fahrzeugtyp);
        if (res.isReturnCode() == false) {
            System.out.println(res.getMessage());
        }
        Fahrzeugtyp typ3 = (Fahrzeugtyp)res.getObject();
        System.out.println(typ3);

        List<Fahrzeugtyp> list = new ArrayList<Fahrzeugtyp>();
        list.add(typ3);

        FahrzeugtypFahrplan fahrzeugtypFahrplan = new FahrzeugtypFahrplan();
        fahrzeugtypFahrplan.setBezeichnung("Bus");
        fahrzeugtypFahrplan.setFahrzeugtyp(list);
        res = fahrzeugRemote.addFahrzeugtypFahrplan(fahrzeugtypFahrplan);
        if (res.isReturnCode() == false) {
            System.out.println(res.getMessage());
        }
        System.out.println(res.getObject());

        addFahrzeug ("B-TR 92", typ3, un, fahrzeugRemote);
//        addFahrzeug ("2", typ3, un, fahrzeugRemote);
//        addFahrzeug ("3", typ3, un, fahrzeugRemote);
//        addFahrzeug ("4", typ3, un, fahrzeugRemote);
//        addFahrzeug ("5", typ3, un, fahrzeugRemote);
//        addFahrzeug ("6", typ3, un, fahrzeugRemote);
//        addFahrzeug ("7", typ3, un, fahrzeugRemote);
//        addFahrzeug ("8", typ3, un, fahrzeugRemote);
//        addFahrzeug ("9", typ3, un, fahrzeugRemote);
//        addFahrzeug ("10", typ3, un, fahrzeugRemote);
//        addFahrzeug ("11", typ3, un, fahrzeugRemote);
//        addFahrzeug ("12", typ3, un, fahrzeugRemote);
//        addFahrzeug ("13", typ3, un, fahrzeugRemote);
//        addFahrzeug ("14", typ3, un, fahrzeugRemote);
//        addFahrzeug ("15", typ5, un, fahrzeugRemote);
//        addFahrzeug ("16", typ5, un, fahrzeugRemote);
//        addFahrzeug ("17", typ5, un, fahrzeugRemote);
//        addFahrzeug ("18", typ5, un, fahrzeugRemote);
//        addFahrzeug ("19", typ5, un, fahrzeugRemote);

    }

    private static void addFahrzeug(String fzgnr, Fahrzeugtyp typ,
            Unternehmen unternehmen, FahrzeugRemote fahrzeugRemote) {

        Fahrzeug fahrzeug = new Fahrzeug();
        fahrzeug.setUnternehmen(unternehmen);
        fahrzeug.setFahrzeugnummer(fzgnr);
        fahrzeug.setBezeichnung(fzgnr);
        fahrzeug.setFahrzeugtyp(typ);
//        fahrzeug.setStandort("Bahnhof Rheine");
//        fahrzeug.setThe_geom();
        RmiResponse res = fahrzeugRemote.addFahrzeug(fahrzeug);
        if (res.isReturnCode() == false) {
            System.out.println(res.getMessage());
            return;
        }
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
