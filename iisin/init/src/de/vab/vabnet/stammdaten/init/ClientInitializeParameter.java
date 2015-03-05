/**
 * Initialisierung Programmparameter
 */
package de.vab.vabnet.stammdaten.init;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.vab.vabnet.common.ConstantsVABnet;
import de.vab.vabnet.rmi.RmiResponse;
import de.vab.vabnet.stammdaten.bean.StammdatenRemote;
import de.vab.vabnet.stammdaten.domain.Parameter;
import de.vab.vabnet.stammdaten.domain.ParameterText;
import de.vab.vabnet.stammdaten.domain.ParameterZahl;
import de.vab.vabnet.stammdaten.domain.SubUnternehmen;
import de.vab.vabnet.stammdaten.domain.Unternehmen;

/**
 * @author stimmerling
 *
 */
public class ClientInitializeParameter {

    private static String user;

    private static String password;

    /**
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) throws NamingException {
        RmiResponse res;

        login();

        // lookup for SessionBean
        InitialContext ctx = getInitialContext();

        StammdatenRemote stammdatenManager = (StammdatenRemote) ctx
                .lookup(ConstantsVABnet.contextRoot
                        + StammdatenRemote.JNDI_REMOTE_NAME);

        List<Unternehmen> unternehmenList = stammdatenManager
                .getAllUnternehmen();

        for (Unternehmen unternehmen : unternehmenList) {

            System.out.println(unternehmen);
            if (!(unternehmen instanceof SubUnternehmen)) {

                ParameterZahl pNew = new ParameterZahl();
                ParameterText pNewText = new ParameterText();

                pNew.setUnternehmen(unternehmen);
                pNew.setParameter_nr(Parameter.PARAMETER_VORRUESTZEIT_UMLAUF);
                pNew.setName("Vorr�stzeit Umlauf (in Minuten)");
                pNew.setWert(Parameter.WERT_VORRUESTZEIT_UMLAUF);
                String hilfstext = "Hat sich f�r einen Umlauf innerhalb der Vorr�stzeit kein Tf angemeldet, so erscheint eine Warnung.";
                if (hilfstext.length() > Parameter.LAENGE_HILFSTEXT) {
                    System.out.println("Hilstext wird abgeschnitten");
                }
                pNew.setHilfstext(hilfstext);
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERFRUEHUNG_ANKUNFT);
                pNew.setName("Schwellwert Verfr�hung Ankunft (in Minuten)");
                pNew.setWert(Parameter.WERT_SCHWELLWERT_VERFRUEHUNG_ANKUNFT);
                pNew
                        .setHilfstext("Ab dieser Verfr�hung wird ein Zug an einem Messpunkt als unp�nktlich gewertet. Ein entsprechender Datensatz f�r QUMA wird erstellt.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERSPAETUNG_ANKUNFT);
                pNew.setName("Schwellwert Versp�tung Ankunft (in Minuten)");
                pNew.setWert(Parameter.WERT_SCHWELLWERT_VERSPAETUNG_ANKUNFT);
                pNew
                        .setHilfstext("Ab dieser Versp�tung wird ein Zug an einem Messpunkt als unp�nktlich gewertet. Ein entsprechender Datensatz f�r QUMA wird erstellt.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERFRUEHUNG_ABFAHRT);
                pNew.setName("Schwellwert Verfr�hung Abfahrt (in Minuten)");
                pNew.setWert(Parameter.WERT_SCHWELLWERT_VERFRUEHUNG_ABFAHRT);
                pNew
                        .setHilfstext("Ab dieser Verfr�hung wird ein Zug an einem Messpunkt als unp�nktlich gewertet. Ein entsprechender Datensatz f�r QUMA wird erstellt.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERSPAETUNG_ABFAHRT);
                pNew.setName("Schwellwert Versp�tung Abfahrt (in Minuten)");
                pNew.setWert(Parameter.WERT_SCHWELLWERT_VERSPAETUNG_ABFAHRT);
                pNew
                        .setHilfstext("Ab dieser Versp�tung wird ein Zug an einem Messpunkt als unp�nktlich gewertet. Ein entsprechender Datensatz f�r QUMA wird erstellt.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERFRUEHUNG_SEKUNDE_MINUTE);
                pNew
                        .setName("Schwellwert in Sekunden, ab dem eine Verfr�hung als 'Minute' bewertet wird.");
                pNew
                        .setWert(Parameter.WERT_SCHWELLWERT_VERFRUEHUNG_SEKUNDE_MINUTE);
                pNew.setAktiv(false);
                pNew
                        .setHilfstext("Bei einem Wert von '30' und einer planm��igen Ankunftszeit von 09:00 wird die Ist-Zeit 08:59:30 als 1 Minute Verfr�hung bewertet.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_SCHWELLWERT_VERSPAETUNG_SEKUNDE_MINUTE);
                pNew
                        .setName("Schwellwert in Sekunden, ab dem eine Versp�tung als 'Minute' bewertet wird.");
                pNew
                        .setWert(Parameter.WERT_SCHWELLWERT_VERSPAETUNG_SEKUNDE_MINUTE);
                pNew.setAktiv(false);
                pNew
                        .setHilfstext("Bei einem Wert von '30' und einer planm��igen Ankunftszeit von 09:00 wird die Ist-Zeit 09:00:30 als 1 Minute Versp�tung bewertet.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew.setParameter_nr(Parameter.PARAMETER_MAX_FAHRPLAN_ZEIT);
                pNew
                        .setName("Maximale Abfahrt-/Ankunftzeit im Fahrplan in Sekunden (Zeiten nach 0 Uhr > 24).");
                pNew.setWert(Parameter.WERT_MAX_FAHRPLAN_ZEIT);
                pNew
                        .setHilfstext("Erforderlich, wenn Uml�ufe �ber 0 Uhr hinausgehen. Die Zeiten werden in diesem Fall als Zeiten > 24:00 erfasst.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew.setParameter_nr(Parameter.PARAMETER_LADE_FAHRPLAN_ZEIT);
                pNew.setName("Ladezeit f�r Fahrplan n�chster Tag in Sekunden.");
                pNew.setWert(Parameter.WERT_LADE_FAHRPLAN_ZEIT);
                pNew
                        .setHilfstext("Zeit zu der Tagesfahrplan des n�chsten tages automatisch geladen wird.");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew.setParameter_nr(Parameter.PARAMETER_VORLAUF_FAHRT_AKTUELL);
                pNew.setName("Vorlaufzeit f�r aktuelle Fahrten (in Sekunden).");
                pNew.setWert(Parameter.WERT_VORLAUF_FAHRT_AKTUELL);
                pNew
                        .setHilfstext("Zeit, in der Fahrt im Vorlauf als aktuelle Fahrt ausgegeben wird (in Sekunden).");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNew
                        .setParameter_nr(Parameter.PARAMETER_NACHLAUF_FAHRT_AKTUELL);
                pNew
                        .setName("Nachlaufzeit f�r aktuelle Fahrten (in Sekunden).");
                pNew.setWert(Parameter.WERT_NACHLAUF_FAHRT_AKTUELL);
                pNew
                        .setHilfstext("Zeit, in der Fahrt im Nachlauf als aktuelle Fahrt ausgegeben wird (in Sekunden).");
                res = stammdatenManager.addParameter(pNew);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());

                pNewText.setParameter_nr(Parameter.PARAMETER_PFAD_QUMA);
                pNewText.setName("Verzeichnis Liefernachweis QUMA.");
                pNewText.setWert(Parameter.WERT_PFAD_QUMA);
                pNewText.setAktiv(false);
                pNewText
                        .setHilfstext("In diesem Verzeichnis werden die Liefernachweise abgelegt.");
                res = stammdatenManager.addParameter(pNewText);
                System.out.println(res);
//                if (res.isReturnCode() == false) { return; }
                System.out.println(res.getObject());
            }
        }
    }

    private static void login() {
        user = "Stammdaten";
        password = "Stammdaten";
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
