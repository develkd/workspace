/*
 * Copyright (c) 2008 Kemal D�nmez. All Rights Reserved.
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

package de.ev.iisin.application.common.formatter;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * @author Kemal D�nmez
 * 
 */
public class PositivLongFormat extends Format {

	/**
	 * Erzeugt am 21.02.2009
	 */
	private static final long serialVersionUID = -7047124649075837596L;
	public static final PositivLongFormat SHORT = new PositivLongFormat("##");
	public static final PositivLongFormat NORMAL = new PositivLongFormat(
			"############");
	public static final PositivLongFormat LONG = new PositivLongFormat(
			"#######################");

	private final DecimalFormat outputFormat;
	private final NumberFormat inputFormat;

	private PositivLongFormat(String outputMask) {
		outputFormat = new DecimalFormat(outputMask);
		inputFormat = NumberFormat.getNumberInstance();

	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo,
			FieldPosition pos) {

		if (obj == null) {
			return toAppendTo;
		}
		long value = 0;

		try {
			value = ((Long) obj).intValue();
		} catch (ClassCastException ex) {

		}
		if (value == 0) {
			return toAppendTo;
		}

		if (outputFormat.equals(SHORT.outputFormat)) {
			toAppendTo.append(SHORT.outputFormat.format(value));
			return toAppendTo;

		}

		toAppendTo.append(outputFormat.format(value));
		return toAppendTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.text.Format#parseObject(java.lang.String,
	 *      java.text.ParsePosition)
	 */
	@Override
	public Object parseObject(String source, ParsePosition pos) {
		if (source == null || source.trim().length() == 0)
			return new Long(0);
		Number value = (Number) inputFormat.parseObject(source, pos);
		long meter = value != null ? (long) value.longValue() : -0;
		return new Long(meter);

	}

}
