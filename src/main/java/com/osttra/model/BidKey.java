package com.osttra.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class BidKey {

    String company;
    String metal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidKey bidKey = (BidKey) o;
        return Objects.equals(company, bidKey.company) && Objects.equals(metal, bidKey.metal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, metal);
    }
}
