package com.city.traffic.toll.fee.calculator.toll.model.entity;

import com.city.traffic.toll.fee.calculator.common.model.model.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tolls")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TollEntity extends EntityBase {
    private String vehicleNo;
    private String type;
    private LocalDate day;
    private LocalTime time;
}
