/*
 * This file is generated by jOOQ.
 */
package com.viartemev.ktor_jooq.generated;


import com.viartemev.ktor_jooq.generated.tables.Channels;
import com.viartemev.ktor_jooq.generated.tables.FlywaySchemaHistory;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.channels</code>.
     */
    public static final Channels CHANNELS = com.viartemev.ktor_jooq.generated.tables.Channels.CHANNELS;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = com.viartemev.ktor_jooq.generated.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;
}