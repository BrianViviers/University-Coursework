/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.richardimms.www.android0303.DataModel;

import java.math.BigDecimal;

/**
 *This class holds all of the information relating to a Rule in the system
 * @author PhilipEdwards
 */
public class Rules {
    private BigDecimal id;
    private String rule;


    public Rules() {
    }

    public Rules(BigDecimal id, String rule){
        this.id = id;
        this.rule = rule;
    }

    /**
     * Accessor method used to retrieve the ID of a Rule.
     * @return - BigDecimal value being the ID of a Rule.
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Accessor method used to assign the ID of a Rule.
     * @param id - BigDecimal value being the ID of a Rule.
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * Accessor method used to retrieve the Rule.
     * @return - String value being the Rule.
     */
    public String getRule() {
        return rule;
    }

    /**
     * Accessor method used to assign the Rule.
     * @param rule - String value being the Rule.
     */
    public void setRule(String rule) {
        this.rule = rule;
    }
}
