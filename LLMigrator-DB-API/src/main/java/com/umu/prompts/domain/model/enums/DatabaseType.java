package com.umu.prompts.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseType {
  MY_SQL("MySQL"),

  POSTGRE_SQL("PostgreSQL"),

  SQ_LITE("SQLite"),

  ORACLE_DB("OracleDB"),

  MICROSOFT_SQL_SERVER("MicrosoftSQLServer"),

  MARIA_DB("MariaDB"),

  IBMDB2("IBMDB2"),

  AMAZON_RDS("AmazonRDS"),

  GOOGLE_CLOUD_SQL("GoogleCloudSQL"),

  SAP_HANA("SAPHana"),

  MONGO_DB("MongoDB"),

  COUCH_DB("CouchDB"),

  CASSANDRA("Cassandra"),

  REDIS("Redis"),

  COUCHBASE("Couchbase"),

  DYNAMO_DB("DynamoDB"),

  NEO4J("Neo4j"),

  RIAK("Riak"),

  H_BASE("HBase"),

  ELASTICSEARCH("Elasticsearch"),

  INFLUX_DB("InfluxDB"),

  TIMESCALE_DB("TimescaleDB"),

  OPEN_TSDB("OpenTSDB"),

  DB4O("db4o"),

  OBJECT_DB("ObjectDB"),

  GOOGLE_SPANNER("GoogleSpanner"),

  COCKROACH_DB("CockroachDB"),

  TI_DB("TiDB"),

  MEMCACHED("Memcached"),

  BERKELEY_DB("BerkeleyDB"),

  SCYLLA_DB("ScyllaDB"),

  ARANGO_DB("ArangoDB"),

  ORIENT_DB("OrientDB"),

  MARK_LOGIC("MarkLogic"),

  GOOGLE_BIGTABLE("GoogleBigtable");

  private final String value;

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static DatabaseType fromValue(String value) {
    for (DatabaseType b : DatabaseType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
