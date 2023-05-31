package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.ParticipantEvaluationResponseInterface;
import com.dbproj.manageprompt.dto.ParticipantEvaluationResponseSummarizeInterface;

import com.dbproj.manageprompt.entity.EvaluationInnerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationInnerDao extends JpaRepository<EvaluationInnerEntity, Long> {
    @Query(
            value = "select sum(communication_rating + performance_rating) / (count(communication_rating)*2) as total_avg_rating, avg(communication_rating) as avg_communication_rating, avg(performance_rating) as avg_performance_rating " +
                    "from (select emp_id, emp_pro_id from employee_project where emp_id=:emd_id) as ep " +
                    "natural join (select emp_pro_id, communication_rating, performance_rating from evaluation_inner) as ei " +
                    "group by emp_id",
            nativeQuery = true
    )
    List<ParticipantEvaluationResponseSummarizeInterface> findAllEvalEmp(@Param("emd_id") Long empId);

    @Query(
            value = "select emp_name, pro_name, role_name, start_date, end_date, proj_start_date, proj_end_date, emp_pro_id, avg(communication_rating) as avg_communication_rating, avg(performance_rating) as avg_performance_rating  from " +
                    "    (select * from (select emp_id, emp_name from employee where emp_id=:emd_id) as a natural join (select * from (select * from (select * from (select emp_pro_id, emp_id, start_date, end_date, pro_id, role_id from employee_project) ep natural join (select role_id, role_name from role_info) ri) as ep natural join (select pro_id, pro_name, start_date as proj_start_date, end_date as proj_end_date from project) as proj) as re) as ep_proj) " +
                    "        as ep_response" +
                    "    natural join" +
                    "        evaluation_inner as ei" +
                    "    where evaluator not in (select distinct emp_id from (select role_id, emp_id from employee_project) ep natural join role_info ri where role_name='PM')" +
                    "    group by emp_pro_id",
            nativeQuery = true
    )
    List<ParticipantEvaluationResponseInterface> findAllByPeerEvalEmp(@Param("emd_id") Long empId);

    @Query(
            value = "select emp_name, pro_name, role_name, start_date, end_date, proj_start_date, proj_end_date, emp_pro_id, avg(communication_rating) as avg_communication_rating, avg(performance_rating) as avg_performance_rating  from " +
                    "    (select * from (select emp_id, emp_name from employee where emp_id=:emd_id) as a natural join (select * from (select * from (select * from (select emp_pro_id, emp_id, start_date, end_date, pro_id, role_id from employee_project) ep natural join (select role_id, role_name from role_info) ri) as ep natural join (select pro_id, pro_name, start_date as proj_start_date, end_date as proj_end_date from project) as proj) as re) as ep_proj) " +
                    "        as ep_response" +
                    "    natural join" +
                    "        evaluation_inner as ei" +
                    "    where evaluator in (select distinct emp_id from (select role_id, emp_id from employee_project) ep natural join role_info ri where role_name='PM')" +
                    "    group by emp_pro_id",
            nativeQuery = true
    )
    List<ParticipantEvaluationResponseInterface> findAllByPmEvalEmp(@Param("emd_id") Long empId);

    @Query(
            value = "select * from evaluation_inner where evaluator=:evaluator and emp_pro_id=:empProId",
            nativeQuery = true
    )
    EvaluationInnerEntity findByEvaluator(@Param("evaluator") Long evaluator,
                                          @Param("empProId") Long empProId);
}
