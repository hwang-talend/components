// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.jira;

import org.osgi.service.component.annotations.Component;
import org.talend.components.api.AbstractComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.api.Constants;
import org.talend.components.jira.tjirainput.TJiraInputDefinition;
import org.talend.components.jira.tjiraoutput.TJiraOutputDefinition;

import com.google.auto.service.AutoService;

/**
 * Install all of the definitions provided for the JIRA family of components.
 */
@AutoService(ComponentInstaller.class)
@Component(name = Constants.COMPONENT_INSTALLER_PREFIX + JiraFamilyDefinition.NAME, service = ComponentInstaller.class)
public class JiraFamilyDefinition extends AbstractComponentFamilyDefinition implements ComponentInstaller {

    public static final String NAME = "Jira";

    public JiraFamilyDefinition() {
        super(NAME,
        // Components
                new TJiraInputDefinition(), new TJiraOutputDefinition());
    }

    @Override
    public void install(ComponentFrameworkContext ctx) {
        ctx.registerComponentFamilyDefinition(this);
    }
}
