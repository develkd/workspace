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

package de.ev.iisin.wizard.provider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.ev.iisin.common.descriptor.ComponentDeskriptor;


/**
 * @author Kemal Dönmez
 * 
 */
public class ComponentProvider {
	private static ComponentProvider provider;

	private ComponentProvider() {

	}

	public static ComponentProvider getInstance() {
		if (provider == null) {
			provider = new ComponentProvider();
		}

		return provider;
	}

	public List<ComponentPair> provide(Object object) {
		List<ComponentPair> list = new ArrayList<ComponentPair>();
		Class<?> parent = object.getClass();
		while (parent != null) {
			for (Field field : parent.getDeclaredFields()) {
				if (field.isAnnotationPresent(ComponentDeskriptor.class)) {
					list.add(new ComponentPair(field
							.getAnnotation(ComponentDeskriptor.class), object));
				}
			}
			parent = parent.getSuperclass();
		}
		return list;
	}
}
