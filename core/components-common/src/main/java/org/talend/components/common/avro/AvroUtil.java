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
package org.talend.components.common.avro;

public class AvroUtil {

    public final static String NULL_NAME = "null_name";
    public final static String EMPTY_NAME = "empty_name";
    public final static char SUBSTITUTE_FIRST_CHAR = '_';
    public final static char SUBSTITUTE_CHAR = '_';

    private AvroUtil(){}

    /**
     * Transform given name to an acceptable avro schema name.
     *
     * Avro schema name must start with [A-Za-z_] folled by [A-Za-z0-9_]
     * If name doesn't start by [A-Za-z_] then add '_' as prefix then transform all not acceptable char to '_'.
     * If given name is null, return "null
     *
     * @param name The input name
     * @return Acceptable name
     */
    public static String getAcceptableName(String name){
        if(name == null){
            return NULL_NAME;
        }

        name = name.trim();

        if(name.isEmpty()){
            return EMPTY_NAME;
        }

        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            newName.append((!(Character.isLetterOrDigit(c) || c == '_')) ? SUBSTITUTE_CHAR : c);
        }

        char first = newName.charAt(0);
        if (!(Character.isLetter(first) || first == '_')) {
            newName.insert(0, SUBSTITUTE_FIRST_CHAR);
        }
        return newName.toString();
    }

}
