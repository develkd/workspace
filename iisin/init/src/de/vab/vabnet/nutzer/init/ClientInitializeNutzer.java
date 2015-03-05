/**
 *
 */
package de.vab.vabnet.nutzer.init;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.person.resources.Person_PersonTypMessageKey;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.stammdaten.admin.remote.AdminRemote;
import de.ev.iisin.common.util.Constants;
import de.ev.iisin.common.util.PersonenTypen;
import de.ev.iisin.common.util.crypt.Password;

/**
 * @author moehle
 * 
 * @version $Id$
 * 
 */
public class ClientInitializeNutzer {

	private static String userNutzer;

	private static String passwordNutzer;

	private static String userStammdaten;

	private static String passwordStammdaten;

	/**
	 * 
	 */
	public ClientInitializeNutzer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String[] args) throws NamingException,
			NoSuchAlgorithmException {

		loginNutzer();
		// lookup for SessionBean
		InitialContext ctxNutzer = getInitialContextNutzer();
		AdminRemote nutzerRemote = (AdminRemote) ctxNutzer
				.lookup(Constants.CONTEXT_ROOT + AdminRemote.JNDI_REMOTE_NAME);

		deleteNutzer(nutzerRemote);
		initNutzer(nutzerRemote);
		// testLogin(nutzerRemote);
	}

	// private static void testLogin(NutzerRemote nutzerRemote)
	// throws NoSuchAlgorithmException, NamingException {
	//        
	// loginStammdaten();
	// InitialContext ctxStamm = getInitialContextStammdaten();
	// StammdatenRemote stammRemote = (StammdatenRemote) ctxStamm
	// .lookup(ConstantsVABnet.contextRoot
	// + StammdatenRemote.JNDI_REMOTE_NAME);
	//        
	// Unternehmen un = null;
	// List<Unternehmen> unternehmen = stammRemote.getAllUnternehmen();
	// for (Unternehmen u : unternehmen) {
	// if (u.getIdvu().equals("2011")) un = u;
	// }
	//        
	// Nutzer n = nutzerRemote
	// .login("admin", Password.encryptMD5("admin"), un);
	//        
	// System.out.println(n);
	//        
	// }

	private static void initNutzer(AdminRemote adminRemote)
			throws NoSuchAlgorithmException, NamingException {

		// PersonTyp typ = new PersonTyp();
		// typ.setBezeichnung("Admin");
		// RmiResponse resp = adminRemote.addPersonTyp(typ);
		// typ = (PersonTyp) resp.getObject();
		PersonTyp adminTyp = createPersonTyps(adminRemote);
		Admin admin = new Admin();
		admin.setPersonTyp(adminTyp);
		admin.setName("admin");
		admin.setAdmin(true);
		admin.setPasswort(Password.encryptMD5("admin"));
		admin.setDeleted(false);
		admin.setWeiblich(false);
		RmiResponse resp = adminRemote.addAdmin(admin);
		admin = (Admin) resp.getObject();
		admin = adminRemote.login("admin", Password.encryptMD5("admin"));
		System.out.println(admin);

	}

	private static PersonTyp createPersonTyps(AdminRemote adminRemote) {
		PersonTyp adminTyp = null;
//		PersonTyp typ = new PersonTyp();
//		typ.setBezeichnung("Admin");
//		typ.setId(1);
//		RmiResponse resp = adminRemote.addPersonTyp(typ);
//		adminTyp = (PersonTyp) resp.getObject();
//		return adminTyp;
		
		for (PersonenTypen typen : PersonenTypen.values()) {
			PersonTyp typ = new PersonTyp();
			typ.setBezeichnung(typen.getBezeichnung());
			typ.setId(typen.ordinal());
			RmiResponse resp = adminRemote.addPersonTyp(typ);
			if(!resp.isReturnCode()){
				System.out.println((Person_PersonTypMessageKey)resp.getObject());
				return null;
			}
			if (typen == PersonenTypen.ADMIN)
				adminTyp = (PersonTyp) resp.getObject();
		}
		return adminTyp;

	}

	private static void deleteNutzer(AdminRemote nutzerRemote)
			throws NoSuchAlgorithmException, NamingException {

		List<Admin> admin = nutzerRemote.getAllAdmin();
		for (Admin admin2 : admin) {
			nutzerRemote.deleteAdmin(admin2);
			System.out.println("Lösche admin " + admin2.getName());
		}
	}

	private static void loginNutzer() {
		userNutzer = "IISIN-intern";
		passwordNutzer = "IISIN-intern";
	}

	public static InitialContext getInitialContextNutzer()
			throws NamingException {
		Properties env = new Properties();
		env.setProperty(Context.SECURITY_PRINCIPAL, userNutzer);
		env.setProperty(Context.SECURITY_CREDENTIALS, passwordNutzer);
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.security.jndi.JndiLoginInitialContextFactory");

		return new InitialContext(env);
	}

}
