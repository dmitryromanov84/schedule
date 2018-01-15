package com.hornsnhuffs.schedule.modules.department;

import com.hornsnhuffs.schedule.core.BaseEntity;

import javax.persistence.Entity;

/**
 * Отдел
 */
@Entity
public class Department extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
