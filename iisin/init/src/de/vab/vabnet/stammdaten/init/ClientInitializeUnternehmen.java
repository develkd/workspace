/**
 *
 */
package de.vab.vabnet.stammdaten.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.vab.vabnet.common.ConstantsVABnet;
import de.vab.vabnet.rmi.RmiResponse;
import de.vab.vabnet.stammdaten.bean.StammdatenRemote;
import de.vab.vabnet.stammdaten.domain.Unternehmen;
import de.vab.vabnet.stammdaten.domain.UnternehmenPool;


/**
 * @author moehle
 *
 * @version $Id$
 *
 */
public class ClientInitializeUnternehmen {

    private static String user;
    private static String password;

    /**
     *
     */
    public ClientInitializeUnternehmen() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) throws NamingException {

        login();
        InitialContext ctxFahrplan = getInitialContext();
        StammdatenRemote stamm = (StammdatenRemote)
            ctxFahrplan.lookup(ConstantsVABnet.contextRoot + StammdatenRemote.JNDI_REMOTE_NAME);

        initUnternehmen(stamm);

//        initUnternehmenPools(stamm);

    }

    private static void initUnternehmen(StammdatenRemote stamm) {

        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setIdvu("VGP");
        unternehmen.setName("Verkehrsgesellschaft Prignitz mbH");

        RmiResponse res = stamm.addUnternehmen(unternehmen);

        System.out.println(res.isReturnCode());

        unternehmen = new Unternehmen();
        unternehmen.setIdvu("PEG");
        unternehmen.setName("Prignitzer Eisenbahn GmbH");

        res = stamm.addUnternehmen(unternehmen);

        System.out.println(res.isReturnCode());

    }

    private static void initUnternehmenPools(StammdatenRemote stamm) {

        List<Unternehmen> unternehmen = stamm.getAllUnternehmen();

        for (Unternehmen u : unternehmen) {

            UnternehmenPool pool = new UnternehmenPool();
            pool.setEditierbar(true);
            pool.setName(u.getName());

            List<Unternehmen> list = new ArrayList<Unternehmen>(1);
            list.add(u);
            pool.setUnternehmen(list);

            RmiResponse response = stamm.addUnternehmenPool(pool);
            System.out.println(response);

            List<UnternehmenPool> pools = new ArrayList<UnternehmenPool>(1);
            pools.add((UnternehmenPool) response.getObject());
            u.setUnternehmenPools(pools);
            Unternehmen un = stamm.updateUnternehmen(u);

            System.out.println(un);
        }
    }

    private static void login()
    {
        user = "VABNET-intern";
        password = "VABNET-intern";
    }

    public static InitialContext getInitialContext() throws NamingException
    {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, user);
        env.setProperty(Context.SECURITY_CREDENTIALS, password);
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        return new InitialContext(env);
    }

}
