package com.jimi64.viral_whitelist;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViralWhitelist implements ModInitializer {

    public static final String MOD_ID = "ViralWhitelist";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_ID);
    }
}
