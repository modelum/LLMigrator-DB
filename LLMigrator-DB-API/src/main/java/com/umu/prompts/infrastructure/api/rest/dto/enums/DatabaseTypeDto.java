package com.umu.prompts.infrastructure.api.rest.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "Enumeration for Database Types used in DTOs")
@Getter
@RequiredArgsConstructor
public enum DatabaseTypeDto {
  @Schema(description = "MySQL database type")
  MY_SQL("MySQL"),

  @Schema(description = "PostgreSQL database type")
  POSTGRE_SQL("PostgreSQL"),

  @Schema(description = "SQLite database type")
  SQ_LITE("SQLite"),

  @Schema(description = "Oracle database type")
  ORACLE_DB("OracleDB"),

  @Schema(description = "Microsoft SQL Server database type")
  MICROSOFT_SQL_SERVER("MicrosoftSQLServer"),

  @Schema(description = "MariaDB database type")
  MARIA_DB("MariaDB"),

  @Schema(description = "IBM DB2 database type")
  IBMDB2("IBMDB2"),

  @Schema(description = "Amazon RDS database type")
  AMAZON_RDS("AmazonRDS"),

  @Schema(description = "Google Cloud SQL database type")
  GOOGLE_CLOUD_SQL("GoogleCloudSQL"),

  @Schema(description = "SAP HANA database type")
  SAP_HANA("SAPHana"),

  @Schema(description = "MongoDB database type")
  MONGO_DB("MongoDB"),

  @Schema(description = "CouchDB database type")
  COUCH_DB("CouchDB"),

  @Schema(description = "Cassandra database type")
  CASSANDRA("Cassandra"),

  @Schema(description = "Redis database type")
  REDIS("Redis"),

  @Schema(description = "Couchbase database type")
  COUCHBASE("Couchbase"),

  @Schema(description = "DynamoDB database type")
  DYNAMO_DB("DynamoDB"),

  @Schema(description = "Neo4j database type")
  NEO4J("Neo4j"),

  @Schema(description = "Riak database type")
  RIAK("Riak"),

  @Schema(description = "HBase database type")
  H_BASE("HBase"),

  @Schema(description = "Elasticsearch database type")
  ELASTICSEARCH("Elasticsearch"),

  @Schema(description = "InfluxDB database type")
  INFLUX_DB("InfluxDB"),

  @Schema(description = "TimescaleDB database type")
  TIMESCALE_DB("TimescaleDB"),

  @Schema(description = "OpenTSDB database type")
  OPEN_TSDB("OpenTSDB"),

  @Schema(description = "db4o database type")
  DB4O("db4o"),

  @Schema(description = "ObjectDB database type")
  OBJECT_DB("ObjectDB"),

  @Schema(description = "Google Spanner database type")
  GOOGLE_SPANNER("GoogleSpanner"),

  @Schema(description = "CockroachDB database type")
  COCKROACH_DB("CockroachDB"),

  @Schema(description = "TiDB database type")
  TI_DB("TiDB"),

  @Schema(description = "Memcached database type")
  MEMCACHED("Memcached"),

  @Schema(description = "BerkeleyDB database type")
  BERKELEY_DB("BerkeleyDB"),

  @Schema(description = "ScyllaDB database type")
  SCYLLA_DB("ScyllaDB"),

  @Schema(description = "ArangoDB database type")
  ARANGO_DB("ArangoDB"),

  @Schema(description = "OrientDB database type")
  ORIENT_DB("OrientDB"),

  @Schema(description = "MarkLogic database type")
  MARK_LOGIC("MarkLogic"),

  @Schema(description = "Google Bigtable database type")
  GOOGLE_BIGTABLE("GoogleBigtable");

  private final String value;

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DatabaseTypeDto fromJson(String value) {
    return fromValue(value);
  }

  public static DatabaseTypeDto fromValue(String value) {
    for (DatabaseTypeDto b : DatabaseTypeDto.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
