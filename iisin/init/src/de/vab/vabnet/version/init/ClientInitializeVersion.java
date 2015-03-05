/**
 *
 */
package de.vab.vabnet.version.init;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.vab.vabnet.common.ConstantsVABnet;
import de.vab.vabnet.common.ConstantsVABnet.SoftwareName;
import de.vab.vabnet.common.ConstantsVABnet.VersionType;
import de.vab.vabnet.fahrzeug.bean.FahrzeugRemote;
import de.vab.vabnet.nutzer.domain.Nutzerfunktion;
import de.vab.vabnet.rmi.RmiResponse;
import de.vab.vabnet.stammdaten.bean.StammdatenRemote;
import de.vab.vabnet.stammdaten.domain.Unternehmen;
import de.vab.vabnet.verwaltung.bean.VersionRemote;
import de.vab.vabnet.verwaltung.domain.SoftwareVersion;
import de.vab.vabnet.verwaltung.domain.Version;


/**
 * @author moehle
 *
 * @version $Id$
 *
 */
public class ClientInitializeVersion {

    private static String user;

    private static String password;

    /**
     *
     */
    public ClientInitializeVersion() {
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

        VersionRemote versionRemote = (VersionRemote)
            ctx.lookup(ConstantsVABnet.contextRoot + VersionRemote.JNDI_REMOTE_NAME);
        StammdatenRemote stammRemote = (StammdatenRemote) ctx
        .lookup(ConstantsVABnet.contextRoot
                + StammdatenRemote.JNDI_REMOTE_NAME);

        initSwVersion(versionRemote, stammRemote);

//        initVersionen(versionRemote, stammRemote);
    }

    private static void initVersionen(VersionRemote versionRemote,
            StammdatenRemote stammRemote) {

        RmiResponse res;

        Unternehmen un = null;
        List<Unternehmen> unternehmen = stammRemote.getAllUnternehmen();
        for (Unternehmen u : unternehmen) {
            if (u.getIdvu().equals("200"))
                un = u;
        }

        Version version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.FAHRTNUMMER);
        version.setBezeichnung("Fahrtnummer");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.FAHRZEUG);
        version.setBezeichnung("Fahrzeuge");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.HALTESTELLE);
        version.setBezeichnung("Haltestellen");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.MESSPUNKT);
        version.setBezeichnung("Messpunkte");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.NUTZER);
        version.setBezeichnung("Nutzer");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.STOERFALLCODE);
        version.setBezeichnung("Störfallcodes");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

        version = new Version();
        version.setUnternehmen(un);
        version.setVersionTyp(VersionType.VORLAGE_TEXTMELDUNG);
        version.setBezeichnung("Vorlagen Textmeldung");
        version.setVersion(1);

        res = versionRemote.addVersion(version);

        version = (Version) res.getObject();
        System.out.println(version);

    }

    private static void initSwVersion(VersionRemote versionRemote, StammdatenRemote stammRemote) {

        RmiResponse res;

        Unternehmen un = null;
        List<Unternehmen> unternehmen = stammRemote.getAllUnternehmen();
        for (Unternehmen u : unternehmen) {
            if (u.getIdvu().equals("2011"))
                un = u;
        }

        SoftwareVersion swVersion = new SoftwareVersion();
        swVersion.setCabUrl("http://80.153.134.115/vabnet/vgp/download/");
        swVersion.setDbVersion("1.0.0");
        swVersion.setProgramm(SoftwareName.VABNET_BUS);
        swVersion.setUnternehmen(un);
        swVersion.setVersion("1.0.0");

        res = versionRemote.addSoftwareVersion(swVersion);
        System.out.println(res.getMessage());
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
