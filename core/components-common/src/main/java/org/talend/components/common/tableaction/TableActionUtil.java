// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.common.tableaction;

import org.talend.components.common.tableaction.properties.TableActionProvider;

import java.util.Arrays;
import java.util.List;

import static org.talend.components.common.tableaction.TableAction.TableActionEnum;

public class TableActionUtil {

    private final static List<TableActionEnum> createTableActionList =
            Arrays.asList(TableActionEnum.CREATE, TableActionEnum.CREATE_IF_NOT_EXISTS, TableActionEnum.DROP_CREATE,
                    TableActionEnum.DROP_IF_EXISTS_AND_CREATE);

    public final static boolean isCreateTableAction(TableActionEnum action) {
        return createTableActionList.contains(action);
    }

    public final static boolean isCreateTableAction(TableActionProvider<TableActionEnum> actionProvider) {
        if(actionProvider == null){
            return false;
        }

        TableActionEnum action = actionProvider.getTableAction();
        return isCreateTableAction(action);
    }

}
