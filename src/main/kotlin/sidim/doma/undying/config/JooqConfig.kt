package sidim.doma.undying.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {
  @Value("${spring.datasource.username}")
  String userName = "username";
  @Value("${spring.datasource.password}")
  String password = "password";
  @Value("${spring.datasource.url}")
  String url = "jdbc:postgresql://localhost:1234/some_db";
  @Value("${sql.dialect}")
  String jooqSqlDialect = "some-dialect";

  @Bean
  public DefaultConfiguration configuration() throws SQLException {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(DriverManager.getConnection(url, userName, password));
    SQLDialect dialect = SQLDialect.valueOf(jooqSqlDialect);
    jooqConfiguration.set(dialect);

    return jooqConfiguration;
  }

  @Bean
  public DSLContext dslContext(org.jooq.Configuration configuration) {
    return new DefaultDSLContext(configuration);
  }

}
