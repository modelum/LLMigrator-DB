package com.umu.prompts.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FrameworkType {

    // --- Java / JVM ---
    SPRING_DATA_JPA("Spring Data JPA"),
    SPRING_DATA_JDBC("Spring Data JDBC"),
    SPRING_DATA_R2DBC("Spring Data R2DBC"),
    SPRING_DATA_MONGODB("Spring Data MongoDB"),
    SPRING_DATA_CASSANDRA("Spring Data Cassandra"),
    SPRING_DATA_ELASTICSEARCH("Spring Data Elasticsearch"),
    SPRING_DATA_REDIS("Spring Data Redis"),

    JOOQ("JOOQ"),
    MYBATIS("MyBatis"),
    JDBI("JDBI"),
    HIBERNATE_ORM("Hibernate ORM"),
    ECLIPSELINK_ORM("EclipseLink ORM"),
    DATANUCLEUS("DataNucleus"),
    OBJECTDB_FRAMEWORK("ObjectDB"),
    QUARKUS_PANACHE("Quarkus Panache"),
    MICRONAUT_DATA("Micronaut Data"),
    JDBC("JDBC directo"),

    // --- .NET ---
    ENTITY_FRAMEWORK_CORE("Entity Framework Core"),
    NHIBERNATE("NHibernate"),
    DAPPER("Dapper"),
    LINQ_TO_SQL("LINQ to SQL"),
    ADO_NET("ADO.NET"),

    // --- JavaScript / TypeScript ---
    TYPEORM("TypeORM"),
    SEQUELIZE("Sequelize"),
    MIKRO_ORM("MikroORM"),
    PRISMA("Prisma"),
    KNEX("Knex.js"),
    OBJECTION_JS("Objection.js"),
    MONGOOSE("Mongoose"),
    WATERLINE("Waterline"),

    // --- Python ---
    SQLALCHEMY("SQLAlchemy"),
    PEEWEE("Peewee"),
    TORTOISE_ORM("Tortoise ORM"),
    PONY_ORM("Pony ORM"),
    DJANGO_ORM("Django ORM"),
    MONGOENGINE("MongoEngine"),
    ODMANTIC("ODMantic"),

    // --- Ruby ---
    ACTIVE_RECORD("Active Record (Rails)"),
    SEQUEL_RB("Sequel (Ruby)"),

    // --- Go ---
    GORM_GO("GORM (Go)"),
    ENT_GO("Ent (Go)"),
    BEEGO_ORM("Beego ORM"),

    // --- PHP ---
    ELOQUENT("Eloquent (Laravel)"),
    DOCTRINE_ORM("Doctrine ORM"),
    CAKEPHP_ORM("CakePHP ORM"),

    // --- Otros ---
    FIREBASE_FIRESTORE_SDK("Firebase Firestore SDK"),
    SUPABASE_CLIENT("Supabase Client"),
    PLANETSCALE_CLIENT("PlanetScale Client"),
    AWS_AMPLIFY_DATASOURCE("AWS Amplify DataSource");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public static FrameworkType fromValue(String value) {
        for (FrameworkType f : FrameworkType.values()) {
            if (f.value.equals(value)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

