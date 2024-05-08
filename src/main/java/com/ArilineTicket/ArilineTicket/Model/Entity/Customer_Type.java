package com.ArilineTicket.ArilineTicket.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer_Type {
    private int typeId;
    private String typeName;
    private String typeClass;
}
