package com.treez.spring.customException;

import lombok.Data;

@Data
public class InventoryDeleteException extends Exception {
    public InventoryDeleteException(String errorMessage) {
        super(errorMessage);
    }
}
