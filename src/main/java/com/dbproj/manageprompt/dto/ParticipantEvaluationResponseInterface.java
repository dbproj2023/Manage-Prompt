package com.dbproj.manageprompt.dto;

import java.util.Date;

public interface ParticipantEvaluationResponseInterface {
    String getEmp_name();

    String getPro_name();

    String getRole_name();

    Date getStart_date();

    Date getEnd_date();

    Date getProj_start_date();

    Date getProj_end_date();

    Long getEmp_pro_id();

    Double getAvg_communication_rating();

    Double getAvg_performance_rating();
}
