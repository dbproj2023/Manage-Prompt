package com.dbproj.manageprompt.Interface;

import java.util.Date;

public interface ProjectSearchResponseInterface {
    String getPro_id();

    String getPro_name();

    String getClient_name();

    Date getStart_date();

    Date getEnd_date();

    Integer getBudget();
}
