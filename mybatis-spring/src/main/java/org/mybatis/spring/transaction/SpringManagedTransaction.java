/**
 * Copyright 2010-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.spring.transaction;

import static org.springframework.util.Assert.notNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.transaction.Transaction;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * {@code SpringManagedTransaction} handles the lifecycle of a JDBC connection. It retrieves a connection from Spring's
 * transaction manager and returns it back to it when it is no longer needed.
 * <p>
 * If Spring's transaction handling is active it will no-op all commit/rollback/close calls assuming that the Spring
 * transaction manager will do the job.
 * <p>
 * If it is not it will behave like {@code JdbcTransaction}.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 */
public class SpringManagedTransaction implements Transaction { /* Spring事务 */

  private static final Logger LOGGER = LoggerFactory.getLogger(SpringManagedTransaction.class);

  private final DataSource dataSource;

  private Connection connection; /* 一个 DefaultSqlSession 一个连接 */

  private boolean isConnectionTransactional;

  private boolean autoCommit;

  public SpringManagedTransaction(DataSource dataSource) {
    notNull(dataSource, "No DataSource specified");
    this.dataSource = dataSource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Connection getConnection() throws SQLException {
    if (this.connection == null) {
      openConnection();
    }
    return this.connection;
  }

  /**
   * Gets a connection from Spring transaction manager and discovers if this {@code Transaction} should manage
   * connection or let it to Spring.
   * <p>
   * It also reads autocommit setting because when using Spring Transaction MyBatis thinks that autocommit is always
   * false and will always call commit/rollback so we need to no-op that calls.
   */
  private void openConnection() throws SQLException {
    this.connection = DataSourceUtils.getConnection(this.dataSource); /* 如果外部业务方法开启了事务@Transactional,那么ThreadLocal中已经存在数据库连接。保证同个事务使用同个Connection */
    this.autoCommit = this.connection.getAutoCommit();
    this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);

    LOGGER.debug(() -> "JDBC Connection [" + this.connection + "] will"
        + (this.isConnectionTransactional ? " " : " not ") + "be managed by Spring");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commit() throws SQLException {
    if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
      LOGGER.debug(() -> "Committing JDBC Connection [" + this.connection + "]");
      this.connection.commit(); /* 如果设置了autoCommit=false，这里就提交事务 */
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rollback() throws SQLException {
    if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
      LOGGER.debug(() -> "Rolling back JDBC Connection [" + this.connection + "]");
      this.connection.rollback();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws SQLException {
    DataSourceUtils.releaseConnection(this.connection, this.dataSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getTimeout() throws SQLException {
    ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
    if (holder != null && holder.hasTimeout()) {
      return holder.getTimeToLiveInSeconds();
    }
    return null;
  }

}
