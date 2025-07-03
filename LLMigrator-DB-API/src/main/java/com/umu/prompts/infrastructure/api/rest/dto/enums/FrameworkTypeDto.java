package com.umu.prompts.infrastructure.api.rest.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Enumeration for Framework Types used in DTOs")
public enum FrameworkTypeDto {
  @Schema(description = "Spring Data JPA")
  SPRING_DATA_JPA("Spring Data JPA"),

  @Schema(description = "Spring Data JDBC")
  SPRING_DATA_JDBC("Spring Data JDBC"),

  @Schema(description = "Spring Data R2DBC")
  SPRING_DATA_R2DBC("Spring Data R2DBC"),

  @Schema(description = "Spring Data MongoDB")
  SPRING_DATA_MONGODB("Spring Data MongoDB"),

  @Schema(description = "Spring Data Cassandra")
  SPRING_DATA_CASSANDRA("Spring Data Cassandra"),

  @Schema(description = "Spring Data Elasticsearch")
  SPRING_DATA_ELASTICSEARCH("Spring Data Elasticsearch"),

  @Schema(description = "Spring Data Redis")
  SPRING_DATA_REDIS("Spring Data Redis"),

  @Schema(description = "JOOQ")
  JOOQ("JOOQ"),

  @Schema(description = "MyBatis")
  MYBATIS("MyBatis"),

  @Schema(description = "JDBI")
  JDBI("JDBI"),

  @Schema(description = "Hibernate ORM")
  HIBERNATE_ORM("Hibernate ORM"),

  @Schema(description = "EclipseLink ORM")
  ECLIPSELINK_ORM("EclipseLink ORM"),

  @Schema(description = "DataNucleus")
  DATANUCLEUS("DataNucleus"),

  @Schema(description = "ObjectDB")
  OBJECTDB_FRAMEWORK("ObjectDB"),

  @Schema(description = "Quarkus Panache")
  QUARKUS_PANACHE("Quarkus Panache"),

  @Schema(description = "Micronaut Data")
  MICRONAUT_DATA("Micronaut Data"),

  @Schema(description = "Direct JDBC")
  JDBC("JDBC directo"),

  @Schema(description = "Entity Framework Core")
  ENTITY_FRAMEWORK_CORE("Entity Framework Core"),

  @Schema(description = "NHibernate")
  NHIBERNATE("NHibernate"),

  @Schema(description = "Dapper")
  DAPPER("Dapper"),

  @Schema(description = "LINQ to SQL")
  LINQ_TO_SQL("LINQ to SQL"),

  @Schema(description = "ADO.NET")
  ADO_NET("ADO.NET"),

  @Schema(description = "TypeORM")
  TYPEORM("TypeORM"),

  @Schema(description = "Sequelize")
  SEQUELIZE("Sequelize"),

  @Schema(description = "MikroORM")
  MIKRO_ORM("MikroORM"),

  @Schema(description = "Prisma")
  PRISMA("Prisma"),

  @Schema(description = "Knex.js")
  KNEX("Knex.js"),

  @Schema(description = "Objection.js")
  OBJECTION_JS("Objection.js"),

  @Schema(description = "Mongoose")
  MONGOOSE("Mongoose"),

  @Schema(description = "Waterline")
  WATERLINE("Waterline"),

  @Schema(description = "SQLAlchemy")
  SQLALCHEMY("SQLAlchemy"),

  @Schema(description = "Peewee")
  PEEWEE("Peewee"),

  @Schema(description = "Tortoise ORM")
  TORTOISE_ORM("Tortoise ORM"),

  @Schema(description = "Pony ORM")
  PONY_ORM("Pony ORM"),

  @Schema(description = "Django ORM")
  DJANGO_ORM("Django ORM"),

  @Schema(description = "MongoEngine")
  MONGOENGINE("MongoEngine"),

  @Schema(description = "ODMantic")
  ODMANTIC("ODMantic"),

  @Schema(description = "Active Record (Rails)")
  ACTIVE_RECORD("Active Record (Rails)"),

  @Schema(description = "Sequel (Ruby)")
  SEQUEL_RB("Sequel (Ruby)"),

  @Schema(description = "GORM (Go)")
  GORM_GO("GORM (Go)"),

  @Schema(description = "Ent (Go)")
  ENT_GO("Ent (Go)"),

  @Schema(description = "Beego ORM")
  BEEGO_ORM("Beego ORM"),

  @Schema(description = "Eloquent (Laravel)")
  ELOQUENT("Eloquent (Laravel)"),

  @Schema(description = "Doctrine ORM")
  DOCTRINE_ORM("Doctrine ORM"),

  @Schema(description = "CakePHP ORM")
  CAKEPHP_ORM("CakePHP ORM"),

  @Schema(description = "Firebase Firestore SDK")
  FIREBASE_FIRESTORE_SDK("Firebase Firestore SDK"),

  @Schema(description = "Supabase Client")
  SUPABASE_CLIENT("Supabase Client"),

  @Schema(description = "PlanetScale Client")
  PLANETSCALE_CLIENT("PlanetScale Client"),

  @Schema(description = "AWS Amplify DataSource")
  AWS_AMPLIFY_DATASOURCE("AWS Amplify DataSource");

  private final String value;

  @Override
  public String toString() {
    return value;
  }

  @JsonCreator
  public static FrameworkTypeDto fromJson(String value) {
    return fromValue(value);
  }

  public static FrameworkTypeDto fromValue(String value) {
    for (FrameworkTypeDto f : FrameworkTypeDto.values()) {
      if (f.value.equals(value)) {
        return f;
      }
    }
    throw new IllegalArgumentException("Unexpected value \'" + value + "\'");
  }
}
