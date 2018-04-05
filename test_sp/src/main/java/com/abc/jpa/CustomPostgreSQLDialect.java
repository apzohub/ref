package com.abc.jpa;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class CustomPostgreSQLDialect extends PostgreSQL94Dialect{

    public CustomPostgreSQLDialect() {
        super(); this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
