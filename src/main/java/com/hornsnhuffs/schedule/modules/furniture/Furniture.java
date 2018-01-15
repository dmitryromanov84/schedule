package com.hornsnhuffs.schedule.modules.furniture;

import com.hornsnhuffs.schedule.core.BaseEntity;
import com.hornsnhuffs.schedule.modules.department.Department;

import javax.persistence.*;

/**
 * Единица изготовляемой предприятием номенклатуры (мебель)
 */
@Entity
public class Furniture extends BaseEntity {

    private String name;

    @ManyToOne(targetEntity = Department.class)
    private Department manufacturer;

    @Override
    public String toString() {
        return "Furniture{" +
                "name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public Integer getManufacturerId() {
        return manufacturer != null ? manufacturer.getId() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Department manufacturer) {
        this.manufacturer = manufacturer;
    }
}
