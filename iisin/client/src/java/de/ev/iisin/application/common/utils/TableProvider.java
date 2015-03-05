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

package de.ev.iisin.application.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.ev.iisin.application.common.interfaces.Sortable;
import de.ev.iisin.application.common.interfaces.Tableconfigurator;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.common.exceptions.ErrorTemplate;

/**
 * This class provides the sorting by doubleclicking the table header
 * 
 * @author doenmez
 * @version $Revision: 1.2 $
 */
public class TableProvider {

	private static TableProvider provider;

	private TableProvider() {

	}

	public static TableProvider getInstance() {
		if (provider == null) {
			provider = new TableProvider();
		}

		return provider;
	}

	public void provide(Object tableHolder, Object table) {
		for (Method mM : tableHolder.getClass().getDeclaredMethods()) {
			if (mM.isAnnotationPresent(Sortable.class)) {
				Sortable sort = mM.getAnnotation(Sortable.class);
				for (Method method : table.getClass().getDeclaredMethods()) {
					try {
						if (method.isAnnotationPresent(Tableconfigurator.class)) {
							method.invoke(table, sort.enabled(), sort
									.sortColumn(), sort.isASC());
						}
					} catch (IllegalArgumentException e) {
						ClientMessageHandler.handle(
								ErrorTemplate.TABLE_PROVIDER,
								ClientMessageType.ERROR);
					} catch (IllegalAccessException e) {
						ClientMessageHandler.handle(
								ErrorTemplate.TABLE_PROVIDER,
								ClientMessageType.ERROR);
					} catch (InvocationTargetException e) {
						ClientMessageHandler.handle(ErrorTemplate.TABLE_PROVIDER,
								ClientMessageType.ERROR);
					}
				}
			}
		}
	}
}
