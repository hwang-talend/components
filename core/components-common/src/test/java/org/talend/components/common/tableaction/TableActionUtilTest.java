package org.talend.components.common.tableaction;

import org.junit.Test;
import org.talend.components.common.tableaction.properties.TableActionProvider;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TableActionUtilTest {

    private List<TableAction.TableActionEnum> areNotCreateTableAction =
            Arrays.asList(TableAction.TableActionEnum.NONE, TableAction.TableActionEnum.CLEAR,
                    TableAction.TableActionEnum.TRUNCATE);

    @Test
    public void isCreateTableAction() {
        for(TableAction.TableActionEnum action : TableAction.TableActionEnum.values()){
            assertEquals(!areNotCreateTableAction.contains(action), TableActionUtil.isCreateTableAction(action));
        }
    }

    @Test
    public void isCreateTableActionFromProvider(){
        TableActionProvider createActionProvider = new TableActionProvider<TableAction.TableActionEnum>() {
            @Override
            public TableAction.TableActionEnum getTableAction() {
                return TableAction.TableActionEnum.CREATE;
            }
        };

        assertTrue(TableActionUtil.isCreateTableAction(createActionProvider));

        TableActionProvider dontCreateActionProvider = new TableActionProvider<TableAction.TableActionEnum>() {
            @Override
            public TableAction.TableActionEnum getTableAction() {
                return TableAction.TableActionEnum.NONE;
            }
        };

        assertFalse(TableActionUtil.isCreateTableAction(dontCreateActionProvider));
    }

}