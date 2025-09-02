package com.wbs.wbs.dto;

import lombok.Data;

@Data
public class ModeFuncRequest {
    private String mac;
    private String mode;  // sit|stand|walk|none|joystick ...
    private String func;  // avoid|climb|stairs|hill|run|none ...
}
