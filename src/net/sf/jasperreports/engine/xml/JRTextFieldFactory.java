/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine.xml;

import java.util.Collection;

import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public class JRTextFieldFactory extends JRBaseFactory
{

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRXmlLoader xmlLoader = (JRXmlLoader)digester.peek(digester.getCount() - 1);
		Collection groupEvaluatedTextFields = xmlLoader.getGroupEvaluatedTextFields();
		JasperDesign jasperDesign = (JasperDesign)digester.peek(digester.getCount() - 2);

		JRDesignTextField textField = new JRDesignTextField(jasperDesign);

		String isStretchWithOverflow = atts.getValue(XmlConstants.ATTRIBUTE_isStretchWithOverflow);
		if (isStretchWithOverflow != null && isStretchWithOverflow.length() > 0)
		{
			textField.setStretchWithOverflow(Boolean.valueOf(isStretchWithOverflow).booleanValue());
		}

		EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.getByName(atts.getValue(XmlConstants.ATTRIBUTE_evaluationTime));
		if (evaluationTime != null)
		{
			textField.setEvaluationTime(evaluationTime);
		}
		if (textField.getEvaluationTimeValue() == EvaluationTimeEnum.GROUP)
		{
			groupEvaluatedTextFields.add(textField);
			
			String groupName = atts.getValue(XmlConstants.ATTRIBUTE_evaluationGroup);
			if (groupName != null)
			{
				JRDesignGroup group = new JRDesignGroup();
				group.setName(groupName);
				textField.setEvaluationGroup(group);
			}
		}
		
		textField.setPattern(atts.getValue(XmlConstants.ATTRIBUTE_pattern));

		String isBlankWhenNull = atts.getValue(XmlConstants.ATTRIBUTE_isBlankWhenNull);
		if (isBlankWhenNull != null && isBlankWhenNull.length() > 0)
		{
			textField.setBlankWhenNull(Boolean.valueOf(isBlankWhenNull));
		}

		textField.setLinkType(atts.getValue(XmlConstants.ATTRIBUTE_hyperlinkType));
		textField.setLinkTarget(atts.getValue(XmlConstants.ATTRIBUTE_hyperlinkTarget));
		
		String bookmarkLevelAttr = atts.getValue(XmlConstants.ATTRIBUTE_bookmarkLevel);
		if (bookmarkLevelAttr != null)
		{
			textField.setBookmarkLevel(Integer.parseInt(bookmarkLevelAttr));
		}

		return textField;
	}
	

}
