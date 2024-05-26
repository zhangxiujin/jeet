package com.jeet.course.bank.vo;

import com.jeet.course.bank.domain.CourseWordBank;
import lombok.Data;

@Data
public class BankStructureVo extends CourseWordBank {

    /**
     * 结构名称
     */
    private String structName;

}
