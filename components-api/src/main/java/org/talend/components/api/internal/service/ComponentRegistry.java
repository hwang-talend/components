// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.api.internal.service;

import org.talend.components.api.ComponentDefinition;

import java.util.Map;

/**
 * interface for the internal component registry that will have a specific implementation for OSGI and Spring
 */
public interface ComponentRegistry {

    /**
     * @return a map of components using their name as a key, never null.
     */
    Map<String, ComponentDefinition> getComponents();

}