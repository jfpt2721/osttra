package com.osttra.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Bid {
    String company;
    String metal;
    Integer price;

    @Override
    public String toString() {
        return "[" + company + "," + metal + "," + price + "]";
    }
}
