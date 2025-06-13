package com.wbs.wbs.dto;

import lombok.Data;

@Data
public class BoundaryDto {
    private double north;
    private double south;
    private double east;
    private double west;


    @Override
    public String toString() {
        return String.format("[N: %.6f, S: %.6f, E: %.6f, W: %.6f]", north, south, east, west);
    }
}
