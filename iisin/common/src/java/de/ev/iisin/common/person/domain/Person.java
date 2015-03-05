/*
 * Copyright (c) 2008 Kemal Dönmez. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *          
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package de.ev.iisin.common.person.domain;

import java.awt.Dimension;
import java.awt.Image;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.swing.ImageIcon;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.descriptor.ComponentDeskriptor;
import de.ev.iisin.common.descriptor.ComponentTyp;
import de.ev.iisin.common.descriptor.EnumAttributes;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "PERSON_SEQUENCE", sequenceName = "PERSON_SEQ")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries(value = { @NamedQuery(name = "Person.getAdresse", query = "from Adresse o where o.id= :id") })
public abstract class Person extends ValidableModel {

	/**
	 * Erzeugt am 20.12.2008
	 */
	private static final long serialVersionUID = -6593151660874068819L;
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd.MMMM.yyyy");

	public final static String PROPERTY_LASTNAME = "lastName";
	public final static String PROPERTY_NAME = "name";
	public final static String PROPERTY_BLOB_IMAGE = "blobImage";
	public final static String PROPERTY_CREATE_IMAGE = "createdImage";
	public final static String PROPERTY_ADRESSE = "adresse";
	public final static String PROPERTY_DELETED = "deleted";
	public final static String PROPERTY_EMAIL = "email";
	public final static String PROPERTY_PERSONEN = "personen";
	public final static String PROPERTY_BERUF = "beruf";
	public final static String PROPERTY_WEIBLICH = "weiblich";
	public final static String PROPERTY_PERSON_TYP = "personTyp";
	public final static String PROPERTY_TELEFON = "telefon";
	public final static String PROPERTY_AGE = "age";
	public final static String PROPERTY_ADGE_AS_STRING = "adgeAsString";

	private long person_id;
	@Transient
	private Blob blobImage;

	@ComponentDeskriptor(labelEnumText = EnumAttributes.NAME, component = ComponentTyp.TEXT, property = PROPERTY_NAME)
	private String name = "";
	@ComponentDeskriptor(labelEnumText = EnumAttributes.LAST_NAME, component = ComponentTyp.TEXT, property = PROPERTY_LASTNAME)
	private String lastName = "";
	@ComponentDeskriptor(labelEnumText = EnumAttributes.EMAIL, component = ComponentTyp.TEXT, property = PROPERTY_EMAIL)
	private String email;
	private ImageIcon image;
	private Date age;

	private ImageIcon minimizedIcon;
	private Adresse adresse;
	private Collection<Person> personenList;
	private Collection<Telefon> telefon;

	private Beruf beruf;
	private PersonTyp personTyp;

	private boolean isDeleted;
	private boolean isWeiblich;

	private int width = 150;
	private int height = 150;

	public Person() {
		isDeleted = false;
		personenList = new ArrayList<Person>();
		telefon = new ArrayList<Telefon>();
		beruf = new Beruf();
		adresse = new Adresse();
		personTyp = new PersonTyp();
		isWeiblich = false;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SEQUENCE")
	public long getPerson_ID() {
		return person_id;
	}

	public void setPerson_ID(long person_id) {
		this.person_id = person_id;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		Adresse old = getAdresse();
		this.adresse = adresse;
		firePropertyChange(PROPERTY_ADRESSE, old, adresse);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		String oldValue = getLastName();
		this.lastName = lastName;
		firePropertyChange(PROPERTY_LASTNAME, oldValue, lastName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = getName();
		this.name = name;
		firePropertyChange(PROPERTY_NAME, oldValue, name);
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	public Blob getBlobImage() {
		// makeImage(blobImage);
		return blobImage;
	}

	public void setBlobImage(Blob blobImage) {
		this.blobImage = blobImage;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		if (isDeleted == isDeleted())
			return;
		this.isDeleted = isDeleted;
		firePropertyChange(PROPERTY_DELETED, !isDeleted, isDeleted);
	}

	public boolean isWeiblich() {
		return isWeiblich;
	}

	public void setWeiblich(boolean isWeiblich) {
		if (isWeiblich == isWeiblich())
			return;
		this.isWeiblich = isWeiblich;
		firePropertyChange(PROPERTY_WEIBLICH, !isWeiblich, isWeiblich);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String old = getEmail();
		this.email = email;
		firePropertyChange(PROPERTY_EMAIL, old, email);
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Person> getPersonen() {
		return personenList;
	}

	public void setPersonen(Collection<Person> personenList) {
		this.personenList = personenList;
		firePropertyChange(PROPERTY_PERSONEN, null, personenList);
	}

	/**
	 * @return the beruf
	 */
	@OneToOne(cascade = CascadeType.ALL)
	public Beruf getBeruf() {
		return beruf;
	}

	/**
	 * @param beruf
	 *            the beruf to set
	 */
	public void setBeruf(Beruf beruf) {
		Beruf old = getBeruf();
		this.beruf = beruf;
		firePropertyChange(PROPERTY_BERUF, old, beruf);
	}

	// @ManyToOne(cascade = CascadeType.ALL)
	public PersonTyp getPersonTyp() {
		return personTyp;
	}

	public void setPersonTyp(PersonTyp typ) {
		PersonTyp oldTyp = getPersonTyp();
		this.personTyp = typ;
		firePropertyChange(PROPERTY_PERSON_TYP, oldTyp, typ);
	}

	/**
	 * @return the collectionTelefon
	 */
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	public Collection<Telefon> getTelefon() {
		return telefon;
	}

	/**
	 * @param collectionTelefon
	 *            the collectionTelefon to set
	 */
	public void setTelefon(Collection<Telefon> telefon) {
		this.telefon = telefon;
		firePropertyChange(PROPERTY_TELEFON, null, telefon);
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		Date oldValue = getAge();
		this.age = age;
		firePropertyChange(PROPERTY_AGE, oldValue, age);
	}

	// Helper Methods---------------------------------------

	// @Transient
	// public Image getMinimizedImage() {
	// if (minimizedImage == null) {
	// minimizedImage = getMinimizedImage(width, height);
	// }
	// return minimizedImage;
	// }

	// @Transient
	// public ImageIcon getScaledImageIcon() {
	// if (scladeImageIcon == null) {
	// Image im = getMinimizedImage(200,200);
	// if (im != null)
	// scladeImageIcon = new ImageIcon(im);
	// }
	// return scladeImageIcon;
	// }

	@Transient
	public String getAdgeAsString() {
		return getAge() == null ? "" : DATE_FORMAT.format(getAge());
	}

	@Transient
	public ImageIcon getMinimizedImageIcon() {
		if (minimizedIcon == null) {
			Image im = getMinimizedImage(image, 40, 40);
			if (im != null)
				minimizedIcon = new ImageIcon(im);
		}
		return minimizedIcon;
	}

	@Transient
	public Dimension getPreferedImageSize() {
		return new Dimension(width, height);
	}

	@Transient
	private Image getMinimizedImage(ImageIcon image, int width, int height) {
		return image == null ? null : image.getImage().getScaledInstance(width,
				height, Image.SCALE_FAST);
	}

	@Transient
	public String getAdresseFormated() {
		StringBuilder builder = new StringBuilder();
		if (adresse != null) {
			builder.append(adresse.getStrasse()).append(",").append(
					adresse.getPlz()).append(" ").append(adresse.getOrt());
		}
		return builder.toString();
	}

	@Transient
	public ImageIcon getCreatedImage() {
		return image;
	}

	@Transient
	public void setCreatedImage(ImageIcon newImage) {
		ImageIcon oldIcon = getCreatedImage();
		this.image = newImage == null ? newImage : new ImageIcon(
				getMinimizedImage(newImage, width, height));
		firePropertyChange(PROPERTY_CREATE_IMAGE, oldIcon, image);

	}

	@Transient
	public ValidationResult validate(ValidationResult result) {

		// if (ValidateUtil.isBlank(getName())) {
		// result.addError("Name darf nicht leer sein");
		// }
		// String email = getEmail();
		// if (ValidateUtil.isBlank(email)) {
		// result.addError("Email darf nicht leer sein");
		// } else if (ValidateUtil.isEmail(email)) {
		// result.addError("Email ist nicht richtig");
		// }
		// result = getAdresse().validate(result);
		// result = getBeruf().validate(result);
		// for (Person persons : getPersonen()) {
		// result = persons.validate(result);
		//			
		// }
		return result;
	}

}
