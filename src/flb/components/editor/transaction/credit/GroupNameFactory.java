package flb.components.editor.transaction.credit;

import java.util.Calendar;

public class GroupNameFactory {

    public static String createGroupName(Calendar date, float sum){
        String groupName = "$yr-$mo-$day-$hr$min:$sum";
        groupName = groupName.replace("$yr", Integer.toString(date.get(Calendar.YEAR)));
        groupName = groupName.replace("$mo", String.format("%02d", date.get(Calendar.MONTH)+1));
        groupName = groupName.replace("$day", String.format("%02d", date.get(Calendar.DAY_OF_MONTH)));
        groupName = groupName.replace("$hr", String.format("%02d", date.get(Calendar.HOUR_OF_DAY)));
        groupName = groupName.replace("$min", String.format("%02d", date.get(Calendar.MINUTE)));
        groupName = groupName.replace("$sum", String.format("%.2f", sum));
        return groupName;
    }
}
